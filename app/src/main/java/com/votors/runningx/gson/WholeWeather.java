package com.votors.runningx.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2017/10/13.
 */

public class WholeWeather {
    public com.votors.runningx.gson.weather weather;
    public com.votors.runningx.gson.main main;
    public com.votors.runningx.gson.wind wind;
    public com.votors.runningx.gson.clouds clouds;
    @SerializedName("name")
    public String name;

    public com.votors.runningx.gson.weather getWeather(){
        return weather;
    }
    public String getName(){
        return name;
    }
    public com.votors.runningx.gson.main getMain(){
        return main;
    }

    public com.votors.runningx.gson.clouds getClouds(){
        return clouds;
    }

    public com.votors.runningx.gson.wind getWind(){
        return wind;
    }


}
