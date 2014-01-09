package com.carlncarl.spdb.android.tasks;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;

public class ServerTask extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		String url = "https://ajax.googleapis.com/ajax/" + 
			    "services/search/web?v=1.0&q={query}";
		
		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the String message converter
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		// Make the HTTP GET request, marshaling the response to a String
		String result = restTemplate.getForObject(url, String.class, "Android");
		
		System.out.println(result);
		return result;
	}

}
