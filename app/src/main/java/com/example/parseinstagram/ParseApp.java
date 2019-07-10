package com.example.parseinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("brittenyoko")
                .clientKey("SailorDavid16")
                .server("https://brittenyoko-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

    }
}
