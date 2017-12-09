package com.example.samyuktha.mapsprac;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samyuktha.mapsprac.Retro.Info;
import com.example.samyuktha.mapsprac.Retro.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BlankFragment extends android.app.Fragment implements OnMapReadyCallback{


    GoogleMap mapo;
    LocationManager lm;
    SearchView sh;
    String here,s;

    private Target target;
    RecyclerView rvs;
    List<Datafromhere> doll = new ArrayList<Datafromhere>();
    List<Datafromhere>doll2;
    List<Datafromhere> dup;

    FloatingActionButton fab2,fab3;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    private ClusterManager<StringClusterItem> mClusterManager;
    Bitmap bitmaps;
    Datafromhere dlm;
    CustomClusterRenderer renderer;
    Realm realm;
    Bundle state;


    static class StringClusterItem implements ClusterItem {
        public String getTitle() {
            return title;
        }

        final String title;
        final LatLng latLng;
        final String vicin;
        String iconnss;
        public StringClusterItem(String name1, String title, LatLng latLng, String vicinity1) {

            this.iconnss = title;
            this.latLng = latLng;
            this.vicin=vicinity1;
            this.title=name1;
        }
        @Override public LatLng getPosition() {
            return latLng;
        }


        public String getVicin() {
            return vicin;
        }

        public String getIconnss() {
            return iconnss;
        }
    }

    public class CustomClusterRenderer extends DefaultClusterRenderer<StringClusterItem> {
        private final Context mContext;
        public CustomClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<BlankFragment.StringClusterItem> clusterManager) {
            super(context, map, clusterManager);
            mContext = context;
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<StringClusterItem> cluster) {
            return cluster.getSize()>1;
        }



        @Override protected void onBeforeClusterItemRendered(BlankFragment.StringClusterItem item,
                                                             MarkerOptions markerOptions) {

                markerOptions.title(item.getTitle());
              markerOptions.snippet(item.getVicin());
            markerOptions.position(item.getPosition());

            String sms = dlm.getIcon1();

            final Target mTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    Log.d("DEBUG1", "onBitmapLoaded");
                    bitmaps = bitmap;

                }

                @Override
                public void onBitmapFailed(Drawable drawable) {
                    Log.d("DEBUG", "onBitmapFailed");
                }

                @Override
                public void onPrepareLoad(Drawable drawable) {
                    Log.d("DEBUG", "onPrepareLoad");
                }
            };

            Picasso.with(getContext()).load(sms).into(mTarget);


            if(bitmaps==null) {


                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            else
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmaps));
//                mapo.addMarker(markerOptions);



        }
    }




    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);




    }
    //Check if item exists already with id

    public boolean checkIfExists(String id,String query){
        RealmResults<MapsData> one = realm.where(MapsData.class)
                .findAll();
        if(one.size()!=0) {
            RealmResults<MapsData> que = realm.where(MapsData.class)
                    .equalTo("placeid", id)
                    .equalTo("query", query)
                    .findAll();

            return que.size() == 0;
        }else
            return true;
    }
    public void realmfunction(String info){
        mapo.clear();
        mClusterManager.clearItems();
        Log.e("Doll2","Size="+doll2.size());
        RealmResults<MapsData> data= realm.where(MapsData.class).equalTo("query",info).distinct("placeid");
        RealmResults<MapsData> whole= realm.where(MapsData.class).findAll();
        Log.e("MAPSDATA","Size all values="+whole.size());
        Log.e("MAPSDATA","Size ="+data.size());
        for(MapsData temp:whole){
            Log.e("MAPSDATA all",temp.getName1()+"\n"+temp.getPlaceid()+"\n"+temp.getQuery());
        }

        for(MapsData temp:data){
            doll.add(new Datafromhere(temp.getLatsdata(), temp.getLngsdata(), temp.getIcon1(), temp.getName1(), temp.isOpennow1(), temp
                    .getRating1(), temp.getVicinity1(),temp.getPlaceid()));
            doll2.add(new Datafromhere(temp.getLatsdata(), temp.getLngsdata(), temp.getIcon1(), temp.getName1(), temp.isOpennow1(), temp
                    .getRating1(), temp.getVicinity1()));
        }
        Log.e("Doll2","Size="+doll2.size());
        removeDuplicates();
        Log.e("around"," "+doll2.size());
        for (int i = 0; i < doll2.size(); i++) {


            dlm =doll2.get(i);
            LatLng latLng = new LatLng(dlm.getLatsdata(),dlm.getLngsdata());

            mClusterManager.addItem(new StringClusterItem(dlm.getName1(), dlm.getIcon1(),latLng,dlm.getVicinity1()));

            // move map camera
            mapo.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(dlm.getLatsdata(),dlm.getLngsdata())));

        }

        mClusterManager.cluster();

        Myrecycls adap=new Myrecycls(doll,mapo);
        rvs.setAdapter(adap);
    }

