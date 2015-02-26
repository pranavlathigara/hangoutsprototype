package com.ekc.hangoutsprototype;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Custom image view to apply a circle mask on top of a profile image
 */
public class CircleImageView extends ImageView {
    private static final String TAG = CircleImageView.class.getSimpleName();
    private Drawable mMaskDrawable;
    private Bitmap mCachedBitmap;
    private Paint mMaskedPaint;

    private Path mPath = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);;

    public CircleImageView(Context context) {
        super(context);
        Log.i(TAG, "constructor 0");
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "constructor 1");

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);

        mMaskDrawable = a.getDrawable(R.styleable.CircleImageView_maskDrawable);
        if (mMaskDrawable != null) {
            mMaskDrawable.setCallback(this);
        }

        mMaskedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mCachedBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

        a.recycle();

        // Xfermode won't work if hardware accelerated
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Log.i(TAG, "constructor 2");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        // if mask drawable is assigned, turn it into a circle view
        // else, draw on canvas like regular imageview
        if (mMaskDrawable != null) {

            mMaskDrawable.setBounds(getPaddingLeft(),
                    getPaddingTop(),
                    canvas.getWidth() - getPaddingRight(),
                    canvas.getHeight() - getPaddingBottom());
            mMaskDrawable.draw(canvas);

            int layer = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mMaskedPaint,
                    Canvas.ALL_SAVE_FLAG);

            super.onDraw(canvas);
            canvas.restoreToCount(layer);

        } else {
            super.onDraw(canvas);
        }
    }
}
