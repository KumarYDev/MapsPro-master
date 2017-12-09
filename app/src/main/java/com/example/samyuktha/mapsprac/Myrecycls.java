package com.example.samyuktha.mapsprac;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by samyuktha on 9/12/2017.
 */

public class Myrecycls extends RecyclerView.Adapter<Myrecycls.MyAdapter> {

    List<Datafromhere> getslist;
GoogleMap getmaps;

    Myrecycls(List<Datafromhere> gets,GoogleMap map)
    {
        this.getslist=gets;
        this.getmaps=map;

    }


    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.eachlay, parent, false);
        return new MyAdapter(v);

    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {

        Datafromhere dsam= getslist.get(position);
        holder.butk.setText((CharSequence) dsam.getName1());


    }

    @Override
    public int getItemCount() {
        return getslist.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder
    {

        Button butk;
        int mk;

        public MyAdapter(View itemView) {
            super(itemView);

            butk = (Button) itemView.findViewById(R.id.buttonss);

            butk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        mk=getAdapterPosition();

                    CameraUpdate locss = CameraUpdateFactory.newLatLngZoom(new LatLng(getslist.get(mk).getLatsdata(),getslist.get(mk).getLngsdata()),15);
                    getmaps.animateCamera(locss);

                }
            });
        }
    }
}
