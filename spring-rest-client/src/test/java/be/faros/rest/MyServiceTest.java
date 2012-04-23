package be.faros.rest;

import be.faros.rest.MyServiceImpl;
import junit.framework.TestCase;

public class MyServiceTest extends TestCase {

	private MyServiceImpl service = new MyServiceImpl();
	
	public void testReadOnce() throws Exception {
		assertEquals("Hello world!", service.getMessage());
	}

}
