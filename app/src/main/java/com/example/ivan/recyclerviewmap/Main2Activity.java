package com.example.ivan.recyclerviewmap;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ivan.recyclerviewmap.test.IActivity;
import com.example.ivan.recyclerviewmap.test.IBaseView;
import com.example.ivan.recyclerviewmap.test.TestView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,IActivity {

    public GoogleMap mMap;
    private LinearLayout linearLayout;
    /**
     * The GoogleApiClient is used for connection to the GoogleApi's provided in the Google Play services library     *
     * The Google API Client provides a common entry point to all the Google Play services and manages
     * the network connection between the user's device and each Google service.
     */
    private GoogleApiClient mLocationClient;
    /**
     * Geocoding is the process of transforming a description of a location—such as a pair of coordinates,
     * an address, or a name of a place—to a location on the earth's surfac
     */
    private Geocoder geocoder;

    private LocationListener mListener;
    private Location currentLocation;
    private Marker marker;
    private RecyclerView myList;
    private List<Event> events;
    private LinearLayoutManager layoutManager;
    private int firstVisiblePosition;
    private MyAdapter adapter;
    private Marker previousMarker;
    private Button btn;
    private LinearLayout linearLayouta;
    private RelativeLayout relativeLayout;
    private IBaseView currentFragment;

    private static final double
            SEATTLE_LAT = 47.60621,
            SEATTLE_LNG =-122.33207,
            SYDNEY_LAT = -33.867487,
            SYDNEY_LNG = 151.20699,
            NEWYORK_LAT = 40.714353,
            NEWYORK_LNG = -74.005973,
            SOFIA_LAT = 42.6977,
            SOFIA_LNG = 23.3219;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        geocoder = new Geocoder(getApplicationContext());
        initMap();

        events = new ArrayList<>();
        events.add(new Event("Seattke","Pesho",R.drawable.hongkong_photo,SEATTLE_LAT,SEATTLE_LNG));
        events.add(new Event("Sydney","Gosho",R.drawable.london_photo,SYDNEY_LAT,SYDNEY_LNG));
        events.add(new Event("New York","Marto",R.drawable.paris_photo,NEWYORK_LAT,NEWYORK_LNG));
        events.add(new Event("Sofia","Ivan",R.drawable.sf_photo,SOFIA_LAT,SOFIA_LNG));
        events.add(new Event("Sofia1","Ivan",R.drawable.hongkong_photo,42.657934,23.290568));
        events.add(new Event("Sofia2","Ivan",R.drawable.paris_photo,42.648595,23.403316));

        linearLayouta = (LinearLayout) findViewById(R.id.fragment_container);
       // btn = (Button) findViewById(R.id.show_event);
        btn.setOnClickListener(v->{
            openFragment(TestView.class,new Bundle());


        });



        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.map_container);
        layoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myList = (RecyclerView) findViewById(R.id.my_recycler_view);
        //adapter = new MyAdapter(this,events);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(myList);
        myList.setAdapter(adapter);
        myList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(myList.getContext(),
                layoutManager.getOrientation());
        myList.addItemDecoration(dividerItemDecoration);


        mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationClient.connect();


        myList.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                if (dx < 0) {
                    gotoLocation(events.get(firstVisiblePosition).getLatitude(),events.get(firstVisiblePosition).getLongitude(),12);
                } else {
                    gotoLocation(events.get(firstVisiblePosition).getLatitude(),events.get(firstVisiblePosition).getLongitude(),12);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    //Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE ) {
                } else {
                    // Do something
                }
            }
        });



    }



    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                showClickedMarker(marker);
                return  false;

            }
        });


    }

    private void showClickedMarker(Marker marker){
        if(previousMarker!=null){
            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        previousMarker=marker;
        for (int i =0 ; i<events.size();i++){
            if (marker.getTitle().equalsIgnoreCase(events.get(i).getName())){
                layoutManager.scrollToPosition(i);
                break;
            }
        }
    }

    public void gotoLocation(double lan, double lng, float zoom) {
        LatLng latLng = new LatLng(lan, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);
        try {
            showAllEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(currentLocation!=null) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            if (marker != null) {
                marker.remove();
            }

            gotoLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),15);

           /* List<Address> listAddresses = null;
            try {
                listAddresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String state = listAddresses.get(0).getAdminArea();

            MarkerOptions options = new MarkerOptions()
                    .title(state)
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            options.snippet(listAddresses.get(0).getCountryName());

            marker = mMap.addMarker(options);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.moveCamera(update);*/


        }




    }

    private void showAllEvents() throws IOException {
        if (marker != null) {
            marker.remove();
        }

        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());



        List<LatLng> listAddresses = new ArrayList<>();
        for(int i=0;i<events.size();i++){
            double latitude = events.get(i).getLatitude();
            double longitude = events.get(i).getLongitude();
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            listAddresses.add(new LatLng(latitude,longitude));

            MarkerOptions options = new MarkerOptions()
                    .title(events.get(i).getName())
                    .position(listAddresses.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            options.snippet(addresses.get(0).getCountryName());


            marker = mMap.addMarker(options);
            mMap.addMarker(options);



        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public <View extends IBaseView> void openFragment(Class<View> clazz, Bundle arguments) {

        openFragment(clazz, arguments, true);
    }

    @Override
    public <View extends IBaseView> void openFragment(Class<View> clazz, Bundle arguments, boolean addToBackStack) {

        String name = clazz.getCanonicalName();
        if (name == null) {
            return;
        }
        if (currentFragment != null && currentFragment.getClass().getCanonicalName().equals(name)) {
            return;
        }
        if (getSupportFragmentManager().findFragmentByTag(name) != null) {
            currentFragment = popBackStack(getSupportFragmentManager(), name, arguments);
        } else {
            Fragment fragmentToOpen = Fragment.instantiate(this, name, arguments);
            if (addToBackStack) {
                linearLayouta.setVisibility(android.view.View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragmentToOpen, name)
                        .addToBackStack(name).commitAllowingStateLoss();
            } else {
                linearLayouta.setVisibility(android.view.View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragmentToOpen, name)
                        .addToBackStack(name).commitAllowingStateLoss();
            }
            getSupportFragmentManager().executePendingTransactions();
            currentFragment = (IBaseView) fragmentToOpen;
        }
    }

    protected IBaseView popBackStack(FragmentManager fragmentManager, String fragmentViewName, Bundle args) {

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentViewName);
        if (fragment != null && fragment.getArguments() != null && args != null) {
            fragment.getArguments().putAll(args);
        }
        fragmentManager.popBackStack(fragmentViewName, 0);
        fragmentManager.executePendingTransactions();

        return (IBaseView) fragment;
    }

    @Override
    public void showLoading() {
        relativeLayout.setVisibility(View.GONE);
        linearLayouta.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        relativeLayout.setVisibility(View.VISIBLE);
        linearLayouta.setVisibility(View.VISIBLE);
    }
}
