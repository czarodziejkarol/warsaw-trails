package com.carlncarl.spdb.android;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carlncarl.spdb.android.dto.UserDto;

public class MainActivity extends Activity {
	EditText editTextLogin;
	EditText editTextPassword;
	Button buttonLogOn;
	MainActivity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.context = this;
		editTextLogin = (EditText) findViewById(R.id.editTextUsername);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);

		buttonLogOn = (Button) findViewById(R.id.buttonLogOn);
		buttonLogOn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String[] array = { editTextLogin.getText().toString(),
						editTextPassword.getText().toString() };
				new LoadingTask(context).execute(array);

			}
		});
		// Remove title bar

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startActivityNawi() {
		Intent intent_name = new Intent();
		intent_name.setClass(this.getApplicationContext(), NaviActivity.class);
		startActivity(intent_name);
	}

}

class LoadingTask extends AsyncTask<String, Void, UserDto> {

	MainActivity context;

	public LoadingTask(MainActivity context) {

		this.context = context;
	}

//	@Override
//	protected UserDto doInBackground(String... params) {
//		String url = "http://89.72.147.55:8080/warsaw-trails/"
//				+ "/api/register/?login={login}&password={password}";
//
//		// Create a new RestTemplate instance
//		RestTemplate restTemplate = new RestTemplate();
//
//		// Add the String message converter
//		restTemplate.getMessageConverters().add(
//				new MappingJacksonHttpMessageConverter());
//		// Make the HTTP GET request, marshaling the response to a String
//
//		
//
//		Map<String, Object> urlVariables = new HashMap<String, Object>();
//		urlVariables.put("login", params[0]);
//		urlVariables.put("password", params[1]);
//
//		UserDto user = restTemplate.getForObject(url, UserDto.class,
//				urlVariables);
//
//		System.out.println(user);
//		return user;
//	}

	@Override
	protected void onPostExecute(UserDto result) {
		context.startActivityNawi();
		if (result == null) {
			Toast.makeText(context, "O NIE UDALO SIE", Toast.LENGTH_SHORT)
					.show();
		} else {
			context.startActivityNawi();
		}

		// super.onPostExecute(result);
		//
		// context.startActivity(new Intent(context, NaviActivity.class));
		// Intent intent = new Intent(context ,NaviActivity.class);
		//
		// context.startActivity(intent);
	}

@Override
protected UserDto doInBackground(String... arg0) {
	// TODO Auto-generated method stub
	return new UserDto();
}
}
