package work.yeshu.areacode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yeshu on 2016/11/25.
 * group decoration
 */

public class AreaCodeGroupDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Paint mTextPaint;
    private int mGroupHeight;

    public AreaCodeGroupDecoration(int groupHeight, int backgroundColor, int textSize, int textColor) {
        mGroupHeight = groupHeight;

        mPaint = new Paint();
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        AreaCode areaCode = (AreaCode) view.getTag();
        if (areaCode.isTop()) {
            // if item is top of the group, need set offset
            outRect.set(0, mGroupHeight, 0, 0);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //第一个可见view
        int firstVisibleItemPosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (RecyclerView.NO_POSITION == firstVisibleItemPosition) {
            return;
        }
        View firstVisibleView = parent.findViewHolderForAdapterPosition(firstVisibleItemPosition).itemView;
        AreaCode firstAreaCode = (AreaCode) firstVisibleView.getTag();

        //第二个可见view
        int secondVisibleItemPosition = firstVisibleItemPosition + 1;
        View secondVisibleItemView = parent.findViewHolderForAdapterPosition(secondVisibleItemPosition).itemView;
        boolean secondViewIsTop;
        if (null != secondVisibleItemView) {
            secondViewIsTop = ((AreaCode) secondVisibleItemView.getTag()).isTop();
        } else {
            secondViewIsTop = false;
        }

        //画顶部悬浮的group
        drawFloatGroup(c, firstVisibleView, firstAreaCode.getGroupName(), secondViewIsTop);
    }

    private void drawFloatGroup(Canvas canvas, View itemView, String groupName, boolean isTop) {
        int top;
        int bottom;
        if (isTop && itemView.getBottom() < mGroupHeight) {
            //画出部分到group
            top = itemView.getBottom() - mGroupHeight;
            bottom = itemView.getBottom();
        } else {
            //画出全部到group
            top = 0;
            bottom = mGroupHeight;
        }

        //draw background
        canvas.drawRect(0, top, itemView.getRight(), bottom, mPaint);

        //draw text
        Rect rect = new Rect();
        mTextPaint.getTextBounds(groupName, 0, groupName.length(), rect);
        int groupTextY = top + mGroupHeight / 2 + rect.height() / 2;
        canvas.drawText(groupName, 0, groupTextY, mTextPaint);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AreaCode areaCode = (AreaCode) parent.getChildAt(i).getTag();
            if (areaCode.isTop()) {
                // if item is top of the group, then draw group
                drawGroup(c, parent.getChildAt(i), areaCode.getGroupName());
            }
        }
    }

    private void drawGroup(Canvas canvas, View itemView, String groupName) {
        int top = itemView.getTop() - mGroupHeight;
        int bottom = itemView.getTop();
        int right = itemView.getRight();
        canvas.drawRect(0, top, right, bottom, mPaint);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(groupName, 0, groupName.length(), rect);
        int groupTextY = top + mGroupHeight / 2 + rect.height() / 2;
        canvas.drawText(groupName, 0, groupTextY, mTextPaint);
    }
}
