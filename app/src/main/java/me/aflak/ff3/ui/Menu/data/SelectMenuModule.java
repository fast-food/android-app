package me.aflak.ff3.ui.Menu.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.ff3.R;
import me.aflak.ff3.ui.Menu.adapter.SelectMenuGridViewAdapter;
import me.aflak.ff3.ui.Menu.interactor.SelectMenuInteractor;
import me.aflak.ff3.ui.Menu.interactor.SelectMenuInteractorImpl;
import me.aflak.ff3.ui.Menu.presenter.SelectMenuPresenter;
import me.aflak.ff3.ui.Menu.presenter.SelectMenuPresenterImpl;
import me.aflak.ff3.ui.Menu.view.SelectMenuView;

@Module
public class SelectMenuModule {
    private SelectMenuView selectMenuView;

    public SelectMenuModule(SelectMenuView selectMenuView) {
        this.selectMenuView = selectMenuView;
    }

    @Provides @Singleton
    public SelectMenuView provideSelectMenuView(){
        return selectMenuView;
    }

    @Provides @Singleton
    public SelectMenuInteractor provideSelectMenuInteractor(){
        return new SelectMenuInteractorImpl();
    }

    @Provides @Singleton
    public SelectMenuPresenter provideSelectMenuPresenter(SelectMenuView view, SelectMenuInteractor interactor){
        return new SelectMenuPresenterImpl(view, interactor);
    }

    @Provides @Singleton
    public SelectMenuGridViewAdapter provideSelectMenuGridViewAdapter(Context context){
        return new SelectMenuGridViewAdapter(context, R.layout.activity_select_menu_grid_item);
    }
}
