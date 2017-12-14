package me.aflak.ff3;

import android.app.Application;

import me.aflak.ff3.app.AppComponent;
import me.aflak.ff3.app.AppModule;
import me.aflak.ff3.app.DaggerAppComponent;

/**
 * Created by Omar on 07/10/2017.
 */

public class MyApp extends Application {
    private static MyApp app;
    private AppModule appModule;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appModule = new AppModule(this);
        appComponent = DaggerAppComponent.builder().appModule(appModule).build();
    }

    public static MyApp app(){
        return app;
    }

    public AppModule appModule(){
        return appModule;
    }

    public AppComponent appComponent(){
        return appComponent;
    }
}
