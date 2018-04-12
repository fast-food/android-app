package me.aflak.ff3.ui.Menu.data;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.ff3.app.AppModule;
import me.aflak.ff3.ui.Menu.view.SelectMenuActivity;

@Singleton
@Component(modules = {AppModule.class, SelectMenuModule.class})
public interface SelectMenuComponent {
    void inject(SelectMenuActivity activity);
}
