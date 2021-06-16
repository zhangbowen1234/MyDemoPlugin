package com.example.mydemoplugin;

import android.content.Context;

/**
 * Created by zhangbowen on 6/15/21.
 * 向整个应用提供 App (application)
 **/
public class AppContext {

    private static AppContext instance;

    private Context applicationContext;

    public AppContext(Context application){
        this.applicationContext = application;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public static AppContext getInstance() {
        if (instance == null){
            throw new RuntimeException();
        }
        return instance;
    }

    public static void init(Context context){
        if (instance != null){
            throw new RuntimeException();
        }
        instance = new AppContext(context);
    }

    public static boolean isInitialized(){
        return (instance != null);
    }
}