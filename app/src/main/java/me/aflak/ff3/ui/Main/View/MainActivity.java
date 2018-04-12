package me.aflak.ff3.ui.Main.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.ui.Main.Presenter.MainPresenter;
import me.aflak.ff3.ui.Main.adapter.GridViewAdapter;
import me.aflak.ff3.ui.Main.data.DaggerMainComponent;
import me.aflak.ff3.ui.Main.data.MainModule;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.activity_main_grid) GridView gridView;
    @BindView(R.id.activity_main_logo) ImageView logo;
    @BindView(R.id.activity_main_tap_layout) RelativeLayout tapLayout;

    @Inject MainPresenter presenter;
    @Inject GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .appModule(MyApp.app().appModule())
                .mainModule(new MainModule(this))
                .build().inject(this);

        presenter.onCreate();
        presenter.checkForNfc(getApplicationContext());
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    public void showMenu(List<Menu> menuList) {
        adapter.addAll(menuList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearMenu() {
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNfcImage(boolean state) {
        tapLayout.setVisibility(state?View.VISIBLE:View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop(this);
    }
}