public void removeDuplicates(){
//    data = new ArrayList<Datafromhere>(new LinkedHashSet<Datafromhere>(data));
    for(int i=0;i<doll.size();i++){
        for(int j=i+1;j<doll.size();j++){
            if(doll.get(i).getPlaceid().equals(doll.get(j).getPlaceid())){
                doll.remove(j);
                j--;
            }
        }
    }
}

    private void saveRealmData(
            final Double lat
            , final Double lng
            , final String icon
            , final String name
            , final Boolean opennow
            , final double rating
            , final String vicinity
            , final String tet
            ,final String placeid
    ) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                MapsData map = bgRealm.createObject(MapsData.class);
                map.setLatsdata(lat);
                map.setLngsdata(lng);
                map.setIcon1(icon);
                map.setName1(name);
                map.setOpennow1(opennow);
                map.setRating1(rating);
                map.setVicinity1(vicinity);
                map.setQuery(tet);
                map.setPlaceid(placeid);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.e("REALM", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("REALM", "FAILED" + error.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private String getUrl(String nearbyPlace) {
        StringBuilder googlePlacesUrl = new
                StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");

        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&query="+ nearbyPlace);
        googlePlacesUrl.append("&key=" + "AIzaSyCry_OxBfTOfawF3GojKc6J-gz5L6t9Ne4");

        return (googlePlacesUrl.toString());
    }

    private void retrofit(final String query){
        String Base_url="https://maps.googleapis.com/maps/api/place/textsearch/";
        doll2=new ArrayList<Datafromhere>();

        doll2.clear();
Log.e("Doll2","Size="+doll2.size());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface ret=retrofit.create(RetrofitInterface.class);
        Call<Info> call=ret.getData(query,query);
//                RetrofitInterface ret=retrofit.create(RetrofitInterface.class);
//                Call<Info> call=ret.getData(s,s);
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Log.e("test","Success");
                try {
                    List<Result> path = response.body().getResults();
                    for (int i = 0; i < path.size(); i++) {
                        Double latitudeval = path.get(i).getGeometry().getLocation().getLat();
                        Double Longitudeval = path.get(i).getGeometry().getLocation().getLng();
                        String icon = path.get(i).getIcon();
                        String placeid=path.get(i).getPlaceId();

                        String name = path.get(i).getName();
                        Boolean opennow;
                        Double rating;

                        if (path.get(i).getRating() == null) {
                            rating = 0.0;
                        } else rating = path.get(i).getRating();
//                    Boolean opennow=false;
                        if (path.get(i).getOpeningHours() != null) {
                            opennow = path.get(i).getOpeningHours().getOpenNow();
                        } else opennow = false;
                        Log.e("test", opennow.toString());
                        String vicinity = path.get(i).getFormattedAddress();

                        if(checkIfExists(placeid,query)) {
                            saveRealmData(latitudeval, Longitudeval, icon, name, opennow, rating, vicinity, query, placeid);
                        }
//                        doll.add(new Datafromhere(latitudeval, Longitudeval, icon, name, opennow, rating, vicinity));
//                        doll2.add(new Datafromhere(latitudeval, Longitudeval, icon, name, opennow, rating, vicinity));

                    }
//                    Log.e("around"," "+doll2.size());
//                for (int i = 0; i < doll2.size(); i++) {
//
//
//                    dlm =doll2.get(i);
//                    LatLng latLng = new LatLng(dlm.getLatsdata(),dlm.getLngsdata());
//
//                    mClusterManager.addItem(new StringClusterItem(dlm.getName1(), dlm.getIcon1(),latLng,dlm.getVicinity1()));
//
//                    // move map camera
//                    mapo.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(dlm.getLatsdata(),dlm.getLngsdata())));
//
//                }
//
//                mClusterManager.cluster();
//
//                Myrecycls adap=new Myrecycls(doll,mapo);
//                rvs.setAdapter(adap);
                }catch (Exception e){
                    e.printStackTrace();
                }
                RealmResults<MapsData> test= realm.where(MapsData.class).findAll();
                Log.e("outside","Size="+test.size());
                RealmResults<MapsData> que = realm.where(MapsData.class)
                        .equalTo("query", query)
                        .distinct("placeid");
                Log.e("outside","Size="+que.size());
                if(que.size()!=0)
                    realmfunction(query);
                else
                    Toast.makeText(getActivity(),"There is No data Saved on "+query+" query",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                Log.e("test","failure");

                RealmResults<MapsData> que = realm.where(MapsData.class)
                        .equalTo("query", query)
                        .distinct("placeid");
                if(que.size()!=0)
                    realmfunction(query);
                else
                    Toast.makeText(getActivity(),"There is No data Saved on "+query+" query",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        Realm Step 1: add Realm.init(this);
        Realm.init(getActivity());
        //Realm Step 2: Get a Realm instance for this thread
//        Realm realm = Realm.getDefaultInstance();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vm =  inflater.inflate(R.layout.fragment_blank, container, false);
        fab2=(FloatingActionButton)getActivity().findViewById(R.id.topfloatingActionButton);
      final FloatingActionButton  fab3=(FloatingActionButton)getActivity().findViewById(R.id.leftfloatingActionButton);


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doll.size()!=0) {
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("samss", (ArrayList<? extends Parcelable>) doll);



                    BlankFragment2 bf2 = new BlankFragment2();
                    bf2.setArguments(b);
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fmlay, bf2);

                    ft.commit();
                    fab2.setVisibility(View.INVISIBLE);
                    fab3.setVisibility(View.INVISIBLE);


                }else
                    Toast.makeText(getActivity(), "No query is received", Toast.LENGTH_LONG).show();


            }
        });
