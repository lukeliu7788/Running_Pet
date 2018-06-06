package com.votors.runningx.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2017/10/13.
 */

public class weather {
    @SerializedName("id")
    public String weatherID;

    @SerializedName("main")
    public String main;


    public String getWeatherID(){
        return weatherID;
    }
    public String getMain(){
        return main;
    }



}
