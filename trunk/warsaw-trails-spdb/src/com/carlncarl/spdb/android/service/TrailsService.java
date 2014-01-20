package com.carlncarl.spdb.android.service;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.carlncarl.spdb.android.dto.TrailDto;
import com.carlncarl.spdb.android.dto.UserDto;

public class TrailsService extends Service {

	public static final String USER_DTO_EXTRA = "USER_EXTRA";

	private final IBinder mBinder = new TrailBinder();

	private static UserDto user;
	
	private static TrailDto currentTrail;;

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class TrailBinder extends Binder {
		public TrailsService getService() {
			return TrailsService.this;
		}
	}
	
	public void setUser(UserDto user){
		TrailsService.user = user;
	}

	public UserDto getUser() {
		return user;
	}

	public TrailDto getCurrentTrail() {
		return currentTrail;
	}

	public void setCurrentTrail(TrailDto currentTrail) {
		TrailsService.currentTrail = currentTrail;
	}

}
