/**
 * 
 */
package com.carlncarl.spdb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.carlncarl.spdb.model.dto.UserDto;

/**
 * @author Karol
 *
 */
public class TestService {

	
	/**
	 * Test method for {@link com.carlncarl.spdb.controller.UserController#login(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLogin() {
		
		String url = "http://89.72.147.55:8080/warsaw-trails/"
				+ "/api/register/?login={login}&password={password}";

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the String message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		// Make the HTTP GET request, marshaling the response to a String

		// String result = restTemplate.getForObject(url, String.class,
		// "Android");

		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("login", "carles");
		urlVariables.put("password", "dupa");

		UserDto user = restTemplate.getForObject(url, UserDto.class,
				urlVariables);
		
		assertNotNull(user);
		
		
		
//		String url = "http://89.72.147.55:8080/warsaw-trails/api/greet/";
//		RestTemplate temp = new RestTemplate();
//		
//		Greeting gret = new Greeting(12312, "321312");
//		
//		temp.getMessageConverters().add(
//				new MappingJacksonHttpMessageConverter());
//		
//		AbstractDto2 das = temp.postForObject(url, gret, AbstractDto2.class);
//		
//		Greeting d = (Greeting) das.getGreet();
//		
//		assertNotNull( das.getGreet());;
		
	
	}

	/**
	 * Test method for {@link com.carlncarl.spdb.controller.UserController#greeting(com.carlncarl.spdb.model.dto.Greeting)}.
	 */
	@Test
	public void testGreeting() {
		fail("Not yet implemented");
	}

}