//        fab3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fab2.setVisibility(View.INVISIBLE);
//                fab3.setVisibility(View.INVISIBLE);
//                Toast.makeText(getActivity(),"Your are in Selected Option",Toast.LENGTH_LONG).show();
//            }
//        });
        sh= (SearchView)getActivity().findViewById(R.id.searchhere);
        rvs=(RecyclerView)getActivity().findViewById(R.id.rec);
        LinearLayoutManager lms = new LinearLayoutManager(getActivity().getBaseContext());
        lms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvs.setLayoutManager(lms);

        sh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                here=query;
                s=query;



                retrofit(query);



//                String url = getUrl(here);
//                Object[] DataTransfer = new Object[2];
//                DataTransfer[0] = mapo;
//                DataTransfer[1] = url;
//                Getnearby getNearbyPlacesData = new Getnearby();
//                getNearbyPlacesData.execute(DataTransfer);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

       return vm;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mv = (MapView)view.findViewById(R.id.mapview);
        mv.onCreate(null);
        mv.onResume();
        mv.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mapo=googleMap;
        mapo.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mapo.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {


                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getActivity().getLayoutInflater().inflate(R.layout.infobubble, null);

                TextView name = (TextView) v.findViewById(R.id.text2);
                TextView lat = (TextView) v.findViewById(R.id.text4);
                TextView longs = (TextView) v.findViewById(R.id.text41);
                TextView  address = (TextView) v.findViewById(R.id.text6);

                LatLng ll =marker.getPosition();
                Double latso=ll.latitude;
                Double longso = ll.longitude;
                name.setText(marker.getTitle());
                lat.setText(latso.toString());
                longs.setText(longso.toString());
                address.setText(marker.getSnippet());

                return v;
            }
        });



        mapo.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {


                Toast.makeText(getActivity(),"checking..",Toast.LENGTH_SHORT).show();
                Log.d("jsdhegfe","hagefe");
//                Intent ins = new Intent(getActivity(), Main2Activity.class);
//                LatLng loss= marker.getPosition();
//                Double l1= loss.latitude;
//                Double l2= loss.longitude;
//                ins.putExtra("h1",marker.getTitle());
//                ins.putExtra("h2",l1.toString());
//                ins.putExtra("h3",l2.toString());
//                ins.putExtra("h4",marker.getSnippet());
//                startActivity(ins);

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mClusterManager = new ClusterManager<>(getContext(), mapo);
        }
        mapo.setOnCameraChangeListener(mClusterManager);
        mapo.setOnMarkerClickListener(mClusterManager);



        mapo.setOnMarkerClickListener(mClusterManager);
        mapo.setOnInfoWindowClickListener(mClusterManager);



         renderer = new CustomClusterRenderer(getActivity(), mapo, mClusterManager);
        mClusterManager.setRenderer(renderer);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<StringClusterItem>() {
            @Override
            public boolean onClusterClick(Cluster<StringClusterItem> cluster) {
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (ClusterItem item : cluster.getItems()) {
                    builder.include(item.getPosition());
                }
                final LatLngBounds bounds = builder.build();
                mapo.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                return true;
            }
        });


        mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<StringClusterItem>() {
            @Override
            public void onClusterItemInfoWindowClick(StringClusterItem stringClusterItem) {
                Log.d("jhbsdv"," "+stringClusterItem.getTitle());

                Intent ins = new Intent(getActivity(), Main2Activity.class);
                LatLng loss= stringClusterItem.getPosition();
                Double l1= loss.latitude;
                Double l2= loss.longitude;
                ins.putExtra("h1",stringClusterItem.getTitle());
                ins.putExtra("h2",l1.toString());
                ins.putExtra("h3",l2.toString());
                ins.putExtra("h4",stringClusterItem.getVicin());
                ins.putExtra("h5",stringClusterItem.getIconnss());
                startActivity(ins);
            }
        });

    }



