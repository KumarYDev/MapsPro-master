package com.example.samyuktha.mapsprac;

import io.realm.RealmObject;

/**
 * Created by HERO on 9/17/2017.
 */

public class MapsData extends RealmObject {
    private Double latsdata;
    private Double lngsdata;
    private String icon1;
    private String name1;
    private boolean opennow1;
    private double rating1;
    private String placeid;

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }



    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query;


    public Double getLatsdata() {
        return latsdata;
    }

    public void setLatsdata(Double latsdata) {
        this.latsdata = latsdata;
    }

    public Double getLngsdata() {
        return lngsdata;
    }

    public void setLngsdata(Double lngsdata) {
        this.lngsdata = lngsdata;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public boolean isOpennow1() {
        return opennow1;
    }

    public void setOpennow1(boolean opennow1) {
        this.opennow1 = opennow1;
    }

    public double getRating1() {
        return rating1;
    }

    public void setRating1(double rating1) {
        this.rating1 = rating1;
    }

    public String getVicinity1() {
        return vicinity1;
    }

    public void setVicinity1(String vicinity1) {
        this.vicinity1 = vicinity1;
    }

    private String vicinity1;
    public MapsData(){}

    MapsData(Double a, Double b, String icon, String name, Boolean opennow, Double rating, String vicinity, String query, String placeid)
    {
        this.latsdata=a;
        this.lngsdata=b;
        this.icon1=icon;
        this.name1=name;
        this.opennow1=opennow;
        this.rating1=rating;
        this.vicinity1=vicinity;
        this.query=query;
        this.placeid=placeid;
    }

}
