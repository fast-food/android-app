package me.aflak.ff3.ui.Main.data;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.ff3.R;
import me.aflak.ff3.model.ObjectManager;
import me.aflak.ff3.service.NfcRequestQueue;
import me.aflak.ff3.ui.Main.adapter.MainGridViewAdapter;
import me.aflak.ff3.ui.Main.interactor.MainInteractor;
import me.aflak.ff3.ui.Main.interactor.MainInteractorImpl;
import me.aflak.ff3.ui.Main.presenter.MainPresenter;
import me.aflak.ff3.ui.Main.presenter.MainPresenterImpl;
import me.aflak.ff3.ui.Main.view.MainView;

/**
 * Created by Omar on 07/10/2017.
 */
@Module
public class MainModule {
    private MainView mainView;

    public MainModule(MainView mainView) {
        this.mainView = mainView;
    }

    @Provides @Singleton
    public MainView provideMainView(){
        return mainView;
    }

    @Provides @Singleton
    public MainInteractor provideMainInteractor(ObjectManager objectManager, NfcRequestQueue nfcRequestQueue, Gson gson){
        return new MainInteractorImpl(objectManager, nfcRequestQueue, gson);
    }

    @Provides @Singleton
    public MainPresenter provideMainPresenter(MainView mainView, MainInteractor mainInteractor){
        return new MainPresenterImpl(mainView, mainInteractor);
    }

    @Provides @Singleton
    public MainGridViewAdapter provideGridViewAdapter(Context context){
        return new MainGridViewAdapter(context, R.layout.activity_main_grid_item);
    }
}
