package a.sklepy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String wybor = null; // string pobrany z putExtra
    String currentCity=""; // string na moje miasto
    String wyszukiwanie; //ostateczny tytul wyszukiwania

    String location;
    String location1;
    String location2;
    String location3;
    String location4;
    String location5;
    String location6;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

                ////////////////otrzymanie danych o wyborze wyszukiwania
        Bundle bundle = getIntent().getExtras();
        wybor = bundle.getString("wybor");              ////odebrany wybor;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onZoom(View view){
        if(view.getId()==R.id.zoomIn){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId()==R.id.zoomOut){
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void changeType(View view){
        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Address onSearch(String miejsce, String wybor, Location mojaLokacja){
        Address najblizszyAdres=null;
        if(wybor.equals("sklep_spożywczy")==true) {
             location = "Biedronka " + miejsce;
             location1 = "lewiatan " + miejsce;
             location2 = "carrefour " + miejsce;
             location3 = "tesco " + miejsce;
             location4 = "żabka " + miejsce;
             location5 = "marcpol " + miejsce;
             location6 = "stokrotka " + miejsce;
        }
        if(wybor.equals("sklep_odzieżowy")==true){
             location = "United Colors of Benetton " + miejsce;
             location1 = "zara " + miejsce;
             location2 = "La fashion " + miejsce;
             location3 = "ecco " + miejsce;
             location4 = "butik " + miejsce;
             location5 = "D & M " + miejsce;
             location6 = "Click " + miejsce;
        }
        if(wybor.equals("zabytki")==true){
             location = "church " + miejsce;
             location1 = "castle " + miejsce;
             location2 = "monument " + miejsce;
             location3 = "abbey " + miejsce;
             location4 = "temple " + miejsce;
             location5 = "bunker " + miejsce;
             location6 = "main market " + miejsce;
        }
        if(wybor.equals("uniwersytety")==true){
            location = "colleage " + miejsce;
            location1 = "school " + miejsce;
            location2 = "univercity " + miejsce;
            location3 = "univercity " + miejsce;
            location4 = "univercity " + miejsce;
            location5 = "univercity " + miejsce;
            location6 = "univercity " + miejsce;
        }

        List<Address> addressList = null;
        try {
            Geocoder geocoder = new Geocoder(this);
            try {
                    addressList = geocoder.getFromLocationName(location, 5);
                    addressList.addAll(geocoder.getFromLocationName(location1, 5));
                    addressList.addAll(geocoder.getFromLocationName(location2, 5));
                    addressList.addAll(geocoder.getFromLocationName(location3, 5));
                    addressList.addAll(geocoder.getFromLocationName(location4, 5));
                    addressList.addAll(geocoder.getFromLocationName(location5, 5));
                    addressList.addAll(geocoder.getFromLocationName(location6, 5));

                    najblizszyAdres =compareLenght(mojaLokacja, addressList);

            } catch (IOException e) {
             //   TextView location_tf = (TextView)findViewById(R.id.miejsce_view); // deklaracja textview
             //   location_tf.setText("exeption");

                e.printStackTrace();
            }
            LatLng latLng;
            /*
            ArrayList<Address> address_list = new ArrayList<Address>();
            for (Address address : addressList){
                address_list.add(address);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
            */
        } catch (Exception e){
         //   TextView location_tf = (TextView)findViewById(R.id.miejsce_view); // deklaracja textview
         //   location_tf.setText("exeption");
        }
        return najblizszyAdres;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Address compareLenght(Location mojalokalizacja, List<Address> addressList){
        double lat1 = mojalokalizacja.getLatitude();
        double long1 = mojalokalizacja.getLongitude();
        Address najblizszyAdres=null;
        double lat2;
        double long2;
        double temp=1000;
        for (Address address : addressList){
            lat2 = address.getLatitude();
            long2 = address.getLongitude();
            if((Math.sqrt(Math.pow(lat1-lat2, 2) + Math.pow(long1-long2, 2))) < temp){
                temp = Math.sqrt(Math.pow(lat1-lat2, 2) + Math.pow(long1-long2, 2));
                najblizszyAdres = address;
            }
        }
        return najblizszyAdres;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ///////////////uprawnienie dostepu do mojej lokalizacji
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]
                        {
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        }, 10);
            }
            return;
        }
        mMap.setMyLocationEnabled(true);
        ///////////////Znalezienie mojej pozycji wspolrzednych
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();// Create a criteria object to retrieve provider
        String provider; // Get the name of the best provider
        provider = locationManager.getBestProvider(criteria,true);        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        /////////////wyswietlenie
        LatLng calculation_myloc = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(calculation_myloc).title("TUTAJ JESTES"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(calculation_myloc));

        //////////////ustalenie nazwy rejonu w ktorym sie znajduje
       // TextView location_tf = (TextView)findViewById(R.id.miejsce_view); // deklaracja textview
        double myLat=myLocation.getLatitude();
        double myLong=myLocation.getLongitude();
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(myLat, myLong, 1);
            if(addressList.size()>0){
                currentCity = addressList.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////stworzenie ostatecznego wyniku wyszukiwania
     //   location_tf.setText(wybor);
        Address foundAdress= onSearch(currentCity, wybor, myLocation);
        LatLng foundLoc = new LatLng(foundAdress.getLatitude(), foundAdress.getLongitude());
        mMap.addMarker(new MarkerOptions().position(foundLoc).title("TUTAJ IDZ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(foundLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
