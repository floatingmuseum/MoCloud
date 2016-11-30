package com.floatingmuseum.mocloud.data.net;

import android.os.Environment;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;

import java.io.File;

import rx.Observable;

/**
 * Created by Floatingmuseum on 2016/11/30.
 */

public class ImageCacheManager {

    private static ImageCacheManager manager = new ImageCacheManager();
    private static File imageDir;


    private ImageCacheManager(){
        imageDir = new File(Environment.getExternalStorageDirectory(), "FanartImageFolder");
    }

    public static boolean hasImageCache(int tmdbID){
        File[] files = imageDir.listFiles();
        for (File file : files) {
            if (file.getName().contains(String.valueOf(tmdbID))) {
                return true;
            }
        }
        return false;
    }
}
