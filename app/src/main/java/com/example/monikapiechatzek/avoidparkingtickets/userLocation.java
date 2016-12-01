package com.example.monikapiechatzek.avoidparkingtickets;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by monikapiechatzek on 2016-11-30.
 */

public class userLocation {
    String latitude;
    String longtitude;
    Calendar rightNow;

    public userLocation(){

    }

    public userLocation(String latitude, String longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.rightNow = Calendar.getInstance();
    }
}
