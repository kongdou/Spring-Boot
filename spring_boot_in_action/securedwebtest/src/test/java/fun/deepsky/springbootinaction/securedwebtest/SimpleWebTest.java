package fun.deepsky.springbootinaction.securedwebtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@WebIntegrationTest
public class SimpleWebTest {

	@Test(expected=HttpClientErrorException.class)
	public void pageNotFound() {
		try {
			RestTemplate rest = new RestTemplate();
			rest.getForObject("http://localhost:8080/bogusPage", String.class);
			fail("should result in HTTP 404");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.SC_NOT_FOUND, e.getStatusCode());
			throw e;
		}
	}

}
