package com.votors.runningx.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2017/10/13.
 */

public class wind {
    @SerializedName("speed")
    public String speed;

    public String getSpeed(){
        return speed;
    }


}
