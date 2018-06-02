package me.aflak.ff3.ui.Sender.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.ui.Sender.data.DaggerSenderComponent;
import me.aflak.ff3.ui.Sender.data.SenderModule;
import me.aflak.ff3.ui.Sender.presenter.SenderPresenter;

public class SenderActivity extends AppCompatActivity implements SenderView{
    @BindView(R.id.activity_sender_logo) ImageView logo;
    @BindView(R.id.activity_sender_help) TextView help;

    @Inject SenderPresenter presenter;
    @Inject Typeface font;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        ButterKnife.bind(this);
        DaggerSenderComponent.builder()
                .appModule(MyApp.app().appModule())
                .senderModule(new SenderModule(this))
                .build().inject(this);

        help.setTypeface(font);
        presenter.onCreate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop(this);
    }

    @Override
    public void startAnimation() {
        RotateAnimation anim = new RotateAnimation(0f, 350f, logo.getPivotX(), logo.getPivotY());
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);
        logo.startAnimation(anim);
    }

    @Override
    public void stopAnimation() {
        logo.setAnimation(null);
    }

    @Override
    public void showToast(int resId) {
        String message = getResources().getString(resId);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayWaitingMessage() {
        help.setText(R.string.activity_sender_done_message);
    }
}
