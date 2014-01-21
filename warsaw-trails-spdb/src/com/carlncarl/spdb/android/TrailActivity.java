package com.carlncarl.spdb.android;

import java.util.ArrayList;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.carlncarl.spdb.android.dto.PointDto;
import com.carlncarl.spdb.android.dto.TrailDto;
import com.carlncarl.spdb.android.dto.TrailRateDto;
import com.carlncarl.spdb.android.dto.UserDto;
import com.carlncarl.spdb.android.service.TrailsService;

public class TrailActivity extends Activity {

	private int position;
	private TrailDto trail;

	private LinearLayout loadingLayout;
	private LinearLayout trailListLayout;
	private LinearLayout trailRateLayout;
	private TextView trailName;
	private TextView trailDesc;
	private TextView trailComent;
	
	private TextView trailType;
	private TextView trailTime;
	private ListView trailList;
	private ListView trailComentsList;
	private RatingBar trailRating;
	private TrailsService mTrailsService;
	private Button buttonRate;

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

	private boolean loadedFromBack = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_trail);
		loadingLayout = (LinearLayout) findViewById(R.id.trail_desc_load);
		trailListLayout = (LinearLayout) findViewById(R.id.trail_desc_list);
		trailRateLayout = (LinearLayout) findViewById(R.id.trail_rate_lay);
		trailName = (TextView) findViewById(R.id.trail_name);
		trailDesc = (TextView) findViewById(R.id.trail_desc);
		trailComent = (TextView) findViewById(R.id.edit_text_trailcomment);
		
		trailType = (TextView) findViewById(R.id.text_type_trail);
		trailTime = (TextView) findViewById(R.id.text_time);
		
		trailRating = (RatingBar) findViewById(R.id.trail_rate);
		trailList = (ListView) findViewById(R.id.trail_list);
		trailComentsList = (ListView) findViewById(R.id.trail_coments_list);
		buttonRate = (Button) findViewById(R.id.button_rate_trail);
		// Show the Up button in the action bar.
		buttonRate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				rateTrail();
			}
		});
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
			loadedFromBack = false;
			new LoadTrailTask().execute(id);

		}
		doBindService();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

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
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
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

		trailName.setText(trail.getName());
		trailDesc.setText(trail.getDescription());
		trailType.setText(trail.getType());
		trailTime.setText((trail.getEndTime().getTime() - trail.getStartTime().getTime())/(1000*60) +" minut");
		ArrayAdapter<PointDto> mAdapter = new ArrayAdapter<PointDto>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				trail.getPoints());

		trailList.setAdapter(mAdapter);

		ArrayAdapter<TrailRateDto> adapter = new ArrayAdapter<TrailRateDto>(
				this, android.R.layout.simple_list_item_1, android.R.id.text1,
				trail.getTrailRates());
		trailComentsList.setAdapter(adapter);

		loadingLayout.setVisibility(View.GONE);
		trailListLayout.setVisibility(View.VISIBLE);
		trailRateLayout.setVisibility(View.VISIBLE);

	}

	public void exploreMap() {
		Intent intent = new Intent(this, TrailMapActivity.class);
		startActivity(intent);
	}

	protected void rateTrail() {
		buttonRate.setVisibility(View.INVISIBLE);

		TrailRateDto rate = new TrailRateDto();
		rate.setUserId(mTrailsService.getUser().getId());
		rate.setTrailId(this.trail.getId());
		rate.setRate(Math.round(trailRating.getRating()));
		rate.setComment(trailComent.getText().toString());
		new RateTrailTask().execute(rate);

	}

	private class RateTrailTask extends
			AsyncTask<TrailRateDto, Integer, TrailRateDto> {

		@Override
		protected TrailRateDto doInBackground(TrailRateDto... arg0) {

			TrailRateDto rate = arg0[0];

			String url = Constants.SERVER_PATH + "/api/vote-trail";

			RestTemplate restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			rate = restTemplate.postForObject(url, rate, TrailRateDto.class);

			return rate;
		}

		@Override
		protected void onPostExecute(TrailRateDto result) {
			super.onPostExecute(result);

			if (result != null) {
				rateSucces(result);
			}

		}
	}

	public void rateSucces(TrailRateDto result) {
		UserDto user = mTrailsService.getUser();
		if (user.getTrailRates() == null) {
			user.setTrailRates(new ArrayList<TrailRateDto>());
		}
		user.getTrailRates().add(result);
		buttonRate.setVisibility(View.VISIBLE);
	}

}
