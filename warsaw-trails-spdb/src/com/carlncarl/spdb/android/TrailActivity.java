package com.carlncarl.spdb.android;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.carlncarl.spdb.android.dto.TrailDto;

public class TrailActivity extends Activity {

	private int position;
	private TrailDto trail;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		position = intent.getIntExtra(NaviActivity.SELECTED_MAIN_ITEM, 0);
		
		String id = intent.getStringExtra(NaviActivity.SELECTED_TRAIL_ID);
		new LoadTrailTask().execute(id);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			
			Intent intent = getParentActivityIntent();
			intent.putExtra(NaviActivity.SELECTED_MAIN_ITEM, position);
			NavUtils.navigateUpTo(this, intent);
			
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private class LoadTrailTask extends AsyncTask<String, Integer, TrailDto> {

		@Override
		protected TrailDto doInBackground(String... arg0) {

			String id  = arg0[0];
			String url = Constants.SERVER_PATH + "api/trail?id={id}";

			RestTemplate restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());

			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());
			
			TrailDto trail = restTemplate.getForObject(url, TrailDto.class,id);
			return trail;
		}

		@Override
		protected void onPostExecute(TrailDto result) {
			super.onPostExecute(result);

			if (result != null) {
				fillItems(result);
			}

		}
	}


	public void fillItems(TrailDto result) {
		this.trail = result;
	}

}
