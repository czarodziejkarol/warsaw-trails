package com.carlncarl.spdb.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.carlncarl.spdb.android.TrailFragment.OnFragmentInteractionListener;
import com.carlncarl.spdb.android.dto.UserDto;
import com.carlncarl.spdb.android.service.TrailsService;

public class NaviActivity extends Activity implements
		OnFragmentInteractionListener {
	public static final String SELECTED_MAIN_ITEM = "main_item_extra";

	public static final int NEW_TRAIL_REQUEST = 1;

	public static final String SELECTED_TRAIL_ID = "trail_id_extra";

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mTitles;
	private int selectedPosition = 0;

	
	private UserDto user;

	
	private TrailsService mTrailsService;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mTrailsService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mTrailsService = ((TrailsService.TrailBinder) service).getService();
		}
	};
	private boolean mIsBound;

	void doBindService() {
		bindService(new Intent(NaviActivity.this, TrailsService.class),
				connection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(connection);
			mIsBound = false;
		}
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SELECTED_MAIN_ITEM, selectedPosition);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doBindService();
		setContentView(R.layout.activity_navi);
		user = (UserDto) getIntent().getSerializableExtra(
				MainActivity.USER_EXTRA);

		mTitle = mDrawerTitle = getTitle();
		mTitles = getResources().getStringArray(R.array.navi_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.draver_list_item, mTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState != null) {
			selectedPosition = savedInstanceState.getInt(SELECTED_MAIN_ITEM);
		} else {
			selectedPosition = getIntent().getIntExtra(SELECTED_MAIN_ITEM, 0);
		}
		selectItem(selectedPosition);
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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		System.out.print(drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		this.selectedPosition = position;
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new TrailFragment();
			break;
		case 1:
			fragment = new NewTrailFragment();
			break;
		default:
			break;
		}

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		mDrawerList.setItemChecked(position, true);
		setTitle(mTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onFragmentInteraction(String id) {
		System.out.println(id);

		Intent intent = new Intent(this, TrailActivity.class);
		intent.putExtra(SELECTED_TRAIL_ID, id);
		intent.putExtra(SELECTED_MAIN_ITEM, selectedPosition);

		startActivity(intent);
	}

	public int getSelectedPosition() {
		return this.selectedPosition;
	}

	public UserDto getUser() {

		return mTrailsService.getUser();
	}

}
