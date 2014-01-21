package com.carlncarl.spdb.android;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carlncarl.spdb.android.dto.UserDto;
import com.carlncarl.spdb.android.service.TrailsService;

public class MainActivity extends Activity {

	public static final String USER_EXTRA = "user_dto_extra";

	EditText editTextLogin;
	EditText editTextPassword;
	Button buttonLogOn;
	MainActivity context;

	private TrailsService mTrailsService;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mTrailsService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mTrailsService = ((TrailsService.TrailBinder) service).getService();
			mIsBound = true;
		}
	};
	private boolean mIsBound;

	@Override
	protected void onStart() {
		super.onStart();
		bindService(new Intent(this, TrailsService.class), connection,
				Context.BIND_AUTO_CREATE);
		Intent intent = new Intent(this, TrailsService.class);

		startService(intent);
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(connection);
			mIsBound = false;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

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

	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
	View view = getCurrentFocus();
	boolean ret = super.dispatchTouchEvent(event);

	if (view instanceof EditText) {
	    View w = getCurrentFocus();
	    int scrcoords[] = new int[2];
	    w.getLocationOnScreen(scrcoords);
	    float x = event.getRawX() + w.getLeft() - scrcoords[0];
	    float y = event.getRawY() + w.getTop() - scrcoords[1];

	    if (event.getAction() == MotionEvent.ACTION_UP 
	&& (x < w.getLeft() || x >= w.getRight() 
	|| y < w.getTop() || y > w.getBottom()) ) { 
	        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	    }
	}
	return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startActivityNawi(UserDto result) {
		if (mIsBound) {
			mTrailsService.setUser(result);
			Intent intent_name = new Intent();
			intent_name.setClass(this.getApplicationContext(),
					NaviActivity.class);
			intent_name.putExtra(USER_EXTRA, result);
			startActivity(intent_name);
		}
	}

}

class LoadingTask extends AsyncTask<String, Void, UserDto> {

	MainActivity context;

	public LoadingTask(MainActivity context) {

		this.context = context;
	}

	@Override
	protected UserDto doInBackground(String... params) {

		String url = Constants.SERVER_PATH
				+ "api/register/?login={login}&password={password}";

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("login", params[0]);
		urlVariables.put("password", params[1]);

		UserDto user = restTemplate.getForObject(url, UserDto.class,
				urlVariables);

		return user;
	}

	@Override
	protected void onPostExecute(UserDto result) {
		if (result == null) {
			Toast.makeText(context, "O NIE UDALO SIE", Toast.LENGTH_SHORT)
					.show();
		} else {
			context.startActivityNawi(result);
		}

	}
}
