package work.yeshu.areacode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeshu on 2016/11/25.
 * index list view
 */

public class IndexView extends View {
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_LINE_SPACE = 24;
    private static final int DEFAULT_TEXT_SIZE = 36;

    private Paint mTextPaint;
    private int mTextSize;
    private int mTextColor;
    private int mLineSpace;
    private Rect mRect;

    private int mTextHeight;
    private List<String> mIndexList = new ArrayList<>();

    private OnSelectedListener mListener = null;

    public IndexView(Context context) {
        super(context);

        mTextColor = DEFAULT_TEXT_COLOR;
        mTextSize = DEFAULT_TEXT_SIZE;
        mLineSpace = DEFAULT_LINE_SPACE;

        init();
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndexView, defStyleAttr, 0);

        try {
            mTextColor = a.getColor(R.styleable.IndexView_textColor, DEFAULT_TEXT_COLOR);
            mTextSize = a.getDimensionPixelOffset(R.styleable.IndexView_textSize, DEFAULT_TEXT_SIZE);
            mLineSpace = a.getDimensionPixelOffset(R.styleable.IndexView_lineSpace, DEFAULT_LINE_SPACE);

        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mRect = new Rect();
    }

    public void setIndexList(List<String> indexList) {
        if (null == indexList) {
            return;
        }

        mIndexList = indexList;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIndexList.isEmpty()) {
            setMeasuredDimension(resolveSize(0, widthMeasureSpec), resolveSize(0, heightMeasureSpec));
            return;
        }

        mTextPaint.getTextBounds(String.valueOf(mIndexList.get(0)), 0, 1, mRect);
        mTextHeight = mRect.height();

        int totalHeight = (mTextHeight + mLineSpace) * mIndexList.size() + getPaddingTop() + getPaddingBottom();
        int totalWidth = mRect.width() + getPaddingLeft() + getPaddingRight();

        setMeasuredDimension(resolveSize(totalWidth, widthMeasureSpec), resolveSize(totalHeight, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mIndexList.size(); i++) {
            canvas.drawText(String.valueOf(mIndexList.get(i)), getPaddingLeft(), calculateIndexY(i), mTextPaint);
        }
    }

    private int calculateIndexY(int i) {
        return mTextHeight * (i + 1) + i * mLineSpace + getPaddingTop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                checkSelect(event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                checkSelect(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void checkSelect(float y) {
        if (null == mListener) {
            return;
        }

        if (y < getPaddingTop() || y > (getMeasuredHeight() - getPaddingBottom())) {
            return;
        }

        int position = (int) ((y - getPaddingTop()) / (mTextHeight + mLineSpace));
        mListener.onSelected(mIndexList.get(position));
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        invalidate();
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        invalidate();
    }

    public void setLineSpace(int lineSpace) {
        mLineSpace = lineSpace;
        requestLayout();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        mListener = listener;
    }

    public interface OnSelectedListener {
        void onSelected(String index);
    }
}
