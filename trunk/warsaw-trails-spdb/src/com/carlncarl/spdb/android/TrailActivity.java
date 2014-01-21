package com.carlncarl.spdb.android;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.carlncarl.spdb.android.dto.TrailDto;
import com.carlncarl.spdb.android.service.TrailsService;

public class TrailActivity extends Activity {

	private int position;
	private TrailDto trail;
	
	private LinearLayout loadingLayout;
	private LinearLayout trailListLayout;
	private TextView trailName;
	private TextView trailDesc;
	private ListView trailList;
	
	private TrailsService mTrailsService;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mTrailsService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mTrailsService = ((TrailsService.TrailBinder) service).getService();
			loadTrail();
		}
	};
	private boolean mIsBound;

	void doBindService() {
		bindService(new Intent(TrailActivity.this, TrailsService.class),
				connection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	protected void loadTrail() {
		if (loadedFromBack) {
			this.trail = mTrailsService.getCurrentTrail();
			fillItems();
		}
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(connection);
			mIsBound = false;
		}
	}

	private boolean loadedFromBack = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doBindService();
		setContentView(R.layout.activity_trail);
		loadingLayout = (LinearLayout) findViewById(R.id.trail_desc_load);
		trailListLayout = (LinearLayout) findViewById(R.id.trail_desc_list);
		trailName = (TextView) findViewById(R.id.trail_name) ;
		trailDesc = (TextView) findViewById(R.id.trail_desc);
		trailList = (ListView) findViewById(R.id.trail_list);

		// Show the Up button in the action bar.
		setupActionBar();

		Intent intent = getIntent();
		position = intent.getIntExtra(NaviActivity.SELECTED_MAIN_ITEM, 0);

		String id = intent.getStringExtra(NaviActivity.SELECTED_TRAIL_ID);

		Button buttonFolow = (Button) findViewById(R.id.button_go);
		buttonFolow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				exploreMap();
			}
		});
		if (id != null) {
			loadedFromBack = true;
			new LoadTrailTask().execute(id);
		}
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

			String id = arg0[0];
			String url = Constants.SERVER_PATH + "api/trail?id={id}";

			RestTemplate restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());

			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			TrailDto trail = restTemplate.getForObject(url, TrailDto.class, id);
			return trail;
		}

		@Override
		protected void onPostExecute(TrailDto result) {
			super.onPostExecute(result);

			if (result != null) {
				trailLoaded(result);
			}

		}
	}

	public void trailLoaded(TrailDto result) {
		this.trail = result;
		mTrailsService.setCurrentTrail(trail);

		fillItems();
	}

	private void fillItems() {
		// TODO Auto-generated method stub

	}

	public void exploreMap() {
		Intent intent = new Intent(this, TrailMapActivity.class);
		startActivity(intent);
	}

}
