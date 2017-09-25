package cn.like.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridPagerSnapHelper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.MoocGridPagerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        GridPagerSnapHelper snapHelper = new GridPagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new MainAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
                Log.e("LOG", "first:"+gridLayoutManager.findFirstVisibleItemPosition()+", last:"+gridLayoutManager.findLastVisibleItemPosition());
            }
        });
        recyclerView.addItemDecoration(new MoocGridPagerItemDecoration((GridLayoutManager) recyclerView.getLayoutManager(), R.color.active, R.color.inactive));
    }


    public static class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

        List<Bean> beens = new ArrayList<>();

        public MainAdapter() {
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-13/0-mVxPiZ.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-13/0-boD1EM.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-12/0-xvcnpa.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-12/0-91i8Qv.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-11/0-6dQ4g2.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-11/0-dJLYQ5.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-11/0-2nPvGy.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-10/0-WRQrdX.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-12/0-ddKcPt.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-04/0-Qt94rG.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/06-20/0-7aUUnx.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/06-19/0-J29rIU.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/06-14/0-G4At5C.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/06-19/0-lWxGsW.jpg"));
            beens.add(new Bean("one","http://img.mingxing.com/upload/thumb/2017/07-13/0-mVxPiZ.jpg"));


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Bean bean = beens.get(position);
            holder.name.setText(bean.getName());
            Glide.with(holder.cover.getContext())
                    .load(bean.getUrl())
                    .centerCrop()
                    .crossFade()
                    .into(holder.cover);
        }

        @Override
        public int getItemCount() {
            return beens.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView cover;
            private TextView name;

            public ViewHolder(View itemView) {
                super(itemView);

                cover = (ImageView) itemView.findViewById(R.id.image);
                name = (TextView) itemView.findViewById(R.id.describe);
            }
        }
    }

    public static class Bean {
        String name;
        String url;

        public Bean(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
