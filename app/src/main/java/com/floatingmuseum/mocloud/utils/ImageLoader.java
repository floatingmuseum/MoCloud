package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPersonImage;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class ImageLoader {

    public static void loadDefault(Context context, ImageView view, int placeHolder) {
        Glide.with(context).load(placeHolder).into(view);
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
        Logger.d("图片加载URI：" + Uri.fromFile(file));
        Glide.with(context)
                .load(Uri.fromFile(file)).crossFade()
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

    public static void load(Context context, Bitmap bitmap, ImageView view, int placeHolder) {
        Drawable default_image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            default_image = context.getResources().getDrawable(placeHolder, null);
        } else {
            default_image = context.getResources().getDrawable(placeHolder);
        }

        Glide.with(context)
                .load(bitmap)
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

    public static void loadArtImage(Context context, ArtImage image, final ImageView view, int type) {
        if (ImageCacheManager.TYPE_BACKDROP == type) {
            if (image.getLocalBackdropUri() != null) {
                load(context, image.getLocalBackdropUri(), view, R.drawable.default_fanart);
            } else if (TextUtils.isEmpty(image.getRemoteBackdropUrl())) {
                load(context, image.getRemoteBackdropUrl(), view, R.drawable.default_fanart);
            } else {
                loadDefault(context, view, R.drawable.default_fanart);
            }
        } else if (ImageCacheManager.TYPE_POSTER == type) {
            if (image.getLocalBackdropUri() != null) {
                load(context, image.getLocalPosterUri(), view, R.drawable.default_movie_poster);
            } else if (TextUtils.isEmpty(image.getRemoteBackdropUrl())) {
                load(context, image.getRemotePosterUrl(), view, R.drawable.default_movie_poster);
            } else {
                loadDefault(context, view, R.drawable.default_movie_poster);
            }
        } else {
            if (image.getLocalBackdropUri() != null) {
                load(context, image.getLocalAvatarUri(), view, R.drawable.default_movie_poster);
            } else if (TextUtils.isEmpty(image.getRemoteBackdropUrl())) {
                load(context, image.getRemoteAvatarUrl(), view, R.drawable.default_movie_poster);
            } else {
                loadDefault(context, view, R.drawable.default_movie_poster);
            }
        }
    }

    public static void loadFromTmdbPersonImage(Context context, TmdbPersonImage image, final ImageView view, int placeHolder) {
        if (image.isHasCache()) {
            Logger.d("Load from sdcard");
            load(context, image.getCacheFile(), view, placeHolder);
        } else if (image != null && image.getProfiles() != null && image.getProfiles().size() > 0) {
            Logger.d("Load from web");
            load(context, StringUtil.buildPeopleHeadshotUrl(image.getProfiles().get(0).getFile_path()), view, placeHolder);
        } else {
            loadDefault(context, view,placeHolder);
        }
    }

    public static void loadPoster(Context context, RatioImageView posterView, Movie movie, int placeHolder) {
//        TmdbMovieImage image = movie.getImage();
//        Logger.d("MovieName:" + movie.getTitle() + "..." + image);
//        if (image != null) {
//            if (image.isHasCache()) {
//                File file = image.getCacheFile();
//                load(context, file, posterView, R.drawable.default_movie_poster);
//                Logger.d("图片从本地加载:" + movie.getTitle() + "..." + file.getName());
//                return;
//            } else if (image.isHasPoster()) {
//                String tmdbPosterUrl = StringUtil.buildImageUrl(image.getPosters().get(0).getFile_path());
//                load(context, tmdbPosterUrl, posterView, R.drawable.default_movie_poster);
//                Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getId() + "...tmdbPosterUrl:" + tmdbPosterUrl);
//                return;
//            }
//        }
        Logger.d("没有图片showImage:" + movie.getTitle());
        loadDefault(context, posterView,placeHolder);
    }

    public static void loadFromDrawable(Context context, int drawable, ImageView view) {
        Glide.with(context)
                .load(drawable)
                .into(view);
    }
}
