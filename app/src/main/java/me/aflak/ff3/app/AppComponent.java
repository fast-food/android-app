package me.aflak.ff3.app;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.ff3.service.NfcCardService;
import me.aflak.ff3.ui.Main.adapter.MainGridViewAdapter;
import me.aflak.ff3.ui.Main.interactor.MainInteractorImpl;
import me.aflak.ff3.ui.Menu.adapter.SelectMenuGridViewAdapter;
import me.aflak.ff3.ui.Menu.interactor.SelectMenuInteractorImpl;
import me.aflak.ff3.ui.Sender.interactor.SenderInteractorImpl;

/**
 * Created by Omar on 07/10/2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(NfcCardService service);
    void inject(MainGridViewAdapter adapter);
    void inject(SelectMenuGridViewAdapter adapter);
    void inject(MainInteractorImpl interactor);
    void inject(SelectMenuInteractorImpl interactor);
    void inject(SenderInteractorImpl interactor);
}
