package fun.deepsky.springboot.atomikos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fun.deepsky.springboot.atomikos.service.MutiDataService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {AtomikosApplication.class})
public class TestMutiDataSource {

	@Autowired
	private MutiDataService mutiDataService;
	
	@Test
	public void testInsertData(){
		mutiDataService.insert();
	}
}
