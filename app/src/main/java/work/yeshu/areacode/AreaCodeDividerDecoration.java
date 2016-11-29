package work.yeshu.areacode;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yeshu on 2016/11/25.
 * divider decoration
 */

public class AreaCodeDividerDecoration extends RecyclerView.ItemDecoration {
    private final Drawable mDivider;
    private final int mPaddingLeft;
    private final int mPaddingRight;

    public AreaCodeDividerDecoration(Drawable divider, int paddingLeft, int paddingRight) {
        mDivider = divider;
        mPaddingLeft = paddingLeft;
        mPaddingRight = paddingRight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            mDivider.setBounds(mPaddingLeft, child.getBottom() - 1, child.getRight() - mPaddingRight, child.getBottom());
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
