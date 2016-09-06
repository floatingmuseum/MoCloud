package com.floatingmuseum.mocloud.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.floatingmuseum.mocloud.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Floatingmuseum on 2016/4/22.
 */
public class RatioImageView extends ImageView{

    private float attrValue;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RationImageView);
        attrValue = ta.getFloat(R.styleable.RationImageView_image_ratio,0);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取测量后的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        Logger.d("屏幕宽度："+(getResources().getDisplayMetrics().widthPixels/3)+"..."+width);
        if (attrValue != 0) {
            //通过宽高比计算出高度的数值
            float height = width * attrValue;
            //计算一个新的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
