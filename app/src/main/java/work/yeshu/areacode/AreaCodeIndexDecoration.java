package work.yeshu.areacode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class AreaCodeIndexDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Paint mTextPaint;
    private int mIndexViewHeight;

    AreaCodeIndexDecoration(int indexViewHeight, int backgroundColor, int textSize, int textColor) {
        mIndexViewHeight = indexViewHeight;

        mPaint = new Paint();
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        Item item = (Item) view.getTag();
        if (item.isTop()) {
            // if item is top of the group, need set offset
            outRect.set(0, mIndexViewHeight, 0, 0);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //第一个可见view
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (null == layoutManager) {
            return;
        }
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (RecyclerView.NO_POSITION == firstVisibleItemPosition) {
            return;
        }
        RecyclerView.ViewHolder firstVisibleViewHolder = parent.findViewHolderForAdapterPosition(firstVisibleItemPosition);
        if (null == firstVisibleViewHolder) {
            return;
        }
        View firstVisibleView = firstVisibleViewHolder.itemView;
        Item firstItem = (Item) firstVisibleView.getTag();

        //第二个可见view
        int secondVisibleItemPosition = firstVisibleItemPosition + 1;
        boolean secondViewIsTop;
        RecyclerView.ViewHolder secondVisibleViewHolder = parent.findViewHolderForAdapterPosition(secondVisibleItemPosition);
        if (null == secondVisibleViewHolder) {
            secondViewIsTop = false;
        } else {
            View secondVisibleItemView = secondVisibleViewHolder.itemView;
            secondViewIsTop = ((Item) secondVisibleItemView.getTag()).isTop();
        }

        //画顶部悬浮的group
        drawFloatIndexView(c, firstVisibleView, firstItem.getIndex(), secondViewIsTop);
    }

    private void drawFloatIndexView(Canvas canvas, View itemView, String indexStr, boolean isTop) {
        int top;
        int bottom;
        int right = itemView.getRight();
        if (isTop && itemView.getBottom() < mIndexViewHeight) {
            //画出部分到group
            top = itemView.getBottom() - mIndexViewHeight;
            bottom = itemView.getBottom();
        } else {
            //画出全部到group
            top = 0;
            bottom = mIndexViewHeight;
        }

        drawIndexView(canvas, indexStr, new Rect(0, top, right, bottom));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Item item = (Item) parent.getChildAt(i).getTag();
            if (item.isTop()) {
                // if item is top of the group, then draw group
                drawNormalGroup(c, parent.getChildAt(i), item.getIndex());
            }
        }
    }

    private void drawNormalGroup(Canvas canvas, View itemView, String index) {
        int top = itemView.getTop() - mIndexViewHeight;
        int bottom = itemView.getTop();
        int right = itemView.getRight();

        drawIndexView(canvas, index, new Rect(0, top, right, bottom));
    }

    //draw index view
    private void drawIndexView(Canvas canvas, String indexStr, Rect rect) {
        canvas.drawRect(rect, mPaint);
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(indexStr, 0, indexStr.length(), textRect);
        int groupTextY = rect.top + rect.height() / 2 + textRect.height() / 2;
        canvas.drawText(indexStr, 0, groupTextY, mTextPaint);
    }

}
