package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class ImageLoader {
    public static void load(Context context,String url,ImageView view){
        Glide.with(context).load(url).into(view);
    }
}
