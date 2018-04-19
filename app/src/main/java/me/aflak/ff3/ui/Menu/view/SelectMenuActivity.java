package me.aflak.ff3.ui.Menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.Order;
import me.aflak.ff3.ui.Menu.adapter.SelectMenuGridViewAdapter;
import me.aflak.ff3.ui.Menu.data.DaggerSelectMenuComponent;
import me.aflak.ff3.ui.Menu.data.SelectMenuModule;
import me.aflak.ff3.ui.Menu.presenter.SelectMenuPresenter;
import me.aflak.ff3.ui.Menu.presenter.SelectMenuPresenterImpl.OnFoodSelectedListener;
import me.aflak.ff3.ui.Sender.view.SenderActivity;

public class SelectMenuActivity extends AppCompatActivity implements SelectMenuView {
    @BindView(R.id.activity_select_menu_grid) GridView gridView;

    @Inject SelectMenuGridViewAdapter adapter;
    @Inject SelectMenuPresenter presenter;
    @Inject Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        ButterKnife.bind(this);
        DaggerSelectMenuComponent.builder()
                .appModule(MyApp.app().appModule())
                .selectMenuModule(new SelectMenuModule(this))
                .build().inject(this);

        startActionMode(actionModeCallback);

        presenter.onCreate(this);
        presenter.setGridView(gridView);
        gridView.setAdapter(adapter);
    }

    @Override
    public void showMenu(Menu menu) {
        adapter.clear();
        adapter.addAll(menu.getTypes());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showFoodList(List<Food> foodList, int checkedItem, OnFoodSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.activity_select_menu_food_dialog_title);
        List<String> food = new ArrayList<>();
        for(Food f : foodList){
            food.add(f.getName());
        }
        String[] foodArray = food.toArray(new String[food.size()]);
        builder.setSingleChoiceItems(foodArray, checkedItem, null);
        builder.setPositiveButton(R.string.activity_select_menu_food_dialog_button, (dialog, which) -> {
            ListView list = ((AlertDialog)dialog).getListView();
            listener.onFoodSelected(list.getCheckedItemPosition());
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public FoodType getItem(int position) {
        return adapter.getItem(position);
    }

    @Override
    public void checkItem(int position, Food food) {
        adapter.checkItem(position, food);
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
            mode.setTitle(R.string.activity_select_menu_action_title);
            menu.add("Done").setIcon(R.drawable.check);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            presenter.onDone();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            finish();
        }
    };

    @Override
    public void showSelectErrorPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.activity_select_menu_error_select_title);
        builder.setMessage(R.string.activity_select_menu_error_select_message);
        builder.setPositiveButton(R.string.activity_select_menu_error_select_button, null);
        builder.show();
    }

    @Override
    public void navigateToSender(Order order) {
        Intent intent = new Intent(this, SenderActivity.class);
        String key = getResources().getString(R.string.key_intent_order);
        intent.putExtra(key, gson.toJson(order));
        startActivity(intent);
    }
}
