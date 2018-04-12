package me.aflak.ff3.app;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.ff3.ui.Main.adapter.MainGridViewAdapter;
import me.aflak.ff3.ui.Menu.adapter.SelectMenuGridViewAdapter;

/**
 * Created by Omar on 07/10/2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(NfcCardService service);
    void inject(MainGridViewAdapter adapter);
    void inject(SelectMenuGridViewAdapter adapter);
}
