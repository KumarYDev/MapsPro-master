package com.example.samyuktha.mapsprac;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by samyuktha on 9/14/2017.
 */

public class Rec2 extends RecyclerView.Adapter<Rec2.Myviewholder> {


    List<Datafromhere>  hellolist;
    Context con;

    Rec2(List<Datafromhere> a, Context cons)
    {
        this.hellolist=a;

        this.con=cons;
        Log.d("contextttt",""+con);
    }


    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v9= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaaach, parent, false);
        return new Rec2.Myviewholder(v9);

    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {

        Datafromhere dsam= hellolist.get(position);
        holder.t1.setText(dsam.getName1());
        holder.t2.setText(dsam.getLatsdata().toString());
        holder.t3.setText(dsam.getLngsdata().toString());
        holder.t4.setText(dsam.getVicinity1());


       Picasso.with(con).load(dsam.getIcon1()).into(holder.imgs);


double df= dsam.getRating1();
       String mkj=  String.valueOf(df);
        holder.t5.setText(mkj);
    }

    @Override
    public int getItemCount() {
        return hellolist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {

        TextView t1,t2,t3,t4,t5;
        ImageView imgs;

        public Myviewholder(View itemView) {
            super(itemView);
            con=itemView.getContext();

            t1=(TextView)itemView.findViewById(R.id.mtitletextview);
            t2=(TextView)itemView.findViewById(R.id.mlatText);
            t3=(TextView)itemView.findViewById(R.id.mlongtextview);
            t4=(TextView)itemView.findViewById(R.id.maddtextview);
            t5=(TextView)itemView.findViewById(R.id.mrating);
            imgs=(ImageView) itemView.findViewById(R.id.mimageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Datafromhere dsam= hellolist.get(getAdapterPosition());
                    Intent ins = new Intent(con, Main2Activity.class);

                    Double l1= dsam.getLatsdata();
                    Double l2= dsam.getLngsdata();
                    ins.putExtra("h1",dsam.getName1());
                    ins.putExtra("h2",l1.toString());
                    ins.putExtra("h3",l2.toString());
                    ins.putExtra("h4",dsam.getVicinity1());
                    ins.putExtra("h5",dsam.getIcon1());
                    con.startActivity(ins);


                }
            });


        }
    }
}
