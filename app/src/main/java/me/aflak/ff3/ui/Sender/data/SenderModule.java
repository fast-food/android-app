package me.aflak.ff3.ui.Sender.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.ff3.ui.Sender.interactor.SenderInteractor;
import me.aflak.ff3.ui.Sender.interactor.SenderInteractorImpl;
import me.aflak.ff3.ui.Sender.presenter.SenderPresenter;
import me.aflak.ff3.ui.Sender.presenter.SenderPresenterImpl;
import me.aflak.ff3.ui.Sender.view.SenderView;

@Module
public class SenderModule {
    private SenderView view;

    public SenderModule(SenderView view) {
        this.view = view;
    }

    @Provides @Singleton
    public SenderView provideSenderView(){
        return view;
    }

    @Provides @Singleton
    public SenderInteractor provideSenderInteractor(){
        return new SenderInteractorImpl();
    }

    @Provides @Singleton
    public SenderPresenter provideSenderPresenter(SenderView view, SenderInteractor interactor){
        return new SenderPresenterImpl(view, interactor);
    }
}
