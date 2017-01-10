package br.com.martinlabs.commons.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import br.com.martinlabs.commons.android.purchase.MLRenderer;

/**
 * Created by gil on 5/11/16.
 */
public class MultiLocation {

    private LocationManager _lm;
    private LocationChanged _onLocChange;
    private boolean _gps_enabled = false;
    private boolean _network_enabled = false;
    private boolean _locationGotten = false;
    private int _minTime;
    private float _minDistancia;
    private Context _ctx;

    public interface LocationChanged {
        void send(Location location);
    }

    DelegatorLocationListener locationListenerGps = new DelegatorLocationListener();
    DelegatorLocationListener locationListenerNetwork = new DelegatorLocationListener();

    public static MultiLocation get(Context context, LocationChanged onLocChange) {
        return new MultiLocation(context, onLocChange, 0, 0);
    }

    public static MultiLocation get(Context context, int minTime, LocationChanged onLocChange) {
        return new MultiLocation(context, onLocChange, minTime, 0);
    }

    public static MultiLocation get(Context context, LocationChanged onLocChange, float minDistancia) {
        return new MultiLocation(context, onLocChange, 0, minDistancia);
    }

    private MultiLocation(Context context, LocationChanged onLocChange, int minTime, float minDistancia) {
        _ctx = context;

        if (ActivityCompat.checkSelfPermission(_ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(_ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        _minTime = minTime;
        _minDistancia = minDistancia;

        //I use LocationResult callback class to pass location value from MyLocation to user code.
        _onLocChange = onLocChange;
        if (_lm == null) {
            _lm = (LocationManager) _ctx.getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try {
            _gps_enabled = _lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
        }

        try {
            _network_enabled = _lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
        }

        locationListenerGps.onLocChange = location -> OnLocationChanged(location);
        locationListenerNetwork.onLocChange = location -> OnLocationChanged(location);

        //don't start listeners if no provider is enabled
        if (!_gps_enabled && !_network_enabled) {
            return;
        }

        if (_gps_enabled) {
            _lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, _minTime, _minDistancia, locationListenerGps);
        }

        if (_network_enabled) {
            _lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, _minTime, _minDistancia, locationListenerNetwork);
        }

        MLRenderer.queue(() ->
        {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!_locationGotten) {
                GetLastLocation();
            }
        });
    }

    void OnLocationChanged(Location location) {
        _locationGotten = true;
        _onLocChange.send(location);
    }

    public void Stop() {
        if (ActivityCompat.checkSelfPermission(_ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(_ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        _lm.removeUpdates(locationListenerGps);
        _lm.removeUpdates(locationListenerNetwork);
    }

    void GetLastLocation() {
        if (ActivityCompat.checkSelfPermission(_ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(_ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location net_loc = null, gps_loc = null;

        if (_gps_enabled) {
            gps_loc = _lm.getLastKnownLocation (LocationManager.GPS_PROVIDER);
        }

        if (_network_enabled) {
            net_loc = _lm.getLastKnownLocation (LocationManager.NETWORK_PROVIDER);
        }

        //if there are both values use the latest one
        if(gps_loc != null && net_loc != null) {

            if (gps_loc.getTime() > net_loc.getTime()) {
                _onLocChange.send(gps_loc);
            } else {
                _onLocChange.send(net_loc);
            }

            return;
        }

        if(gps_loc != null) {
            _onLocChange.send(gps_loc);
            return;
        }

        if(net_loc!=null) {
            _onLocChange.send(net_loc);
            return;
        }
    }

    public class DelegatorLocationListener implements LocationListener
    {

        public LocationChanged onLocChange;

        @Override
        public void onLocationChanged(Location location) {
            if (onLocChange != null) {
                onLocChange.send (location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
