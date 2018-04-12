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

public class GridViewAdapter extends ArrayAdapter<Menu> implements View.OnClickListener {
    private LayoutInflater inflater;
    private int[] colors;
    private List<Integer> pickedColors;
    private Random random;
    private OnMenuClickListener onMenuClickListener;

    @Inject Typeface font;

    public GridViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        init(context);

        MyApp.app().appComponent().inject(this);
    }

    public GridViewAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        init(context);
    }

    public GridViewAdapter(@NonNull Context context, int resource, @NonNull Menu[] objects) {
        super(context, resource, objects);
        init(context);
    }

    public GridViewAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Menu[] objects) {
        super(context, resource, textViewResourceId, objects);
        init(context);
    }

    public GridViewAdapter(@NonNull Context context, int resource, @NonNull List<Menu> objects) {
        super(context, resource, objects);
        init(context);
    }

    public GridViewAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Menu> objects) {
        super(context, resource, textViewResourceId, objects);
        init(context);
    }

    private void init(Context context){
        onMenuClickListener = null;
        inflater = LayoutInflater.from(context);
        colors = context.getResources().getIntArray(R.array.rainbow);
        pickedColors = new ArrayList<>();
        random = new Random();
    }

    private SpannableString getTextFromMenu(Menu menu){
        StringBuilder builder = new StringBuilder();
        builder.append(menu.getPrice());
        builder.append("€\n\n");

        Map<FoodType, Long> counts = menu.getTypes().stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        for(Map.Entry<FoodType, Long> entry : counts.entrySet()){
            builder.append(Food.toString(entry.getKey()));
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
        @BindView(R.id.gridview_item_square) ImageView view;
        @BindView(R.id.gridview_item_text) TextView text;
        int color;
        int position;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            text.setTypeface(font);
            int c;
            while(pickedColors.contains((c = random.nextInt(colors.length))));
            color = colors[c];
            pickedColors.add(c);
        }
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener{
        void onMenuClick(Menu menu);
    }
}
