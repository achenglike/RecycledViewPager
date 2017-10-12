package cn.like.sample.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridPagerSnapHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.MoocGridPagerItemDecoration;
import android.support.v7.widget.PageSelectedListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cn.like.sample.R;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by like on 2017/9/30.
 */

public class CardViewBinder extends ItemViewBinder<Card, CardViewBinder.Holder> {


    RecyclerView.RecycledViewPool recycledViewPool;

    public CardViewBinder(RecyclerView.RecycledViewPool recycledViewPool) {
        this.recycledViewPool = recycledViewPool;
    }

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_pager, parent, false);
        return new Holder(view, recycledViewPool);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull Card item) {
        holder.setPosts(item.getBeanList());
    }

    static class Holder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        MultiTypeAdapter multiTypeAdapter;

        public Holder(final View itemView, RecyclerView.RecycledViewPool recycledViewPool) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            recyclerView.setRecycledViewPool(recycledViewPool);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 3, LinearLayoutManager.HORIZONTAL, false);
            gridLayoutManager.setRecycleChildrenOnDetach(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            GridPagerSnapHelper snapHelper = new GridPagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.addItemDecoration(new MoocGridPagerItemDecoration((GridLayoutManager) recyclerView.getLayoutManager(), R.color.active, R.color.inactive));
            multiTypeAdapter = new MultiTypeAdapter();
            multiTypeAdapter.register(Bean.class, new BeanViewBinder());
            recyclerView.setAdapter(multiTypeAdapter);

            snapHelper.setPageSelectedListener(new PageSelectedListener() {
                @Override
                public void onPageSelected(int page) {
                    Toast.makeText(itemView.getContext(), "选中了第"+page+"页", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setPosts(List<Bean> beans) {
            multiTypeAdapter.setItems(beans);
            multiTypeAdapter.notifyDataSetChanged();
        }
    }
}
