package fun.deepsky.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByNameAndAddress(String name, String address);

	List<Person> findByAddress(String name);

	@Query("select p from Person p where p.name=:name and p.address=:address")
	Person withNameAndAddressQuery(@Param("name") String name,@Param("address") String address);
	
	Person withNameAndAddressNameQuery(String name,String address);

}
