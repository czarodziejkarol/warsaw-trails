package com.carlncarl.spdb.android;

import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.carlncarl.spdb.android.dto.Point;
import com.carlncarl.spdb.android.dto.Trail;
import com.carlncarl.spdb.android.dummy.DummyContent;
import com.carlncarl.spdb.android.dummy.DummyContent.DummyItem;

public class NewTrailFragment extends Fragment {

	Spinner spinner;
	EditText editTextName;
	EditText editTextDesc;
	ListView listNewPoints;
	LinearLayout layFirst;
	LinearLayout laySecond;
	Button buttonContinue;
	Trail trail;

	public NewTrailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_new_trail, container,
				false);

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
		Trail trail = new Trail(editTextName.getText().toString(), editTextDesc
				.getText().toString(), (String) spinner.getSelectedItem());
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
				
				trail = (Trail) data
						.getSerializableExtra(NewTrailActivity.NEW_TRAIL_KEY);
				new NewTrailTask().execute(trail);
				editTextName.setText(trail.getName());
				editTextDesc.setText(trail.getDescription());
				
				ArrayAdapter<Point> mAdapter = new ArrayAdapter<Point>(getActivity(),
						android.R.layout.simple_list_item_1, android.R.id.text1,
						trail.getPoints());
				
				listNewPoints.setAdapter(mAdapter);
				//wyœwietlenie danych
				
				
				
			}
		}
	}

	private class NewTrailTask extends AsyncTask<Trail, Integer, Trail> {

		@Override
		protected Trail doInBackground(Trail... arg0) {
			Trail trail = arg0[0];
			
			
			return trail;
		}

		@Override
		protected void onPostExecute(Trail result) {
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
