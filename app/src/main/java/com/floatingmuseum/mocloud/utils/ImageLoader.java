package com.floatingmuseum.mocloud.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.R;
import com.orhanobut.logger.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class ImageLoader {

    public static void loadDefault(Context context, ImageView view) {
        Glide.with(context).load(R.drawable.default_movie_poster).into(view);
    }

    public static void load(Context context, String url, ImageView view, int placeHolder) {
        Drawable default_image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            default_image = context.getResources().getDrawable(placeHolder, null);
        } else {
            default_image = context.getResources().getDrawable(placeHolder);
        }
            Glide.with(context)
                    .load(url)
                    .placeholder(default_image)
                    .into(view);
    }

    public static void load(Context context, File file, ImageView view, int placeHolder) {
        Drawable default_image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            default_image = context.getResources().getDrawable(placeHolder, null);
        } else {
            default_image = context.getResources().getDrawable(placeHolder);
        }
        Glide.with(context)
                .load(file).crossFade()
                .placeholder(default_image)
                .into(view);
    }

    public static void load(Context context, Uri fileUri, ImageView view, int placeHolder) {
        Drawable default_image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            default_image = context.getResources().getDrawable(placeHolder, null);
        } else {
            default_image = context.getResources().getDrawable(placeHolder);
        }

        Glide.with(context)
                .load(fileUri)
                .placeholder(default_image)
                .into(view);
    }

    public static void loadDontAnimate(Context context, String url, final ImageView view, int placeHolder) {
        Drawable default_image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            default_image = context.getResources().getDrawable(placeHolder, null);
        } else {
            default_image = context.getResources().getDrawable(placeHolder);
        }

        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(default_image)
                .into(view);
    }

    public static void loadFromDrawable(Context context, int drawable, ImageView view) {
        Glide.with(context)
                .load(drawable)
                .into(view);
    }
}
