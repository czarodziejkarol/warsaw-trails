package com.carlncarl.spdb.android.dto;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Trail implements Serializable {

	private static final long serialVersionUID = 3432619381650070831L;

	private String name;
	private String description;
	private String type;
	private ArrayList<Point> points;

	public Trail(String name, String description, String type) {
		this.name = name;
		this.description = description;
		this.type = type;
		setPoints(new ArrayList<Point>());
	}

	public Trail(Parcel in) {
		this.name = in.readString();
		this.description = in.readString();
		this.type = in.readString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(name);
		out.writeString(description);
		out.writeString(type);
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public static final Parcelable.Creator<Trail> CREATOR = new Parcelable.Creator<Trail>() {
		public Trail createFromParcel(Parcel in) {
			return new Trail(in);
		}

		public Trail[] newArray(int size) {
			return new Trail[size];
		}
	};

}
