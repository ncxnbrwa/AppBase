package com.xiong.appbase.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xiong.appbase.R;


/**
 * @author xiong
 * @ClassName: PicIndexView
 * @Description: 实现轮播指示点的view
 * @date 2016/9/7
 */
public class PagerIndexView extends View {
    private int mCurrentPage = 0;
    private int mTotalPage = 0;
    private Paint mPaint;
    //图标宽高
    private int iconWidth;
    private int iconHeight;
    //图标间隔
    private int space;
    //选中的图标和未选中的图标
    private Bitmap selectedBmp, unselectedBmp;
    //绘制Bitmap的矩形区域
    private Rect rect;

    public PagerIndexView(Context context) {
        this(context, null);
    }

    public PagerIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        iconWidth = dp2px(10);
        iconHeight = dp2px(10);
        //有些图标自带间隔,所以这里设置为0也没事
        space = dp2px(0);
        //第二个参数必须是图片,用XML资源使用会报错
        selectedBmp = BitmapFactory.decodeResource(getResources(), R.drawable.selected_indicator);
        unselectedBmp = BitmapFactory.decodeResource(getResources(), R.drawable.unselected_indicator);
        rect = new Rect();
    }

    //设置指示器总数,必须调用一次
    public void setTotalPage(int nPageNum) {
        mTotalPage = nPageNum;
        if (mCurrentPage >= mTotalPage)
            mCurrentPage = mTotalPage - 1;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int nPageIndex) {
        //采用求余,防止一些ViewPager使用了Integer.MAX_VALUE的方法无限轮播
        nPageIndex = nPageIndex % mTotalPage;
        if (nPageIndex < 0 || nPageIndex >= mTotalPage)
            return;

        if (mCurrentPage != nPageIndex) {
            mCurrentPage = nPageIndex;
            this.invalidate();
        }
    }

    //绑定ViewPager监听
    public void bindViewPager(final ViewPager vp) {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //x相当于左边距,作为水平绘制起点,减去padding,保证绘制在中间
        int x = (getWidth() - (iconWidth * mTotalPage + space * (mTotalPage - 1))) / 2;
        //y相当于上边距,作为竖直绘制起点
        int y = (getHeight() - iconHeight) / 2;
        for (int i = 0; i < mTotalPage; i++) {
            //绘制图片区域
//            rect.left = x;
//            rect.top = y;
//            rect.right = x + iconWidth;
//            rect.bottom = y + iconHeight;
            rect.set(x, y, x + iconWidth, y + iconHeight);
            //当前选中状态的点
            if (i == mCurrentPage) {
                canvas.drawBitmap(selectedBmp, null, rect, mPaint);
            } else {
                canvas.drawBitmap(unselectedBmp, null, rect, mPaint);
            }
            //迭代x,画下一个图
            x += iconWidth + space;
        }
    }

    private int dp2px(int values) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, values,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onDetachedFromWindow() {
        //及时销毁Bitmap资源
        if (null != selectedBmp && !selectedBmp.isRecycled()) {
            selectedBmp = null;
        }
        if (null != unselectedBmp && !unselectedBmp.isRecycled()) {
            unselectedBmp = null;
        }
        super.onDetachedFromWindow();
    }
}
