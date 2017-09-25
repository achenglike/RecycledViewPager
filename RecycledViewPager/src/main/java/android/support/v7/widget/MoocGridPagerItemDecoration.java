package android.support.v7.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by like on 2017/9/25.
 */

public class MoocGridPagerItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int lastSelectedPosition;
    private @ColorRes int activeColor;
    private @ColorRes int inActiveColor;

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private int mIndicatorHeight = (int) (DP * 16);

    /**
     * Indicator stroke width.
     */
    private final float mIndicatorStrokeWidth = DP * 8;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemLength = DP * 8;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemSelectedLength = DP * 32;

    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 8;

    private final Paint activePaint = new Paint();
    private final Paint indicatorPaint = new Paint();

    public MoocGridPagerItemDecoration(GridLayoutManager gridLayoutManager, int activeColor, int inActiveColor) {
        if (gridLayoutManager.canScrollVertically())
            throw new IllegalStateException("only work for Horizontal GridLayoutManager");
        this.activeColor = activeColor;
        this.inActiveColor = inActiveColor;

        spanCount = gridLayoutManager.getSpanCount();
        activePaint.setStrokeCap(Paint.Cap.ROUND);
        activePaint.setStrokeWidth(mIndicatorStrokeWidth);
        activePaint.setStyle(Paint.Style.STROKE);
        activePaint.setAntiAlias(true);

        indicatorPaint.setAntiAlias(true);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        activePaint.setColor(parent.getResources().getColor(activeColor));
        indicatorPaint.setColor(parent.getResources().getColor(inActiveColor));

        int itemCount = parent.getAdapter().getItemCount();

        int pointCount = itemCount%spanCount > 0 ? itemCount/spanCount+1 : itemCount/spanCount;

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * (pointCount-1) + mIndicatorItemSelectedLength;
        float paddingBetweenItems = Math.max(0, pointCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            activePosition = lastSelectedPosition;
        }
        lastSelectedPosition = activePosition;
        activePosition = activePosition%spanCount > 0 ? activePosition/spanCount+1 : activePosition/spanCount;
        drawIndicators(c, indicatorStartX, indicatorPosY, pointCount, activePosition);
    }

    private void drawIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount, int highlightPosition) {
        float start = indicatorStartX;

        for (int i = 0; i < itemCount; i++) {
            if (i == highlightPosition) {
                c.drawLine(start, indicatorPosY, start + mIndicatorItemSelectedLength, indicatorPosY, activePaint);
                start += mIndicatorItemSelectedLength;
            } else {
                c.drawCircle(start+mIndicatorItemLength/2, indicatorPosY, mIndicatorItemLength/2, indicatorPaint);
                start += mIndicatorItemLength;
            }
            start += mIndicatorItemPadding;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mIndicatorHeight;
    }

    public void setmIndicatorHeight(int mIndicatorHeight) {
        this.mIndicatorHeight = mIndicatorHeight;
    }
}
