package com.lee.myapplication.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lee.myapplication.R;
import com.lee.myapplication.Util;

/**
 * Created by alvinlee on 16/7/26.
 */
public class CustomView extends View{

    private Paint mPaint;
    private Context mContext;

    private Bitmap mBitmap;

    private int x, y;

    private boolean mIsClicked;


    public CustomView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
        initRes();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsClicked) {
                    mIsClicked = false;
                    mPaint.setColorFilter(null);
                } else {
                    mIsClicked = true;
                    ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                            -1, 0, 0, 1, 1,
                            0, -1, 0, 1, 1,
                            0, 0, -1, 1, 1,
                            0, 0, 0, 1, 0,
                    });
                    mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                }

                invalidate();
            }
        });
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 生成色彩矩阵
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0.5F, 0, 0, 0, 0,
//                0, 0.5F, 0, 0, 0,
//                0, 0, 0.5F, 0, 0,
//                0, 0, 0, 1, 0,
//        });
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0.33F, 0.59F, 0.11F, 0, 0,
//                0.33F, 0.59F, 0.11F, 0, 0,
//                0.33F, 0.59F, 0.11F, 0, 0,
//                0, 0, 0, 1, 0,
//        });
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                -1, 0, 0, 1, 1,
//                0, -1, 0, 1, 1,
//                0, 0, -1, 1, 1,
//                0, 0, 0, 1, 0,
//        });
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
    }

    private void initRes() {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pingmu);

        x = Util.getScreenWidth(mContext) / 2 - mBitmap.getWidth() / 2;
        y = Util.getScreenHeight(mContext) / 2 - mBitmap.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, x, y, mPaint);
    }

}
