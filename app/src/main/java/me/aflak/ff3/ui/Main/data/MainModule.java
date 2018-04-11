package me.aflak.ff3.ui.Main.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.ff3.model.ObjectManager;
import me.aflak.ff3.ui.Main.Interactor.MainInteractor;
import me.aflak.ff3.ui.Main.Interactor.MainInteractorImpl;
import me.aflak.ff3.ui.Main.Presenter.MainPresenter;
import me.aflak.ff3.ui.Main.Presenter.MainPresenterImpl;
import me.aflak.ff3.ui.Main.View.MainView;

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
    public MainInteractor provideMainInteractor(ObjectManager objectManager){
        return new MainInteractorImpl(objectManager);
    }

    @Provides @Singleton
    public MainPresenter provideMainPresenter(MainView mainView, MainInteractor mainInteractor){
        return new MainPresenterImpl(mainView, mainInteractor);
    }
}
