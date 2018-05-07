package me.aflak.ff3.ui.Sender.data;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.ff3.app.AppModule;
import me.aflak.ff3.ui.Sender.view.SenderActivity;

@Singleton
@Component(modules = {AppModule.class, SenderModule.class})
public interface SenderComponent {
    void inject(SenderActivity activity);
}
