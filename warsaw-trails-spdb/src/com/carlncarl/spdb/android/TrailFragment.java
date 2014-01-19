package com.carlncarl.spdb.android;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.carlncarl.spdb.android.dto.TrailDto;

public class TrailFragment extends Fragment implements
		AbsListView.OnItemClickListener {

	private OnFragmentInteractionListener mListener;


	private AbsListView mListView;

	private ListAdapter mAdapter;

	public TrailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.trails = new ArrayList<TrailDto>();
		mAdapter = new ArrayAdapter<TrailDto>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, trails);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trail, container, false);

		
		
		mListView = (AbsListView) view.findViewById(android.R.id.list);
		((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

		
		mListView.setOnItemClickListener(this);

		new LoadTrailsTask().execute("");
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (null != mListener) {
			mListener.onFragmentInteraction(trails.get(position).getId()
					.toString());
		}
	}

	/**
	 * The default content for this Fragment has a TextView that is shown when
	 * the list is empty. If you would like to change the text, call this method
	 * to supply the text it should use.
	 */
	public void setEmptyText(CharSequence emptyText) {
		View emptyView = mListView.getEmptyView();

		if (emptyText instanceof TextView) {
			((TextView) emptyView).setText(emptyText);
		}
	}

	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(String id);
	}

	private class LoadTrailsTask extends AsyncTask<String, Integer, TrailDto[]> {

		@Override
		protected TrailDto[] doInBackground(String... arg0) {

			String url = Constants.SERVER_PATH + "api/new-trails/";

			RestTemplate restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());
			
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			TrailDto[] list = restTemplate.getForObject(url, TrailDto[].class);
			return list;
		}

		@Override
		protected void onPostExecute(TrailDto[] result) {
			super.onPostExecute(result);

			if (result != null) {
				showLastTrails(result);
			}

		}
	}

	List<TrailDto> trails;

	public void showLastTrails(TrailDto[] result) {

		for (TrailDto trailDto : result) {
			trails.add(trailDto);
		}
		((ArrayAdapter)mAdapter).notifyDataSetChanged();

	}
}
