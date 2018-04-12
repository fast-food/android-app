package me.aflak.ff3.ui.Main.data;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.ff3.app.AppModule;
import me.aflak.ff3.ui.Main.view.MainActivity;

/**
 * Created by Omar on 07/10/2017.
 */
@Singleton
@Component(modules = {AppModule.class, MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
