package com.carlncarl.spdb.android;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.carlncarl.spdb.android.dto.PointDto;
import com.carlncarl.spdb.android.dto.TrailDto;

public class NewTrailFragment extends Fragment {

	Spinner spinner;
	EditText editTextName;
	EditText editTextDesc;
	ListView listNewPoints;
	LinearLayout layFirst;
	LinearLayout laySecond;
	Button buttonContinue;
	TrailDto trail;
	private NaviActivity parentActivity;

	public NewTrailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_new_trail, container,
				false);

		parentActivity = (NaviActivity) getActivity();
		
		spinner = (Spinner) view.findViewById(R.id.spinner_type);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.trails_types_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		layFirst = (LinearLayout) view.findViewById(R.id.lay_first_step);
		laySecond = (LinearLayout) view.findViewById(R.id.lay_second_step);
		
		editTextName = (EditText) view.findViewById(R.id.edit_trail_text_name);

		editTextDesc = (EditText) view
				.findViewById(R.id.edit_trail_text_description);
		listNewPoints = (ListView) view.findViewById(R.id.list_new_points);

		buttonContinue = (Button) view
				.findViewById(R.id.button_trail_continue);
		buttonContinue.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				submitForm();
			}
		});
		
		Button buttonSave = (Button) view.findViewById(R.id.button_trail_save);
		buttonSave.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				saveTrail();
			}
		});

		return view;
	}

	protected void saveTrail() {
		new NewTrailTask().execute(trail);
	}

	protected void submitForm() {
		TrailDto trail = new TrailDto(editTextName.getText().toString(), editTextDesc
				.getText().toString(), (String) spinner.getSelectedItem(), parentActivity.getUser());
		Intent intent = new Intent(getActivity(), NewTrailActivity.class);

		intent.putExtra(NaviActivity.SELECTED_MAIN_ITEM,
				((NaviActivity) getActivity()).getSelectedPosition());
		intent.putExtra(NewTrailActivity.NEW_TRAIL_KEY, trail);

		startActivityForResult(intent, NaviActivity.NEW_TRAIL_REQUEST);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == NaviActivity.NEW_TRAIL_REQUEST) {
			// Make sure the request was successful
			if (resultCode == NaviActivity.RESULT_OK) {
				layFirst.setVisibility(View.GONE);
				laySecond.setVisibility(View.VISIBLE);
				
				trail = (TrailDto) data
						.getSerializableExtra(NewTrailActivity.NEW_TRAIL_KEY);
				
				editTextName.setText(trail.getName());
				editTextDesc.setText(trail.getDescription());
				
				ArrayAdapter<PointDto> mAdapter = new ArrayAdapter<PointDto>(getActivity(),
						android.R.layout.simple_list_item_1, android.R.id.text1,
						trail.getPoints());
				
				listNewPoints.setAdapter(mAdapter);
			}
		}
	}

	private class NewTrailTask extends AsyncTask<TrailDto, Integer, TrailDto> {

		@Override
		protected TrailDto doInBackground(TrailDto... arg0) {
			
			TrailDto trail = arg0[0];
			String url = Constants.SERVER_PATH + "api/add-trail/";

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			//new RestTemplate(org.springframework.http.client.support.HttpRequestWrapper)
			// Add the String message converter
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());
			// Make the HTTP GET request, marshalin	g the response to a String

			// String result = restTemplate.getForObject(url, String.class,
			// "Android");
			//System.setProperty("http.keepAlive", "false");
			TrailDto trailDto = null;
			try{
				trailDto = restTemplate.postForObject(url, trail, TrailDto.class);
			} catch(Exception e){
				System.err.println(e.getMessage() + e.getStackTrace().toString());
			}
			return trailDto;
		}

		@Override
		protected void onPostExecute(TrailDto result) {
			super.onPostExecute(result);

			if (result != null) {
				trailSaved();
			}

		}
	}

	public void trailSaved() {
		Toast.makeText(getActivity(), "ZAPISANO! I DODANO", Toast.LENGTH_SHORT)
				.show();
		buttonContinue.setVisibility(View.GONE);
	}

}
