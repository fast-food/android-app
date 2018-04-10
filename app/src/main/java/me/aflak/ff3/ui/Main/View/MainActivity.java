package me.aflak.ff3.ui.Main.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.MenuSize;
import me.aflak.ff3.ui.Main.Presenter.MainPresenter;
import me.aflak.ff3.ui.Main.data.DaggerMainComponent;
import me.aflak.ff3.ui.Main.data.MainModule;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.activity_main_text) TextView textView;
    @BindView(R.id.activity_main_nfc) ImageView nfcImage;

    @Inject MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .appModule(MyApp.app().appModule())
                .mainModule(new MainModule(this))
                .build().inject(this);

        textView.setMovementMethod(new ScrollingMovementMethod());
        presenter.checkForNfc(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    String getMenuSizeString(MenuSize size){
        if(size==MenuSize.Small){
            return "Small";
        }
        else if(size==MenuSize.Large){
            return "Large";
        }
        else{
            return "null";
        }
    }

    @Override
    public void showMenu(List<Menu> menuList) {
        StringBuilder builder = new StringBuilder();
        for(Menu menu : menuList){
            builder.append("Menu : ");
            builder.append(menu.getSandwich().getName());
            builder.append(", ");
            builder.append(menu.getExtra().getName());
            builder.append(", ");
            builder.append(menu.getDrink().getName());
            builder.append("\n");
            builder.append("Size : ");
            builder.append(getMenuSizeString(menu.getSize()));
            builder.append("\n");
            builder.append("Price : ");
            builder.append(menu.getPrice());
            builder.append("\n\n");
        }
        textView.setText(builder.toString());
    }

    @Override
    public void showText(String data) {
        textView.setText(data);
    }

    @Override
    public void showNfcImage(boolean state) {
        nfcImage.setVisibility(state?View.VISIBLE:View.GONE);
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
