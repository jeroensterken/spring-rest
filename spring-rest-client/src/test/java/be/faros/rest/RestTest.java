package be.faros.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import be.faros.rest.domain.MyRestResult;
import be.faros.rest.security.credentials.Credentials;
import be.faros.rest.security.credentials.SimpleCrendentialsProvider;
import be.faros.rest.security.factory.BasicSecureSimpleClientHttpRequestFactory;
import be.faros.rest.security.factory.SecureCommonsClientHttpRequestFactory;

@ContextConfiguration(value="classpath:/META-INF/spring/app-config.xml" )
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTest {

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * http://www.javabeat.net/articles/256-introduction-to-spring-rest-services-3.html
	 * RestClient firefox plugin !
	 * http://blog.springsource.org/2009/03/08/rest-in-spring-3-mvc/ --> ontstaan van REST support vanaf Spring 3.0 en waarom ze niet jax-rs implementeren ...
	 */
	@Test
	public void testUnsecuredRestCall() throws Exception {
		String uri = "http://localhost:8080/spring-rest-server/rest/1";

		String result = restTemplate.getForObject(uri, String.class);
		System.out.println("--> result : "+result);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

		/*MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		restTemplate.getMessageConverters().add(converter);*/

		ResponseEntity<MyRestResult> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, MyRestResult.class);
		MyRestResult result1 = response.getBody();
		System.out.println("--> result : "+result1.getString_field());
	}

	@Test
	public void testSecuredRestCall() {
		String uri = "http://localhost:8080/spring-rest-server/rest/secured/1";
        String username = "admin";
        String password = "admin";

        /*
         * Constructing the http request yourself
         */
        BasicSecureSimpleClientHttpRequestFactory requestFactory = new BasicSecureSimpleClientHttpRequestFactory();
        requestFactory.setCredentialsProvider(new SimpleCrendentialsProvider(new Credentials(username, password)));
        restTemplate.setRequestFactory(requestFactory);

        String result;
        result = restTemplate.getForObject(uri, String.class);
        
        /*
         * Using spring's CommonsClientHttpRequestFactory & library : "commons-httpclient" 
         * so we don't have to deal directly with HTTP headers. 
         */
        SecureCommonsClientHttpRequestFactory commonsHttpFactory = new SecureCommonsClientHttpRequestFactory();
        commonsHttpFactory.setCredentialsProvider(new SimpleCrendentialsProvider(new Credentials(username, password)));
        restTemplate.setRequestFactory(commonsHttpFactory);

        result = restTemplate.getForObject(uri, String.class);
	}
}