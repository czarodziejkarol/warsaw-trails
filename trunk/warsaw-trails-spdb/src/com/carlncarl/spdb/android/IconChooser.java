package com.carlncarl.spdb.android;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class IconChooser {
	public static BitmapDescriptor chooseBitmap(int id){
		BitmapDescriptor icon = null;
		switch (id) {
		case 0:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m1);
			break;
		case 1:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m2);
			break;
		case 2:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m3);
			break;
		case 3:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m4);
			break;
		case 4:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m5);
			break;
		case 5:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m6);
			break;
		case 6:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m7);
			break;
		case 8:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m8);
			break;
		case 9:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m9);
			break;
		case 10:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m10);
			break;
		case 11:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.m11);
			break;
		default:
			icon = BitmapDescriptorFactory.defaultMarker(20f);
			break;
		}
		
		return icon;
	}
}
