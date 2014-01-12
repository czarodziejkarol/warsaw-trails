package com.carlncarl.spdb.android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.carlncarl.spdb.android.dto.Trail;

public class NewTrailFragment extends Fragment {

	Spinner spinner;
	EditText editTextName;
	EditText editTextDesc;

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

		editTextName = (EditText) view.findViewById(R.id.edit_trail_text_name);

		editTextDesc = (EditText) view.findViewById(R.id.edit_trail_text_description);
		
		Button buttonContinue = (Button) view
				.findViewById(R.id.button_trail_continue);
		buttonContinue.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				submitForm();
			}
		});

		return view;
	}

	protected void submitForm() {
		Trail trail = new Trail(editTextName.getText().toString(), editTextDesc
				.getText().toString(), (String) spinner.getSelectedItem());
		Intent intent = new Intent(getActivity(), NewTrailActivity.class);
		
		intent.putExtra(NaviActivity.SELECTED_MAIN_ITEM, ((NaviActivity)getActivity()).getSelectedPosition());
		intent.putExtra(NewTrailActivity.NEW_TRAIL_BUNDLE_KEY, trail);
		
		startActivity(intent);
	}

}
