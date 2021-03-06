package com.samuel.cachelocator;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class CacheLocatorApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        ParseObject.registerSubclass(UserPost.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, CacheLocatorKeys.AUTH_KEY, CacheLocatorKeys.AUTH_SECRET);
    }
}
