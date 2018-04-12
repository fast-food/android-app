package me.aflak.ff3.ui.Menu.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class SelectMenuGridViewAdapter extends ArrayAdapter<FoodType> implements View.OnClickListener {
    private LayoutInflater inflater;
    private OnFoodTypeClickListener onFoodTypeClickListener;
    private int[] colors;

    @Inject Typeface font;
    @Inject RandHelper randHelper;

    public SelectMenuGridViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        MyApp.app().appComponent().inject(this);
        init(context);
    }

    private void init(Context context){
        onFoodTypeClickListener = null;
        inflater = LayoutInflater.from(context);
        colors = context.getResources().getIntArray(R.array.rainbow);
        randHelper.setMax(colors.length);
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
            view.setOnClickListener(this);
            holder = new ViewHolder(view);
            holder.position = position;
            view.setTag(holder);
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

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        FoodType foodType = getItem(holder.position);
        if(onFoodTypeClickListener!=null){
            onFoodTypeClickListener.onFoodTypeClick(foodType);
        }
    }

    class ViewHolder {
        @BindView(R.id.activity_select_menu_grid_item_image) ImageView image;
        @BindView(R.id.activity_select_menu_grid_item_text) TextView text;
        int color;
        int position;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            text.setTypeface(font);
            color = colors[randHelper.nextInt()];
        }
    }

    public void setOnFoodTypeClickListener(OnFoodTypeClickListener onFoodTypeClickListener) {
        this.onFoodTypeClickListener = onFoodTypeClickListener;
    }

    public interface OnFoodTypeClickListener{
        void onFoodTypeClick(FoodType foodType);
    }
}
