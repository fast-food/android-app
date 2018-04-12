package me.aflak.ff3.ui.Main.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.model.RandHelper;

public class MainGridViewAdapter extends ArrayAdapter<Menu> implements View.OnClickListener {
    private LayoutInflater inflater;
    private OnMenuClickListener onMenuClickListener;
    private int[] colors;

    @Inject Typeface font;
    @Inject RandHelper randHelper;

    public MainGridViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        MyApp.app().appComponent().inject(this);
        init(context);
    }

    private void init(Context context){
        onMenuClickListener = null;
        inflater = LayoutInflater.from(context);
        colors = context.getResources().getIntArray(R.array.rainbow);
        randHelper.setMax(colors.length);
    }

    private SpannableString getTextFromMenu(Menu menu){
        StringBuilder builder = new StringBuilder();
        builder.append(menu.getPrice());
        builder.append("€\n\n");

        Map<FoodType, Long> counts = menu.getTypes().stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        for(Map.Entry<FoodType, Long> entry : counts.entrySet()){
            String name = getContext().getResources().getString(Food.toString(entry.getKey()));
            builder.append(name);
            if(entry.getValue()>1){
                builder.append(" x");
                builder.append(entry.getValue());
            }
            builder.append("\n");
        }

        String text = builder.toString();
        SpannableString ss = new SpannableString(text);

        // all
        ss.setSpan(new RelativeSizeSpan(1.2f), 0, text.length(), 0);

        // price
        int priceIndex = text.indexOf("\n");
        ss.setSpan(new RelativeSizeSpan(2.8f), 0, priceIndex, 0);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, priceIndex, 0);

        // x{n}
        Pattern pattern = Pattern.compile("[x][2-9]");
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new RelativeSizeSpan(1.3f), start, end, 0);
            ss.setSpan(new StyleSpan(Typeface.BOLD), start, end, 0);
        }
        return ss;
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
            view = inflater.inflate(R.layout.activity_main_grid_item, parent, false);
            view.setLayoutParams(new GridView.LayoutParams(size, size));
            view.setOnClickListener(this);
            holder = new ViewHolder(view);
            holder.position = position;
            view.setTag(holder);
        }

        Menu menu = getItem(position);
        if(menu!=null) {
            holder.text.setText(getTextFromMenu(menu));
            ColorStateList csl = new ColorStateList(new int[][]{{}}, new int[]{holder.color});
            holder.view.setBackgroundTintList(csl);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Menu menu = getItem(holder.position);
        if(onMenuClickListener!=null){
            onMenuClickListener.onMenuClick(menu);
        }
    }

    class ViewHolder {
        @BindView(R.id.activity_main_grid_item_square) ImageView view;
        @BindView(R.id.activity_main_grid_item_text) TextView text;
        int color;
        int position;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            text.setTypeface(font);
            color = colors[randHelper.nextInt()];
        }
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener{
        void onMenuClick(Menu menu);
    }
}
