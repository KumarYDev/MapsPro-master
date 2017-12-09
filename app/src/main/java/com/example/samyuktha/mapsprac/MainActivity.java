package com.example.samyuktha.mapsprac;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MainActivity extends AppCompatActivity   {


    GoogleMap map;
    MapFragment frag;
    boolean opennow;
    Double rating;
    LocationManager lm;
    private double latitude, longitude;
    String here;
    RecyclerView rv;
    SearchView sh;
    FloatingActionButton fab1,fab2,fab3;
    boolean isOpen = false;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    BlankFragment2 bf2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sams);

        rv= (RecyclerView)findViewById(R.id.rec);

         bf2= new BlankFragment2();
        final BlankFragment bfmt = new BlankFragment();

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fmlay,bfmt,"map");
        ft.commit();

        fab1=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        fab2=(FloatingActionButton)findViewById(R.id.topfloatingActionButton);
        fab3=(FloatingActionButton)findViewById(R.id.leftfloatingActionButton);



        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rv.setVisibility(View.VISIBLE);
                ft=fm.beginTransaction();
                ft.replace(R.id.fmlay,bfmt);
                ft.commit();
                fab2.setVisibility(View.INVISIBLE);
                fab3.setVisibility(View.INVISIBLE);


            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fab2.getVisibility()==View.VISIBLE)
                {
                    fab2.setVisibility(View.INVISIBLE);
                    fab3.setVisibility(View.INVISIBLE);
                    isOpen=false;
                }
                else {

                    fab2.setVisibility(View.VISIBLE);
                    fab3.setVisibility(View.VISIBLE);
                    isOpen=true;
                }
            }
        });




    }


    public void addMarkercustom(Double lat1, Double lng1)
    {
        LatLng TutorialsPoint = new LatLng(lat1, lng1);
        map.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("Tutorialspoint.com"));
        map.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
    }





}
