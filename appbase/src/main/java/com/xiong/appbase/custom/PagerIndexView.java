package com.xiong.appbase.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.xiong.appbase.R;


/**
 * @author xiong
 * @ClassName: PicIndexView
 * @Description: 实现轮播指示点的view
 * @date 2016/9/7
 */
public class PagerIndexView extends View {
    private int mCurrentPage = -1;
    private int mTotalPage = 0;

    public PagerIndexView(Context context) {
        super(context);
    }

    public PagerIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        Rect r = new Rect();
        //获取View的绘制范围，即左、上、右、下边界相对于此View的左顶点的距离(偏移量),即0、0、View的宽、View的高
        this.getDrawingRect(r);

        int iconWidth = 40;
        int iconHeight = 20;
        int space = 12;

        //x相当于左边距,作为水平绘制起点
        int x = (r.width() - (iconWidth * mTotalPage + space * (mTotalPage - 1))) / 2;
        //y相当于上边距,作为竖直绘制起点
        int y = (r.height() - iconHeight) / 2;

        for (int i = 0; i < mTotalPage; i++) {
            int resid = R.mipmap.indicator_black;
            //当前选中状态的点
            if (i == mCurrentPage) {
                resid = R.mipmap.indicator_sel;
            }
            //绘制图片区域
            Rect r1 = new Rect();
            r1.left = x;
            r1.top = y;
            r1.right = x + iconWidth;
            r1.bottom = y + iconHeight;

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), resid);
            canvas.drawBitmap(bmp, null, r1, paint);

            //迭代x,画下一个图
            x += iconWidth + space;

        }

    }
}
