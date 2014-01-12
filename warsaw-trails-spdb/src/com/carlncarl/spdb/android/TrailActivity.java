package com.carlncarl.spdb.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class TrailActivity extends Activity {

	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		position = intent.getIntExtra(NaviActivity.SELECTED_MAIN_ITEM, 0);
		
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
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			Intent intent = getParentActivityIntent();
			intent.putExtra(NaviActivity.SELECTED_MAIN_ITEM, position);
			NavUtils.navigateUpTo(this, intent);
		//	NavUtils.navigateUpFromSameTask(this);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
