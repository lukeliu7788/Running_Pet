package com.votors.runningx.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2017/10/13.
 */

public class main {
    @SerializedName("temp")
    public String tempareture;

    public String getTempareture(){
        return tempareture;
    }


}
