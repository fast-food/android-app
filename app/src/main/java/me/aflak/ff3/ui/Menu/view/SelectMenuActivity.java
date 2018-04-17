package me.aflak.ff3.ui.Menu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.ui.Menu.adapter.SelectMenuGridViewAdapter;
import me.aflak.ff3.ui.Menu.data.DaggerSelectMenuComponent;
import me.aflak.ff3.ui.Menu.data.SelectMenuModule;
import me.aflak.ff3.ui.Menu.presenter.SelectMenuPresenter;

public class SelectMenuActivity extends AppCompatActivity implements SelectMenuView {
    @BindView(R.id.activity_select_menu_grid) GridView gridView;

    @Inject SelectMenuGridViewAdapter adapter;
    @Inject SelectMenuPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        ButterKnife.bind(this);
        DaggerSelectMenuComponent.builder()
                .appModule(MyApp.app().appModule())
                .selectMenuModule(new SelectMenuModule(this))
                .build().inject(this);

        presenter.onCreate(this);
        gridView.setAdapter(adapter);
        adapter.setOnFoodTypeClickListener(presenter::onFoodTypeClick);
    }

    @Override
    public void showMenu(Menu menu) {
        adapter.addAll(menu.getTypes());
    }

    @Override
    public void showFood(List<Food> foodList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.activity_select_menu_food_dialog_title);
        List<String> food = new ArrayList<>();
        for(Food f : foodList){
            food.add(f.getName());
        }
        String[] foodArray = food.toArray(new String[food.size()]);
        builder.setSingleChoiceItems(foodArray, 0, null);
        builder.setPositiveButton("OK", (dialog, which) -> {
            ListView list = ((AlertDialog)dialog).getListView();
            int pos = list.getCheckedItemPosition();
            presenter.selectFood(foodList.get(pos));
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
