package be.faros.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/rest")
public class MyRestController {
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody MyRest home(@PathVariable long id, Model model) {
		System.out.println(" inside ");
		return new MyRest("test");
	}
	
}