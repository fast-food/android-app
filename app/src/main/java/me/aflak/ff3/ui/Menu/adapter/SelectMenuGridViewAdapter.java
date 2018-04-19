package me.aflak.ff3.ui.Menu.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.model.RandHelper;

public class SelectMenuGridViewAdapter extends ArrayAdapter<FoodType> {
    private LayoutInflater inflater;
    private int[] colors;
    private SparseArray<ViewHolder> viewHolders;

    @Inject Typeface font;
    @Inject RandHelper randHelper;

    public SelectMenuGridViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        MyApp.app().appComponent().inject(this);
        init(context);
    }

    private void init(Context context){
        inflater = LayoutInflater.from(context);
        colors = context.getResources().getIntArray(R.array.rainbow);
        randHelper.setMax(colors.length);
        viewHolders = new SparseArray<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            GridView grid = (GridView) parent;
            int size = grid.getColumnWidth();
            view = inflater.inflate(R.layout.activity_select_menu_grid_item, parent, false);
            view.setLayoutParams(new GridView.LayoutParams(size, size));
            holder = new ViewHolder(view, position);
            view.setTag(holder);

            if(viewHolders.get(position, null)==null)
                viewHolders.put(position, holder);
        }

        FoodType foodType = getItem(position);
        if(foodType!=null) {
            String name = getContext().getResources().getString(Food.toString(foodType));
            holder.text.setText(name);
            holder.image.setImageResource(Food.getImage(foodType));
            ColorStateList csl = new ColorStateList(new int[][]{{}}, new int[]{holder.color});
            holder.image.setBackgroundTintList(csl);
        }

        return view;
    }

    public void checkItem(int position, Food food){
        ViewHolder holder = viewHolders.get(position);
        holder.text.setText(food.getName());
        holder.text.setTextColor(holder.color);
    }

    class ViewHolder {
        @BindView(R.id.activity_select_menu_grid_item_image) ImageView image;
        @BindView(R.id.activity_select_menu_grid_item_text) TextView text;
        int color;
        int position;

        ViewHolder(View view, int position) {
            ButterKnife.bind(this, view);
            text.setTypeface(font);
            this.color = colors[randHelper.nextInt()];
            this.position = position;
        }
    }
}
