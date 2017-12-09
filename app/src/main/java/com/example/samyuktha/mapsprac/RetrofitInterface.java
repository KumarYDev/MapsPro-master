package com.example.samyuktha.mapsprac;

import com.example.samyuktha.mapsprac.Retro.Info;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by HERO on 9/15/2017.
 */

public interface RetrofitInterface {
    @POST("json?key=AIzaSyCry_OxBfTOfawF3GojKc6J-gz5L6t9Ne4")
    Call<Info> getData(@Query("type") String type, @Query("query") String query);
}
