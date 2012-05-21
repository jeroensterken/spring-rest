package be.faros.rest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

	@RequestMapping(value = "/rest/{id}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody MyRestResult anonymousRest(@PathVariable long id) {
		return new MyRestResult("anonymous");
	}

	@RequestMapping(value = "/rest/secured/{id}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody MyRestResult securedRest(@PathVariable long id) {
		return new MyRestResult("secured");
	}
}