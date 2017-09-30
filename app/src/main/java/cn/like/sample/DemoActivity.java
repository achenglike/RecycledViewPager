package cn.like.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.like.sample.multitype.Bean;
import cn.like.sample.multitype.Card;
import cn.like.sample.multitype.CardViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by like on 2017/9/30.
 */

public class DemoActivity extends AppCompatActivity {

    static String one = "hello";
    static String two = "RecyclerView网上很多文都说是用来取代ListView和GridView的，事实上RecyclerView的确可以做到ListView和GridView能做的事";
    static String three = "看了上面的这段话，我产生了一个疑问，第一段话的意思仿佛是只有在需要新的类型的ViewHolder的时候才需要调用这个方法。如果是这样，的确可以从侧面说明他是以ViewHolder为单位来实现复用的。为了验证我的想法，我在onCreateViewHolder和onBindViewHolder方法中加入了计数的代码，看一下log：";
    private String[] infos = new String[]{one, two, three};

    RecyclerView recyclerView;
    MultiTypeAdapter multiTypeAdapter;

    RecyclerView.RecycledViewPool mPool = new RecyclerView.RecycledViewPool(){
        @Override
        public RecyclerView.ViewHolder getRecycledView(int viewType) {
            RecyclerView.ViewHolder scrap = super.getRecycledView(viewType);
            Log.i("@@@", "get holder from pool: "+scrap );
            return scrap;
        }

        @Override
        public void putRecycledView(RecyclerView.ViewHolder scrap) {
            super.putRecycledView(scrap);
            Log.i("@@@", "put holder to pool: " + scrap);
        }

        @Override
        public String toString() {
            return "ViewPool in adapter@"+Integer.toHexString(hashCode());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(Card.class, new CardViewBinder(mPool));
        recyclerView.setAdapter(multiTypeAdapter);

        //mock data
        Items items = new Items();
        for (int i = 0; i < 100; i++) {
            items.add(createCard());
        }
        multiTypeAdapter.setItems(items);
        multiTypeAdapter.notifyDataSetChanged();

    }

    private Card createCard() {
        List<Bean> beans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            beans.add(createRandomBean());
        }
        return new Card(beans);
    }


    Random rand = new Random( );
    private Bean createRandomBean() {
        Bean bean = new Bean();
        int i = rand.nextInt(3);
        bean.setInfo(infos[i]);
        return bean;
    }
}
