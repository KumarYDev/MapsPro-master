package com.example.samyuktha.mapsprac;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samyuktha on 9/11/2017.
 */

public class Datafromhere implements Parcelable {

    private Double latsdata;
    private Double lngsdata;
    private String icon1;
    private String name1;
    private boolean opennow1;
    private double rating1;
    private String vicinity1;

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    private String placeid;

    Datafromhere(Double a, Double b,String icon,String name,Boolean opennow,Double rating,String vicinity)
    {
        this.latsdata=a;
        this.lngsdata=b;
        this.icon1=icon;
        this.name1=name;
        this.opennow1=opennow;
        this.rating1=rating;
        this.vicinity1=vicinity;
    }
    Datafromhere(Double a, Double b,String icon,String name,Boolean opennow,Double rating,String vicinity,String placeid)
    {
        this.latsdata=a;
        this.lngsdata=b;
        this.icon1=icon;
        this.name1=name;
        this.opennow1=opennow;
        this.rating1=rating;
        this.vicinity1=vicinity;
        this.placeid=placeid;
    }


    public static final Creator<Datafromhere> CREATOR = new Creator<Datafromhere>() {
        @Override
        public Datafromhere createFromParcel(Parcel in) {
            return new Datafromhere(in);
        }

        @Override
        public Datafromhere[] newArray(int size) {
            return new Datafromhere[size];
        }
    };

    public Datafromhere(Parcel in) {

        this.latsdata=in.readDouble();
        this.lngsdata=in.readDouble();
        this.icon1=in.readString();
        this.name1=in.readString();
        this.rating1=in.readDouble();
        this.vicinity1=in.readString();
        this.placeid=in.readString();


    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeDouble(latsdata);
        dest.writeDouble(lngsdata);
        dest.writeString(icon1);
        dest.writeString(name1);
        dest.writeDouble(rating1);
        dest.writeString(vicinity1);
        dest.writeString(placeid);


    }

    public Double getLatsdata() {
        return latsdata;
    }

    public Double getLngsdata() {
        return lngsdata;
    }

    public String getIcon1() {
        return icon1;
    }

    public String getName1() {
        return name1;
    }

    public boolean isOpennow1() {
        return opennow1;
    }

    public double getRating1() {
        return rating1;
    }

    public String getVicinity1() {
        return vicinity1;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
