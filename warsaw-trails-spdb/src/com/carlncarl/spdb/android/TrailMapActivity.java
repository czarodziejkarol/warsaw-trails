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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carlncarl.spdb.android.dto.CoordinateDto;
import com.carlncarl.spdb.android.dto.PointDto;
import com.carlncarl.spdb.android.dto.PointRateDto;
import com.carlncarl.spdb.android.dto.TrailDto;
import com.carlncarl.spdb.android.dto.UserDto;
import com.carlncarl.spdb.android.service.TrailsService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrailMapActivity extends Activity implements
		OnMyLocationChangeListener, OnMarkerClickListener {

	private TrailDto trail;
	private TrailsService mTrailsService;
	private RatingBar ratingBar;
	private LinearLayout rateLayout;
	private EditText textComment;
	private Button buttonRate;
	private PointDto selectedPoint;
	private TextView textDistance;
	private ImageView imageArrow;

	GoogleMap map;
	Integer visiblePoint = null;
	boolean zoomed = false;
	private PointDto currentPoint;
	private int pointIndex = 0;
	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mTrailsService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mTrailsService = ((TrailsService.TrailBinder) service).getService();
			setUpTrailOnMap();
		}
	};
	private boolean mIsBound;
	private ArrayList<MarkerOptions> markers;

	void doBindService() {
		bindService(new Intent(TrailMapActivity.this, TrailsService.class),
				connection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	protected void setUpTrailOnMap() {
		trail = mTrailsService.getCurrentTrail();
		PolylineOptions polyline = new PolylineOptions();
		polyline.color(Color.GREEN);
		for (CoordinateDto coor : trail.getPath()) {
			polyline.add(new LatLng(coor.getLatitude(), coor.getLongitude()));
		}
		currentPoint = trail.getPoints().get(0);
		map.addPolyline(polyline);
		markers = new ArrayList<MarkerOptions>();
		for (int i = 0; i < trail.getPoints().size(); i++) {
			PointDto point = trail.getPoints().get(i);
			MarkerOptions marker = new MarkerOptions();
			marker.title(point.getName());
			marker.position(new LatLng(point.getLatitude(), point
					.getLongitude()));
			marker.visible(true);
			marker.snippet(point.getDescription());
			marker.icon(IconChooser.chooseBitmap(i));

			markers.add(marker);
			map.addMarker(marker);
		}

	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(connection);
			mIsBound = false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_trail_map);
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);

		map = mapFragment.getMap();
		setUpMap();
		doBindService();
		setupActionBar();

		ratingBar = (RatingBar) findViewById(R.id.point_rate);
		rateLayout = (LinearLayout) findViewById(R.id.point_rate_lay);
		textComment = (EditText) findViewById(R.id.edit_text_comment);
		imageArrow = (ImageView) findViewById(R.id.image_angle);
		textDistance = (TextView) findViewById(R.id.text_distance);

		buttonRate = (Button) findViewById(R.id.button_rate_point);
		buttonRate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ratePoint();
			}
		});

		rateLayout.setVisibility(View.GONE);

	}

	private void setUpMap() {
		// LocationManager manager = (LocationManager)
		// this.getSystemService(Context.LOCATION_SERVICE);

		map.setMyLocationEnabled(true);
		LatLng latLng = new LatLng(52.2297700, 21.0117800);
		// gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng ));
		map.animateCamera(CameraUpdateFactory.zoomTo(11));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
		map.setOnMyLocationChangeListener(this);
		map.setOnMarkerClickListener(this);
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
		getMenuInflater().inflate(R.menu.trail_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void ratePoint() {
		buttonRate.setVisibility(View.INVISIBLE);

		PointRateDto rate = new PointRateDto();
		rate.setUserId(mTrailsService.getUser().getId());
		rate.setMainPointId(selectedPoint.getMainPointId());
		rate.setRate(Math.round(ratingBar.getRating()));
		rate.setComment(textComment.getText().toString());
		new RatePointTask().execute(rate);

	}

	private class RatePointTask extends
			AsyncTask<PointRateDto, Integer, PointRateDto> {

		@Override
		protected PointRateDto doInBackground(PointRateDto... arg0) {

			PointRateDto rate = arg0[0];

			String url = Constants.SERVER_PATH + "/api/vote-point";

			RestTemplate restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			rate = restTemplate.postForObject(url, rate, PointRateDto.class);

			return rate;
		}

		@Override
		protected void onPostExecute(PointRateDto result) {
			super.onPostExecute(result);

			if (result != null) {
				rateSucces(result);
			}

		}
	}

	public void rateSucces(PointRateDto result) {
		UserDto user = mTrailsService.getUser();
		if (user.getPointRates() == null) {
			user.setPointRates(new ArrayList<PointRateDto>());
		}
		user.getPointRates().add(result);
		realoadPointRating();
		rateLayout.setVisibility(View.GONE);
		buttonRate.setVisibility(View.VISIBLE);
	}

	private void realoadPointRating() {

		ratingBar.setRating(0f);
		textComment.setText("");
	}

	@Override
	public void onMyLocationChange(Location location) {
		if (location == null) {
			return;
		}
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());

		if (!zoomed) {
			zoomed = true;
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
		} else {
			map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
		}

		double distance = TrailMapActivity.distFrom(location.getLatitude(),
				location.getLongitude(), currentPoint.getLatitude(),
				currentPoint.getLongitude());
		if (distance < 10.0) {

			pointIndex++;
			if (pointIndex < trail.getPoints().size()) {
				currentPoint = trail.getPoints().get(pointIndex);
			} else {
				Toast.makeText(this, "Dziêkujemy za wspólnie sêdzony czas",
						Toast.LENGTH_LONG).show();
				textDistance.setText("To ju¿ koniec");
				map.setOnMyLocationChangeListener(null);
			}
		}
		
//		Matrix matrix=new Matrix();
//		Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
//		matrix.postRotate(getAngle(location.getLatitude(),
//				location.getLongitude(), currentPoint.getLatitude(),
//				currentPoint.getLongitude()));
//		Bitmap newBitmap = Bitmap.createBitmap(bmap, 0, 0,
//				bmap.getHeight(), bmap.getWidth(), matrix, true);
//		
//	//	imageArrow.setScaleType(ScaleType.MATRIX);   //required
//		imageArrow.setImageBitmap(newBitmap);
//		
		//imageArrow.setImageMatrix(matrix);
		textDistance.setText((int)distance + " metrów");

	}

	@Override
	public boolean onMarkerClick(Marker cmar) {
		for (int i = 0; i < markers.size(); i++) {
			MarkerOptions m = markers.get(i);
			if (cmar.getPosition().equals(cmar.getPosition())
					&& cmar.getTitle().equals(m.getTitle())) {
				if (this.visiblePoint == null) {
					rateLayout.setVisibility(View.VISIBLE);
					visiblePoint = i;
					selectedPoint = trail.getPoints().get(i);
				} else if (visiblePoint.intValue() != i) {
					realoadPointRating();
					visiblePoint = i;
					selectedPoint = trail.getPoints().get(i);
				} else {
					rateLayout.setVisibility(View.GONE);
					realoadPointRating();
					selectedPoint = null;
					visiblePoint = null;
				}
				break;
			}
		}
		return false;
	}

	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2) {
		double earthRadius = 6371000;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

	public static float getAngle(double lat1, double lng1, double lat2,
			double lng2) {
		double angle = 0;
		double deltaY = lng2 - lng1;
		double deltaX = lat2 - lat1;

		angle = Math.atan2(deltaY, deltaX) * 180.0 / Math.PI;

		return (float) angle;
	}

}
