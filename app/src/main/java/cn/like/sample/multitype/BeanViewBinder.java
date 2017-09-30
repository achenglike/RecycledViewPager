package cn.like.sample.multitype;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import cn.like.sample.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by like on 2017/9/30.
 */

public class BeanViewBinder extends ItemViewBinder<Bean, BeanViewBinder.Holder> {
    static int []ranColor ={Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.WHITE,Color.LTGRAY};
    static Random random = new Random();


    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        Log.e("LIKE", "onCreateViewHolder:"+this.getClass().getSimpleName());
        View view = inflater.inflate(R.layout.item_text, parent, false);
        return new Holder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull Bean item) {
        holder.textView.setText(item.getInfo());
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setBackgroundColor(ranColor[random.nextInt(6)]);
        }
    }
}
