package me.aflak.ff3.app;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.ff3.ui.Main.adapter.GridViewAdapter;

/**
 * Created by Omar on 07/10/2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(NfcCardService service);
    void inject(GridViewAdapter adapter);
}
