package com.example.samyuktha.mapsprac;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity  {

    ImageView  img;
    TextView title,lats,longs,addresss;
    double dlat,dlon,alat,alon;
    Button b;
    LocationManager lm;
    LocationListener loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img=(ImageView)findViewById(R.id.imageview);
        title=(TextView)findViewById(R.id.titletextview);
        lats=(TextView)findViewById(R.id.latText);
        longs=(TextView)findViewById(R.id.longtextview);
        addresss=(TextView)findViewById(R.id.addtextview);
        b=(Button) findViewById(R.id.googlebutton);






       Intent ink= getIntent();
        title.setText(ink.getStringExtra("h1"));
        lats.setText(ink.getStringExtra("h2"));
        longs.setText(ink.getStringExtra("h3"));
        addresss.setText(ink.getStringExtra("h4"));

        Picasso.with(this).load(ink.getStringExtra("h5")).into(img);
        dlat=Double.parseDouble(lats.getText().toString());
                dlon=Double.parseDouble(longs.getText().toString());


        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        loc=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location.getLongitude()!=0&&location.getLatitude()!=0) {
                    alon = location.getLongitude();
                    alat = location.getLatitude();
                    Toast.makeText(Main2Activity.this, " "+alat+" "+alon,Toast.LENGTH_SHORT).show();
                    lm.removeUpdates(loc);
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
        };

check();
        Toast.makeText(Main2Activity.this, " "+alat+" "+alon,Toast.LENGTH_SHORT).show();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1")
                        .appendQueryParameter("destination", dlat + "," + dlon)
                        .appendQueryParameter("origin",alat+","+alon)
                        .appendQueryParameter("travelmode","driving");
                String url = builder.build().toString();
                Log.d("Directions", url);
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse(url));
                startActivity(in);
            }
        });


    }
    public void check(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},406);
            }
        }else{
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,10, loc);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==406 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //the main function u need

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                        &&shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},406);

                } else {
                    AlertDialog.Builder dia = new AlertDialog.Builder(Main2Activity.this);
                    dia.setMessage("Enable the required Permission for the Application\n Go to Setting and Enable the Permissions");
                    dia.setTitle("PERMISSIONS NEEDED");
                    dia.setPositiveButton("PERMISSION", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    dia.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Main2Activity.this, "PERMISSIONS ARE NOT ENABLED", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = dia.create();
                    dialog.show();

                }
            }
        }


    }

}
