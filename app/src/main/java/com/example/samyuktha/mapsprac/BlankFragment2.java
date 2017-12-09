package com.example.samyuktha.mapsprac;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends android.app.Fragment {



    List<Datafromhere> gtlisthr;


    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vo= inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        final FloatingActionButton fab2=(FloatingActionButton)getActivity().findViewById(R.id.topfloatingActionButton);
        final FloatingActionButton fab3=(FloatingActionButton)getActivity().findViewById(R.id.leftfloatingActionButton);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab2.setVisibility(View.INVISIBLE);
                fab3.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),"Your are in Selected Option",Toast.LENGTH_LONG).show();
            }
        });

        RecyclerView rs=(RecyclerView)getActivity().findViewById(R.id.rec);
        rs.setVisibility(View.INVISIBLE);

        gtlisthr= getArguments().getParcelableArrayList("samss");
        Toast.makeText(getActivity(),""+gtlisthr.size(),Toast.LENGTH_SHORT).show();

        RecyclerView r=(RecyclerView)vo.findViewById(R.id.reco);

        GridLayoutManager gm = new GridLayoutManager(getActivity(),2);

        gm.setOrientation(GridLayoutManager.VERTICAL);

        r.setLayoutManager(gm);

        Rec2 adapter= new Rec2(gtlisthr,getActivity());

        r.setAdapter(adapter);

        return vo ;



    }







}
