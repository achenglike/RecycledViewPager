package android.support.v7.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by like on 2017/9/25.
 */

public class HorizonGridPagerItemDecoration extends RecyclerView.ItemDecoration {

    private int colorActive = 0xFFFFFFFF;
    private int colorInactive = 0x66FFFFFF;
    private int spanCount;
    private int lastSelectedPostion;

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private final int mIndicatorHeight = (int) (DP * 16);

    /**
     * Indicator stroke width.
     */
    private final float mIndicatorStrokeWidth = DP * 2;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemLength = DP * 16;
    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 4;

    /**
     * Some more natural animation interpolation
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    public HorizonGridPagerItemDecoration(GridLayoutManager gridLayoutManager) {
        if (gridLayoutManager.canScrollVertically())
            throw new IllegalStateException("only work for Horizontal GridLayoutManager");
        spanCount = gridLayoutManager.getSpanCount();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getAdapter().getItemCount();

        int pointCount = itemCount%spanCount > 0 ? itemCount/spanCount+1 : itemCount/spanCount;

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * pointCount;
        float paddingBetweenItems = Math.max(0, pointCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, pointCount);


        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            activePosition = lastSelectedPostion;
        }
        lastSelectedPostion = activePosition;
        activePosition = activePosition%spanCount > 0 ? activePosition/spanCount+1 : activePosition/spanCount;
        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition);
    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        mPaint.setColor(colorInactive);

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY, int highlightPosition) {
        mPaint.setColor(colorActive);
        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        // no swipe, draw a normal indicator
        float highlightStart = indicatorStartX + itemWidth * highlightPosition;
        c.drawLine(highlightStart, indicatorPosY,
                highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mIndicatorHeight;
    }

}
