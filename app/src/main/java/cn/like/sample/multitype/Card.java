package cn.like.sample.multitype;

import java.util.List;

/**
 * Created by like on 2017/9/30.
 */

public class Card {
    private List<Bean> beanList;

    public Card(List<Bean> beanList) {
        this.beanList = beanList;
    }

    public List<Bean> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Bean> beanList) {
        this.beanList = beanList;
    }
}
