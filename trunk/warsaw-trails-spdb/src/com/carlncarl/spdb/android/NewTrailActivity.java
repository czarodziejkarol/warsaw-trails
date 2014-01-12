package com.carlncarl.spdb.android;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.carlncarl.spdb.android.dto.Point;
import com.carlncarl.spdb.android.dto.Trail;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class NewTrailActivity extends Activity implements
		OnMyLocationChangeListener, OnMapClickListener {

	public static final String NEW_TRAIL_BUNDLE_KEY = "new_trail";
	private int position;
	
	LinearLayout newPointOrEndTrailLayout;
	LinearLayout addPointLayout;
	
	EditText textPointName;
	EditText textPointDesc;
	
	GoogleMap map;
	Trail trail;
	LinkedList<Location> locations;
	PolylineOptions polyline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		locations = new LinkedList<Location>();
		setContentView(R.layout.activity_new_trail);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		setUpMap();

		Button buttonNewPoint = (Button) findViewById(R.id.button_new_point);
		buttonNewPoint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showAddPointLayout();
			}
		});

		Button buttonEndTrail = (Button) findViewById(R.id.button_end_trail);
		buttonEndTrail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				endTrail();
			}
		});
		
		Button buttonAddPoint = (Button) findViewById(R.id.button_add_point);
		buttonAddPoint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addNewPoint();
			}
		});
		
		Button buttonCancelAdding = (Button) findViewById(R.id.button_add_point);
		buttonCancelAdding.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				cancelAddingPoint();
			}
		});
		
		textPointName = (EditText) findViewById(R.id.edit_point_name);
		textPointDesc = (EditText) findViewById(R.id.edit_point_description);
		
		newPointOrEndTrailLayout = (LinearLayout) findViewById(R.id.new_or_end_layout);
		newPointOrEndTrailLayout.setVisibility(View.VISIBLE);
		addPointLayout = (LinearLayout) findViewById(R.id.add_point_layout);
		addPointLayout.setVisibility(View.GONE);

		Intent intent = getIntent();
		position = intent.getIntExtra(NaviActivity.SELECTED_MAIN_ITEM, 0);
		trail = (Trail) intent.getSerializableExtra(NEW_TRAIL_BUNDLE_KEY);

		setupActionBar();
	}

	protected void cancelAddingPoint() {
		newPointOrEndTrailLayout.setVisibility(View.VISIBLE);
		addPointLayout.setVisibility(View.GONE);
	}

	protected void addNewPoint() {
		Location loc = map.getMyLocation();
		
		Point point = new Point();
		point.setName(this.textPointName.getText().toString());
		this.textPointName.setText("");
		point.setDescription(this.textPointDesc.getText().toString());
		this.textPointDesc.setText("");
		point.setLatitude(loc.getLatitude());
		point.setLongitude(loc.getLongitude());
		
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(point.getLatitude(), point.getLongitude()));
		trail.getPoints().add(point);
		map.addMarker(marker );
		
		cancelAddingPoint();
	}

	protected void showAddPointLayout() {
		newPointOrEndTrailLayout.setVisibility(View.GONE);
		addPointLayout.setVisibility(View.VISIBLE);
	}

	protected void endTrail() {
		navigateUpTo(true);
	}

	private void setUpMap() {
		map.setMyLocationEnabled(true);
		LatLng latLng = new LatLng(52.2297700, 21.0117800);
		// gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng ));
		map.animateCamera(CameraUpdateFactory.zoomTo(11));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
		map.setOnMyLocationChangeListener(this);
		map.setOnMapClickListener(this);
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
		getMenuInflater().inflate(R.menu.new_trail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			navigateUpTo(false);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void navigateUpTo(boolean trail) {
		Intent intent = getParentActivityIntent();
		intent.putExtra(NaviActivity.SELECTED_MAIN_ITEM, position);
		if (trail) {
			intent.putExtra(NEW_TRAIL_BUNDLE_KEY, this.trail);
		}
		NavUtils.navigateUpTo(this, intent);
	}

	@Override
	public void onMyLocationChange(Location location) {
		if (location.getAccuracy() > 10) {
			return;
		}
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		if (polyline == null) {
			polyline = new PolylineOptions();
			polyline.color(Color.GREEN);
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21));
		} else {
			map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
		}

		polyline.add(latLng);

		map.addPolyline(polyline);

		locations.add(location);

	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

}
