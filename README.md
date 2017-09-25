# RecycledViewPager

RecyclerView实现的ViewPager效果，每一页可以有多个Item。主要实现就是GridPagerSnapHelper完成的。
![效果图](https://github.com/achenglike/RecycledViewPager/raw/master/img/gif_20170925_183449.gif)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
  dependencies {
	        compile 'com.github.achenglike:RecycledViewPager:1.0.0'
	}


```
final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
recyclerView.setLayoutManager(gridLayoutManager);
GridPagerSnapHelper snapHelper = new GridPagerSnapHelper();
snapHelper.attachToRecyclerView(recyclerView);
recyclerView.addItemDecoration(new MoocGridPagerItemDecoration((GridLayoutManager) recyclerView.getLayoutManager(), R.color.active, R.color.inactive));
```

