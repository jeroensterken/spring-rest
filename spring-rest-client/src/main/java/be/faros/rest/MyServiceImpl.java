package be.faros.rest;

import org.springframework.stereotype.Component;


@Component
public class MyServiceImpl implements MyService {
	
	public String getMessage() {
		return "Hello world!";	
	}

}
