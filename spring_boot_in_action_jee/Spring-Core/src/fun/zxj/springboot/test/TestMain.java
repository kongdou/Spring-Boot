package fun.zxj.springboot.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//在Junit环境下提供Spring TestContextFrameWork的功能
@ContextConfiguration(classes = {TestConfig.class})//用来加载ApplicationContext
@ActiveProfiles("prod")//声明活动的Profile
public class TestMain {

	@Autowired
	private TestBean testBean;
	
	@Test
	public void prodBeanShouldInject(){
		String expected = "from prpcitem_car";
		String actual = testBean.getConent();
		
		Assert.assertEquals(expected, actual);
	}

}