//    public class Getnearby extends AsyncTask<Object,String ,String> {
//
//        String googleplacesdata, url;
//        GoogleMap mgooglemap;
//
//        List<Address> la;
//        MainActivity ma;
//        @Override
//        protected String doInBackground(Object... params) {
//
//
//            try {
//                mgooglemap = (GoogleMap) params[0];
//                url = (String) params[1];
//                Downloadurl downloadUrl = new Downloadurl();
//                googleplacesdata = downloadUrl.readUrl(url);
//            } catch (Exception e) {
//                Log.d("GooglePlacesReadTask", e.toString());
//            }
//            Log.d("output", googleplacesdata);
//            return googleplacesdata;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            doll2=new ArrayList<Datafromhere>();
//
//            try {
//                JSONObject reader = new JSONObject(s);
//                JSONArray abc= reader.getJSONArray("results");
//                Log.d("around1"," "+abc.length());
//                for(int i=0;i<abc.length();i++)
//                {
//                    JSONObject objectinsidearray = abc.getJSONObject(i);
//
//                    JSONObject geometryobject = objectinsidearray.getJSONObject("geometry");
//                    JSONObject locationobj = geometryobject.getJSONObject("location");
//                    Double latitudeval=locationobj.getDouble("lat");
//                    Double Longitudeval = locationobj.getDouble("lng");
//                    String icon = objectinsidearray.getString("icon");
//
//                    String name = objectinsidearray.getString("name");
//
//                    if(objectinsidearray.has("opening_hours")) {
//                        JSONObject openobject = objectinsidearray.getJSONObject("opening_hours");
//                        opennow=openobject.getBoolean("open_now");
//                    }
//                    else opennow=false;
//                    if(objectinsidearray.has("rating")) {
//                        rating = objectinsidearray.getDouble("rating");
//                    }
//                    else rating = 0.0;
//                    String vicinity = objectinsidearray.getString("formatted_address");
//                    Log.d("latitudes", " "+latitudeval);
//                    doll.add(new Datafromhere(latitudeval,Longitudeval,icon,name,opennow,rating,vicinity));
//                    doll2.add(new Datafromhere(latitudeval,Longitudeval,icon,name,opennow,rating,vicinity));
//                    Log.d("around2"," added"+i);
//
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//
//            Log.d("around"," "+doll2.size());
//            for (int i = 0; i < doll2.size(); i++) {
//
//
//                dlm =doll2.get(i);
//                LatLng latLng = new LatLng(dlm.getLatsdata(),dlm.getLngsdata());
//
//                mClusterManager.addItem(new StringClusterItem(dlm.getName1(), dlm.getIcon1(),latLng,dlm.getVicinity1()));
//
//                // move map camera
//                mapo.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(dlm.getLatsdata(),dlm.getLngsdata())));
//
//            }
//
//            mClusterManager.cluster();
//
//            Myrecycls adap=new Myrecycls(doll,mapo);
//            rvs.setAdapter(adap);
//
//        }
//    }





}
