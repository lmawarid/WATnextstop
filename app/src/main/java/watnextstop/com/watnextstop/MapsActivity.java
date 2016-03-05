package watnextstop.com.watnextstop;

import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Marker start;
    private Marker end;
    boolean startlastupdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        //43.4667, 80.5167, 43.4500, 80.4833

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String key = getResources().getString(R.string.google_maps_key);
        try {
            JSONObject json = new JSONObject();
            json = LocationStuff.getDirections(43.4667, 80.5167, 43.4500, 80.4833,key);}
        catch (MalformedURLException e) {}
        catch (IOException i) {}
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            mMap.setMyLocationEnabled(true);
        }
        catch (SecurityException se){
            System.out.println("Permission problems for getting location");
        }
        mMap.setOnMapClickListener(this);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void onMapClick(LatLng point){
        if(startlastupdated){ //update the end pin
            start.remove();
            start = mMap.addMarker(new MarkerOptions().position(point).title("Start").draggable(true));
        }
        else{ //update the start pin
            end.remove();
            end = mMap.addMarker(new MarkerOptions().position(point).title("Destination").draggable(true));
        }
        startlastupdated  = !startlastupdated;
    }

}
