package me.aflak.ff3.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.ff3.model.ObjectManager;

/**
 * Created by Omar on 07/10/2017.
 */

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides @Singleton
    public SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton
    public Gson provideGson(){
        return new Gson();
    }

    @Provides @Singleton
    public ObjectManager provideObjectManager(SharedPreferences sharedPreferences, Context context, Gson gson){
        return new ObjectManager(sharedPreferences, context.getResources(), gson);
    }

    @Provides @Singleton
    public Typeface provideTypeface(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/insanibu.ttf");
    }

    @Provides @Singleton
    public NfcRequest provideNfcRequest(ObjectManager objectManager){
        return new NfcRequest(objectManager);
    }
}
