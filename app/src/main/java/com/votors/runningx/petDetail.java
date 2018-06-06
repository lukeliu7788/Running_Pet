package com.votors.runningx;

/**
 * Created by mengzhang on 2017/10/15.
 */

import java.util.Date;

/**
 * Created by lenovo on 2017/9/17.
 */

public class petDetail {
    private String name,breed;
    private boolean gender,ster;
    private Date birthday,arrivetime;
    public petDetail(String name,boolean gender,String breed,
                     Date birthday,Date arrivetime,boolean ster){
        this.name=name;
        this.gender=gender;
        this.arrivetime=arrivetime;
        this.birthday=birthday;
        this.breed=breed;
        this.ster=ster;
    }


}
