package be.faros.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import be.faros.rest.domain.MyRest;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTest {
	
	@Autowired
	private MyService service;

	/*
	 * http://www.javabeat.net/articles/256-introduction-to-spring-rest-services-3.html
	 *
	 * RestClient firefox plugin !
	 * 
	 * http://blog.springsource.org/2009/03/08/rest-in-spring-3-mvc/ --> ontstaan van REST support vanaf Spring 3.0 en waarom ze niet jax-rs implementeren ...
	 */
	@Test
	public void testSimpleProperties() throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		String result = restTemplate.getForObject("http://localhost:8080/spring-rest-server/rest/1", String.class);
		System.out.println("--> "+result);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

		/*MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		restTemplate.getMessageConverters().add(converter);*/

		ResponseEntity<MyRest> response = restTemplate.exchange("http://localhost:8080/spring-rest-server/rest/1", HttpMethod.GET, httpEntity, MyRest.class);
		MyRest result1 = response.getBody();
		System.out.println("result 1 : "+result1.getString_field());
	}
}