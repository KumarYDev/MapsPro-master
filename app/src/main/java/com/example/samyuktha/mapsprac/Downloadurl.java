package com.example.samyuktha.mapsprac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by samyuktha on 9/11/2017.
 */

public class Downloadurl {

    HttpURLConnection con;
    InputStream iStream;
    String data=" ";
    URL url= null;

    public String readUrl(String myurl)  {


        try {
            url = new URL(myurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            con.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            iStream = con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

        StringBuffer sb = new StringBuffer();

        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        data = sb.toString();
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;


    }
}
