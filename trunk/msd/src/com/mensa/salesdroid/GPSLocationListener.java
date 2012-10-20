package com.mensa.salesdroid;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class GPSLocationListener implements LocationListener {
	OnGPSLocationChanged onGPSLocationChanged;
	
	public void setOnGPSLocationChanged(OnGPSLocationChanged listener){
		onGPSLocationChanged = listener;
	}
	
	public interface OnGPSLocationChanged {
		public abstract void OnLatAndLongChanged(double longitude, double latitude);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if (onGPSLocationChanged!=null){
			double longitude = location.getLatitude();
			double latitude  = location.getLongitude();
			onGPSLocationChanged.OnLatAndLongChanged(longitude, latitude);
		}
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
