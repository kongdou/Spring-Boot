package fun.deepsky.cooking.bootmongodb4;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import fun.deepsky.cooking.bootmongodb4.entity.Person;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@SpringBootApplication
public class Mongodb4Boot {
	// 定义本地化排序
	final static Collation collation = Collation.of("zh");
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	MongoOperations operation;

	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;

	@Autowired
	ReactiveMongoOperations reactiveOperation;

	
	public class Decimal128Serializer extends JsonSerializer<Decimal128> {
		@Override
		public void serialize(Decimal128 decimal128, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException, JsonProcessingException {
			jsonGenerator.writeNumber(decimal128.bigDecimalValue());
		}
	}

	public class Decimal128Deserializer extends JsonDeserializer<Decimal128> {
		@Override
		public Decimal128 deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
			return new Decimal128(new BigDecimal(jsonParser.getText()));
		}
	}
	
	

	/**
	 * 自定义MongoDB对象映射调整
	 */
	@Order(1)
	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
		try {
			// 删除SpringData MongoDB下面的_class 字段
			mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
			mappingConverter.setCustomConversions(beanFactory.getBean(MongoCustomConversions.class));
		} catch (NoSuchBeanDefinitionException ex) {
			// Ignore
		}
		return mappingConverter;
	}

	@Order(2)
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(
				Arrays.asList(BigDecimalToDecimal128Converter.INSTANCE, Decimal128ToBigDecimalConverter.INSTANCE));
	}

	/**
	 * 常规式处理加载
	 */
	private void common_load() {
		// 1 清除数据
		mongoTemplate.dropCollection("person");
		mongoTemplate.createCollection(Person.class, CollectionOptions.just(collation));

		// 指定字符集排序
		mongoTemplate.indexOps(Person.class).ensureIndex(new Index("name", Sort.Direction.ASC).collation(collation));

		// 2 增加数据
		Person person = Person.builder().name("张三").decimal(new BigDecimal("123.45")).decimal128(new Decimal128(new BigDecimal("123.56")))
				.build();
		mongoTemplate.save(person);
		person = Person.builder().name("李四").decimal(new BigDecimal("42.4")).decimal128(new Decimal128(new BigDecimal("23.46"))).build();
		mongoTemplate.save(person);
		person = Person.builder().name("王五").decimal(new BigDecimal("2.83")).decimal128(null).build();
		mongoTemplate.save(person);
		person = Person.builder().name("赵六").decimal(null).decimal128(new Decimal128(new BigDecimal("38.7"))).build();
		mongoTemplate.save(person);
		person = Person.builder().name("王五").decimal(new BigDecimal("2.56")).decimal128(new Decimal128(new BigDecimal("3.41"))).build();
		mongoTemplate.save(person);
		person = Person.builder().name("李四").decimal(new BigDecimal("3.33")).decimal128(new Decimal128(new BigDecimal("63.22"))).build();
		mongoTemplate.save(person);
		person = Person.builder().name("刘二").decimal(new BigDecimal("7.85")).decimal128(new Decimal128(new BigDecimal("22.13"))).build();
		mongoTemplate.save(person);

		// 3 更新第一条数据，加5
		mongoTemplate.updateFirst(new Query(), new Update().inc("decimal", 5), Person.class);

		// 4.1 查询数据基于对象
		log.info("查询数据基于对象......");
		Query query = new Query().collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		List<Person> loadeds = mongoTemplate.find(query, Person.class);
		loadeds.stream().forEach(l -> {
			log.info("姓名：" + l.getName() + "，BigDecimal类型：" + l.getDecimal() + "，Decimal128类型：" + l.getDecimal128());
		});

		// 4.1.1 大于5查询
		query = new Query(Criteria.where("decimal").gt(5)).collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		loadeds = mongoTemplate.find(query, Person.class);
		loadeds.stream().forEach(l -> {
			log.info("姓名：" + l.getName() + "，BigDecimal类型：" + l.getDecimal());
		});

		// 4.2 查询数据基于Document
		log.info("查询数据基于Document......");
		query = new Query().collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		List<Document> dbObjects = mongoTemplate.find(query, Document.class, "person");
		dbObjects.stream().forEach(l -> {
			// Document中只能直接转成Decimal128
			Decimal128 result = null;
			if (Objects.nonNull(l.get("decimal"))) {
				result = (Decimal128) l.get("decimal");
			}
			BigDecimal result1 = null;
			if (Objects.nonNull(l.get("decimal128"))) {
				result1 = ((Decimal128) l.get("decimal128")).bigDecimalValue();
			}
			log.info("姓名：" + l.getString("name") + "，Decimal128类型：" + result + "，BigDecimal类型：" + result1);
		});

		// 5.1 聚合返回普通结果
		AggregationOptions options = AggregationOptions.builder().collation(collation).build();
		SortOperation sorts = sort(new Sort(Direction.ASC, "name"));// 聚合中排序
		log.info("聚合返回结果......");
		Aggregation aggregation = newAggregation(match(Criteria.where("decimal").gt(5)), project("decimal", "decimal128", "name"), sorts)
				.withOptions(options);// 聚合计算
		AggregationResults<Document> groupResults = operation.aggregate(aggregation, Person.class, Document.class);
		List<Document> dbs = groupResults.getMappedResults();
		dbs.stream().forEach(l -> {
			Decimal128 result = (Decimal128) l.get("decimal");
			BigDecimal result1 = ((Decimal128) l.get("decimal128")).bigDecimalValue();
			log.info("姓名：" + l.getString("name") + "，Decimal128类型：" + result + "，BigDecimal类型：" + result1);
		});

		// 5.2 聚合求和
		log.info("聚合求和......");
		aggregation = newAggregation(Person.class, group("name").sum("decimal").as("sums").sum("decimal128").as("sums1"),
				project("sums", "sums1").and("name").previousOperation(), sorts).withOptions(options);
		groupResults = operation.aggregate(aggregation, Person.class, Document.class);
		dbs = groupResults.getMappedResults();
		dbs.stream().forEach(l -> {
			val s = l.get("sums");
			val s1 = l.get("sums1");
			BigDecimal result = new BigDecimal(s.toString());
			BigDecimal result1 = new BigDecimal(s1.toString());
			log.info("姓名：" + l.getString("name") + "，BigDecimal求和：" + result + "，BigDecimal求和：" + result1);
		});

		// 5.3 聚合求平均值
		log.info("聚合求平均值......");
		aggregation = newAggregation(Person.class, group("name").avg("decimal").as("avgs").avg("decimal128").as("avgs1"),
				project("avgs", "avgs1").and("name").previousOperation(), sorts).withOptions(options);
		groupResults = operation.aggregate(aggregation, Person.class, Document.class);
		dbs = groupResults.getMappedResults();
		dbs.stream().forEach(l -> {
			val a = l.get("avgs");
			val a1 = l.get("avgs1");
			Decimal128 result = Objects.isNull(a) ? new Decimal128(BigDecimal.ZERO) : (Decimal128) a;
			BigDecimal result1 = Objects.isNull(a1) ? BigDecimal.ZERO
					: ((Decimal128) a1).bigDecimalValue().setScale(2, BigDecimal.ROUND_DOWN);
			log.info("姓名：" + l.getString("name") + "，Decimal128求平均值：" + result + "，BigDecimal求平均值：" + result1);
		});

		// 5.4 聚合求最大最小值
		log.info("聚合求最大最小值......");
		aggregation = newAggregation(Person.class, group("name").max("decimal").as("max").min("decimal").as("min"),
				project("max", "min").and("name").previousOperation(), sorts).withOptions(options);
		groupResults = operation.aggregate(aggregation, Person.class, Document.class);
		dbs = groupResults.getMappedResults();
		dbs.stream().forEach(l -> {
			val r = l.get("max");
			val r1 = l.get("min");
			Decimal128 result = Objects.isNull(r) ? new Decimal128(BigDecimal.ZERO) : (Decimal128) r;
			BigDecimal result1 = Objects.isNull(r1) ? BigDecimal.ZERO : ((Decimal128) r1).bigDecimalValue();
			log.info("姓名：" + l.getString("name") + "，求decimal属性的最大值：" + result + "，最小值：" + result1);
		});

		// 5.5 聚合统计
		log.info("聚合统计......");
		aggregation = newAggregation(Person.class, project("name"), unwind("name"), group("name").count().as("count"),
				project("count").and("name").previousOperation(), sorts).withOptions(options);
		AggregationResults<PersonCount> groupResults1 = operation.aggregate(aggregation, Person.class, PersonCount.class);
		List<PersonCount> objects1 = groupResults1.getMappedResults();
		objects1.stream().forEach(l -> {
			log.info("姓名：" + l.getName() + "，数量：" + l.getCount());
		});

		// 5.6 聚合转换DTO
		log.info("聚合转换DTO......");
		aggregation = newAggregation(Person.class, project().and("id").as("personId").and("name").as("name"), sorts).withOptions(options);
		AggregationResults<NewPerson> groupResults2 = operation.aggregate(aggregation, Person.class, NewPerson.class);
		List<NewPerson> objects2 = groupResults2.getMappedResults();
		objects2.stream().forEach(l -> {
			log.info("ID：" + l.getPersonId() + "，姓名：" + l.getName());
		});
	}

	// private void common_transaction() {
	// whenQueryDuringMongoTransaction_thenSuccess();
	// }

	// @Transactional
	// public void whenQueryDuringMongoTransaction_thenSuccess() {
	// Person person = Person.builder().name("曾帅").decimal(new BigDecimal("52013.14"))
	// .decimal128(new Decimal128(new BigDecimal("168.68"))).build();
	// operation.save(person);
	// person = Person.builder().name("沈秀").decimal(new BigDecimal("520520.14"))
	// .decimal128(new Decimal128(new BigDecimal("999.99"))).build();
	// List<Person> users = operation.find(new Query(), Person.class);
	// }

	/**
	 * 响应式处理加载
	 * 
	 * @throws InterruptedException
	 */
	private void reactive_load() throws InterruptedException {
		// 1 清除数据
		reactiveMongoTemplate.dropCollection(Person.class).subscribe();
		TimeUnit.SECONDS.sleep(2);
		reactiveMongoTemplate.createCollection(Person.class, CollectionOptions.just(collation)).subscribe();
		// 指定字符集排序
		reactiveMongoTemplate.indexOps(Person.class).ensureIndex(new Index("name", Sort.Direction.ASC).collation(collation)).subscribe();

		// 2 增加数据
		Person person = Person.builder().name("张三").decimal(new BigDecimal("123.45")).decimal128(new Decimal128(new BigDecimal("123.56")))
				.build();
		reactiveMongoTemplate.save(person).subscribe();
		person = Person.builder().name("李四").decimal(new BigDecimal("42.4")).decimal128(new Decimal128(new BigDecimal("23.46"))).build();
		reactiveMongoTemplate.save(person).subscribe();
		person = Person.builder().name("王五").decimal(new BigDecimal("2.83")).decimal128(null).build();
		reactiveMongoTemplate.save(person).subscribe();
		person = Person.builder().name("赵六").decimal(null).decimal128(new Decimal128(new BigDecimal("38.7"))).build();
		reactiveMongoTemplate.save(person).subscribe();
		person = Person.builder().name("王五").decimal(new BigDecimal("2.56")).decimal128(new Decimal128(new BigDecimal("3.41"))).build();
		reactiveMongoTemplate.save(person).subscribe();
		person = Person.builder().name("李四").decimal(new BigDecimal("3.33")).decimal128(new Decimal128(new BigDecimal("63.22"))).build();
		reactiveMongoTemplate.save(person).subscribe();
		person = Person.builder().name("刘二").decimal(new BigDecimal("7.85")).decimal128(new Decimal128(new BigDecimal("22.13"))).build();
		reactiveMongoTemplate.save(person).subscribe();

		// 3 更新第一条数据，加5
		reactiveMongoTemplate.updateFirst(new Query(), new Update().inc("decimal", 5), Person.class).subscribe();

		// 4.1 查询数据基于对象
		log.info("查询数据基于对象......");
		Query query = new Query().collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		reactiveOperation.find(query, Person.class).log().subscribe(l -> {
			log.info("姓名：" + l.getName() + "，BigDecimal类型：" + l.getDecimal() + "，Decimal128类型：" + l.getDecimal128());
		});

		// 4.1.1 大于5查询
		query = new Query(Criteria.where("decimal").gt(5)).collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		reactiveMongoTemplate.find(query, Person.class).log().subscribe(l -> {
			log.info("姓名：" + l.getName() + "，BigDecimal类型：" + l.getDecimal());
		});

		// 4.2 查询数据基于Document
		log.info("查询数据基于Document......");
		query = new Query().collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		reactiveMongoTemplate.find(query, Document.class, "person").log().subscribe(l -> {
			// Document中只能直接转成Decimal128
			Decimal128 result = null;
			if (Objects.nonNull(l.get("decimal"))) {
				result = (Decimal128) l.get("decimal");
			}
			BigDecimal result1 = null;
			if (Objects.nonNull(l.get("decimal128"))) {
				result1 = ((Decimal128) l.get("decimal128")).bigDecimalValue();
			}
			log.info("姓名：" + l.getString("name") + "，Decimal128类型：" + result + "，BigDecimal类型：" + result1);
		});

		// 5.1 聚合返回普通结果
		AggregationOptions options = new AggregationOptions.Builder().collation(collation).build();
		log.info("聚合返回结果......");
		Aggregation aggregation = newAggregation(match(Criteria.where("decimal").gt(5)), project("decimal", "decimal128", "name"),
				sort(Sort.Direction.ASC, "name")).withOptions(options);// 聚合计算
		reactiveOperation.aggregate(aggregation, Person.class, Document.class).log().subscribe(l -> {
			Decimal128 result = (Decimal128) l.get("decimal");
			BigDecimal result1 = ((Decimal128) l.get("decimal128")).bigDecimalValue();
			log.info("姓名：" + l.getString("name") + "，Decimal128类型：" + result + "，BigDecimal类型：" + result1);
		});

		// 5.2 聚合求和
		log.info("聚合求和......");
		aggregation = newAggregation(Person.class, group("name").sum("decimal").as("sums").sum("decimal128").as("sums1"),
				project("sums", "sums1").and("name").previousOperation());
		reactiveOperation.aggregate(aggregation, Person.class, Document.class).log().subscribe(l -> {
			val s = l.get("sums");
			val s1 = l.get("sums1");
			BigDecimal result = new BigDecimal(s.toString());
			BigDecimal result1 = new BigDecimal(s1.toString());
			log.info("姓名：" + l.getString("name") + "，BigDecimal求和：" + result + "，BigDecimal求和：" + result1);
		});

		// 5.3 聚合求平均值
		log.info("聚合求平均值......");
		aggregation = newAggregation(Person.class, group("name").avg("decimal").as("avgs").avg("decimal128").as("avgs1"),
				project("avgs", "avgs1").and("name").previousOperation());
		reactiveOperation.aggregate(aggregation, Person.class, Document.class).log().subscribe(l -> {
			val a = l.get("avgs");
			val a1 = l.get("avgs1");
			Decimal128 result = Objects.isNull(a) ? new Decimal128(BigDecimal.ZERO) : (Decimal128) a;
			BigDecimal result1 = Objects.isNull(a1) ? BigDecimal.ZERO
					: ((Decimal128) a1).bigDecimalValue().setScale(2, BigDecimal.ROUND_DOWN);
			log.info("姓名：" + l.getString("name") + "，Decimal128求平均值：" + result + "，BigDecimal求平均值：" + result1);
		});

		// 5.4 聚合求最大最小值
		log.info("聚合求最大最小值......");
		aggregation = newAggregation(Person.class, group("name").max("decimal").as("max").min("decimal").as("min"),
				project("max", "min").and("name").previousOperation());
		reactiveOperation.aggregate(aggregation, Person.class, Document.class).log().subscribe(l -> {
			val r = l.get("max");
			val r1 = l.get("min");
			Decimal128 result = Objects.isNull(r) ? new Decimal128(BigDecimal.ZERO) : (Decimal128) r;
			BigDecimal result1 = Objects.isNull(r1) ? BigDecimal.ZERO : ((Decimal128) r1).bigDecimalValue();
			log.info("姓名：" + l.getString("name") + "，求decimal属性的最大值：" + result + "，最小值：" + result1);
		});
	}

	/**
	 * 一定要加subscribe来订阅请求，否则会处理不成功。
	 * 
	 * @throws InterruptedException
	 */
	@PostConstruct
	private void postConstruct() throws InterruptedException {
		log.info("开始常规式处理........................");
		common_load();
		// log.info("开始常规式事务处理........................");
		// common_transaction();
		log.info("开始响应式处理........................");
		reactive_load();
	}

	/**
	 * 用户详细信息
	 *
	 */
	@GetMapping(value = "/all", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public List<Person> common() {
		Query query = new Query().collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		List<Person> loadeds = mongoTemplate.find(query, Person.class);
		return loadeds;
	}

	/**
	 * 用户详细信息
	 *
	 */
	@GetMapping(value = "/all-flux")
	public Flux<Person> react() {
		Query query = new Query().collation(collation);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		return reactiveMongoTemplate.find(query, Person.class);
	}

	@Data
	static class NewPerson {
		String personId;
		String name;
	}

	@Data
	static class PersonCount {
		String name;
		private long count;
	}

	private static enum BigDecimalToDecimal128Converter implements Converter<BigDecimal, Decimal128> {
			INSTANCE;
		@Override
		public Decimal128 convert(BigDecimal bigDecimal) {
			return new Decimal128(bigDecimal);
		}
	}

	private static enum Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {
			INSTANCE;
		@Override
		public BigDecimal convert(Decimal128 decimal128) {
			return decimal128.bigDecimalValue();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Mongodb4Boot.class, args);
	}

	// 性能监控
	@Bean
	MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
		return (registry) -> registry.config().commonTags("application", applicationName);
	}

}
