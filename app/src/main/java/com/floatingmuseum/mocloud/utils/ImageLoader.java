package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.R;
import com.orhanobut.logger.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class ImageLoader {
    public static void load(Context context,String url,ImageView view,int placeHolder){
        Drawable default_image;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            default_image = context.getResources().getDrawable(placeHolder,null);
        }else{
            default_image = context.getResources().getDrawable(placeHolder);
        }
        Glide.with(context)
                .load(url)
                .placeholder(default_image)
                .into(view);
    }

    public static void loadDontAnimate(Context context, String url, final ImageView view, int placeHolder){
        Drawable default_image;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            default_image = context.getResources().getDrawable(placeHolder,null);
        }else{
            default_image = context.getResources().getDrawable(placeHolder);
        }

        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(default_image)
                .into(view);
    }
}
