package me.aflak.ff3.ui.Sender.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.R;

public class SenderActivity extends AppCompatActivity implements SenderView{
    @BindView(R.id.activity_sender_logo) ImageView logo;
    @BindView(R.id.activity_sender_help) TextView help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        ButterKnife.bind(this);
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
}
