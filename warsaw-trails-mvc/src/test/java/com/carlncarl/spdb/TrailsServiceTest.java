package com.carlncarl.spdb;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.dto.CoordinateDto;
import com.carlncarl.spdb.model.dto.PointDto;
import com.carlncarl.spdb.model.dto.TrailDto;
import com.carlncarl.spdb.model.dto.UserDto;
import com.carlncarl.spdb.service.TrailService;
import com.carlncarl.spdb.service.UserServiceImpl;

public class TrailsServiceTest {

	@Autowired
	TrailService trailService;
	
	
	UserServiceImpl usersService;
	
	@Test
	public void testGetTrailsByDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTrailsByRate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() throws WarsawTrailsException {
	//	usersService = new UserServiceImpl();
	//	User uM = usersService.register("cardasdwles", "dupa");
		UserDto u = new UserDto(new Long(1), "carles");
		
		TrailDto t = new TrailDto();
		t.setCreator(u);
		t.setDescription("piêkny szlak kêdzie¿awy");
		t.setEndTime(new Date());
		t.setName("Szlak z dupy");
		ArrayList<CoordinateDto> path = new ArrayList<CoordinateDto>();
		path.add(new CoordinateDto(22.01, 23.20));
		path.add(new CoordinateDto(22.02, 23.21));
		path.add(new CoordinateDto(22.03, 23.22));
		path.add(new CoordinateDto(22.04, 23.23));
		path.add(new CoordinateDto(22.05, 23.24));
		path.add(new CoordinateDto(22.06, 23.25));
		path.add(new CoordinateDto(22.07, 23.26));
		path.add(new CoordinateDto(22.08, 23.27));
		t.setPath(path );
		
		
		ArrayList<PointDto> points = new ArrayList<PointDto>();
		PointDto p = new PointDto();
		p.setDescription("wata wata");
		p.setPointDescription("Extra siomek exksta musisz to zobaczyc");
		p.setEndTime(new Date());
		p.setLatitude(22.02);
		p.setLongitude(23.20);
		p.setName("Punkt michigan");
		p.setStartTime(new Date());
		points.add(p);
		
		p = new PointDto();
		p.setDescription("wata wa21312ta");
		//p.setPoiRef("21312416fgvdahbanawbfiabibach4tba");
		p.setEndTime(new Date());
		p.setLatitude(22.06);
		p.setPointDescription("Extra siomekdwdwad wad aw awa exksta musisz to zobaczyc");

		p.setLongitude(23.25);
		p.setName("Punkt michigan");
		p.setStartTime(new Date());
		points.add(p);
		
		p = new PointDto();
		p.setDescription("wata wa21312ta");
	//	p.setMainPointId(new Long(2131));
		p.setEndTime(new Date());
		p.setPointDescription("Extra s dawd awdaw dawaw iomek exksta musisz to zobaczyc");

		p.setLatitude(22.06);
		p.setLongitude(23.25);
		p.setName("Punkt michigandawdaw");
		p.setStartTime(new Date());
		points.add(p);
		
		t.setPoints(points );
		
		t.setStartTime(new Date());
		t.setType("pieszy");
		
		String url = "http://89.72.147.55:8080/warsaw-trails/api/add-trail/";

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();
		//new RestTemplate(org.springframework.http.client.support.HttpRequestWrapper)
		// Add the String message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		// Make the HTTP GET request, marshalin	g the response to a String

		// String result = restTemplate.getForObject(url, String.class,
		// "Android");
		//System.setProperty("http.keepAlive", "false");
		TrailDto trailDto = null;
		try{
			trailDto = restTemplate.postForObject(url, t, TrailDto.class);
		} catch(Exception e){
			System.err.println(e.getMessage() + e.getStackTrace().toString());
		}
		
		
		
//		String url = "http://89.72.147.55:8080/warsaw-trails/api/add-trail";
//		RestTemplate temp = new RestTemplate();
//		
//		
//		temp.getMessageConverters().add(
//				new MappingJacksonHttpMessageConverter());
//		
//		TrailDto das = temp.postForObject(url, t, TrailDto.class);
//		
//
//		assertNotNull( das.getId());;
		
		
		fail("Not yet implemented");
	}

}
