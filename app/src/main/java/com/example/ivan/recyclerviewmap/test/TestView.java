package com.example.ivan.recyclerviewmap.test;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ivan.recyclerviewmap.MyAdapter;
import com.example.ivan.recyclerviewmap.R;
import com.example.ivan.recyclerviewmap.Service.Gasstation;
import com.example.ivan.recyclerviewmap.Service.GasstationEvent;
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

import rx.Subscription;

/**
 * Created by Ivan on 9/2/2017.
 */
@Layout(layoutId = R.layout.activity_main2)
public class TestView extends BaseView<TestPresenter> implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


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
    private LinearLayoutManager layoutManager;
    private int firstVisiblePosition;
    private MyAdapter adapter;
    private Marker previousMarker;

    private List<Gasstation> gasstationsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter.getGasstations(1,"&fuel=","lpg");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        geocoder = new Geocoder(getContext());
        initMap();

        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_view);
        layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        myList = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(myList.getContext(),
                layoutManager.getOrientation());
        myList.addItemDecoration(dividerItemDecoration);


        mLocationClient = new GoogleApiClient.Builder(getContext())
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
                    gotoLocation(gasstationsList.get(firstVisiblePosition).getLat(),gasstationsList.get(firstVisiblePosition).getLng(),12);
                } else {
                    gotoLocation(gasstationsList.get(firstVisiblePosition).getLat(),gasstationsList.get(firstVisiblePosition).getLng(),12);
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


      /*  Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        Log.d("Tag", "onViewCreated: "+formattedDate);

        Calendar eventTime = Calendar.getInstance();
        int year = eventTime.get(Calendar.YEAR);
        int month = eventTime.get(Calendar.MONTH);
        int day =  eventTime.get(Calendar.DAY_OF_MONTH);
        Log.e("Tag","Year" + year +"Month" + month + "Day" + day);*/



       /* button.setOnClickListener(v -> {
            showLoading();
            //presenter.getEvent("gasoline","&date=");
        });*/


    }


    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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
        for (int i =0 ; i<gasstationsList.size();i++){
            if (marker.getTitle().equalsIgnoreCase(gasstationsList.get(i).getName())){
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
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        geocoder = new Geocoder(getContext(), Locale.getDefault());



        List<LatLng> listAddresses = new ArrayList<>();
        for(int i=0;i<gasstationsList.size();i++){
            double latitude = gasstationsList.get(i).getLat();
            double longitude = gasstationsList.get(i).getLng();
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            listAddresses.add(new LatLng(latitude,longitude));

            MarkerOptions options = new MarkerOptions()
                    .title(gasstationsList.get(i).getName())
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
    public boolean onBack() {
        hideLoading();
        return super.onBack();
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected void addSubscriptions(List<Subscription> subscriptions) {
        //subscriptions.add(presenter.getEventObservable().subscribe(this::followingSuccess));
       // subscriptions.add(presenter.getErrorEventObservable().subscribe(this::onError));
        subscriptions.add(presenter.getGasstationEventObservable().subscribe(this::followingSuccess));
        subscriptions.add(presenter.getErrorGasstationEventObservable().subscribe(this::onError));
    }

    private void followingSuccess(GasstationEvent priceEvent){
        gasstationsList = priceEvent.gasstations();
        adapter = new MyAdapter(getContext(),gasstationsList);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(myList);
        myList.setAdapter(adapter);
        myList.setLayoutManager(layoutManager);

        try {
            showAllEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void onError(Throwable t){

        Toast.makeText(getContext(), "Something whent wrong", Toast.LENGTH_SHORT).show();
    }

}
