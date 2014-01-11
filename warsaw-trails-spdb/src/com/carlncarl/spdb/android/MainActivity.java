package com.carlncarl.spdb.android;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText editText;
	Button buttonLogOn;
	MainActivity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.context = this;
		editText = (EditText) (EditText) findViewById(R.id.editTextUsername);
		editText = (EditText) (EditText) findViewById(R.id.editTextPassword);

		buttonLogOn = (Button) findViewById(R.id.buttonLogOn);
		buttonLogOn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new LoadingTask(context).execute("");
				
			}
		});
		//Remove title bar
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startActivityNawi(){ 
		Intent intent_name = new Intent();
		intent_name.setClass(this.getApplicationContext(),NaviActivity.class);
		startActivity(intent_name);
	}

}

class LoadingTask extends AsyncTask<Object, Integer, String> {

	MainActivity context;

	public LoadingTask(MainActivity context) {

		this.context = context;
	}

	@Override
	protected String doInBackground(Object... params) {
		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		// if (result == null) {
		//
		// } else {
		//
		// }
		context.startActivityNawi();
		
//		super.onPostExecute(result);
//
//		context.startActivity(new Intent(context, NaviActivity.class));
//		Intent intent = new Intent(context ,NaviActivity.class);
//      
//        context.startActivity(intent);
	}
}
