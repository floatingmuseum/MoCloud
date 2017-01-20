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
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
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
                .error(default_image)
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

    public static void loadFromTmdbMovieImage(Context context, TmdbMovieImage image, final ImageView view, int placeHolder) {
        if (image != null) {
            if (image.isHasCache()) {
                load(context, image.getCacheFile(), view, R.drawable.default_movie_poster);
            } else if (image.isHasPoster()) {
                load(context, StringUtil.buildPosterUrl(image.getPosters().get(0).getFile_path()), view, R.drawable.default_movie_poster);
            }
        } else {
            loadDefault(context, view);
        }
    }

    public static void loadPoster(Context context, RatioImageView posterView, Movie movie, int placeHolder) {
        TmdbMovieImage image = movie.getImage();
        Logger.d("MovieName:" + movie.getTitle() + "..." + image);
        if (image != null) {
            if (image.isHasCache()) {
                File file = image.getCacheFile();
                load(context, file, posterView, R.drawable.default_movie_poster);
                Logger.d("图片从本地加载:" + movie.getTitle() + "..." + file.getName());
                return;
            } else if (image.isHasPoster()) {
                String tmdbPosterUrl = StringUtil.buildPosterUrl(image.getPosters().get(0).getFile_path());
                load(context, tmdbPosterUrl, posterView, R.drawable.default_movie_poster);
                Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getId() + "...tmdbPosterUrl:" + tmdbPosterUrl);
                return;
            }
        }
        Logger.d("没有图片showImage:" + movie.getTitle());
        loadDefault(context, posterView);
    }

    public static void loadFromDrawable(Context context, int drawable, ImageView view) {
        Glide.with(context)
                .load(drawable)
                .into(view);
    }
}
