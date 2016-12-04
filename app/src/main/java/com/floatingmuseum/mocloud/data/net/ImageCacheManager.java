package com.floatingmuseum.mocloud.data.net;

import android.net.Uri;
import android.os.Environment;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.utils.FileUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import rx.Observable;

/**
 * Created by Floatingmuseum on 2016/11/30.
 */

public class ImageCacheManager {

    private static final String IMAGE_DIR_NAME = "imageDirName";
    private static final String IMAGE_DIR_SIZE = "imageDirSize";
    private static File imageDir;
    private static long dirSize;

    public static void init(){
        String dirName = SPUtil.getString(IMAGE_DIR_NAME,"MoCloudImageCache/poster");
        //默认大小10mb
        dirSize = SPUtil.getLong(IMAGE_DIR_SIZE,10*1024*1024);
        imageDir = new File(Environment.getExternalStorageDirectory(), dirName);
        if (!imageDir.exists()){
            imageDir.mkdirs();
        }
    }

    public static File hasCacheImage(int tmdbID) {
        File[] files = imageDir.listFiles();
        for (File file : files) {
            Logger.d("文件名:" + file.getName() + "...imdbID:" + tmdbID + "...lastModifiedTime:" + file.lastModified());
            if (file.getName().contains(String.valueOf(tmdbID))) {
                Logger.d("文件已存在:" + tmdbID+"...Uri:"+file.toURI().toString());
                return file;
            }
        }
        Logger.d("文件不存在:" + tmdbID);
        return null;
    }

    public static Observable<TmdbMovieImage> localImage(int tmdbID,Uri fileUri){
        TmdbMovieImage tmdbMovieImage = new TmdbMovieImage();
        tmdbMovieImage.setHasCache(true);
        tmdbMovieImage.setId(tmdbID);
        tmdbMovieImage.setFileUri(fileUri);
        return Observable.just(tmdbMovieImage);
    }

    public static void writeToDisk(ResponseBody body, String fileName) {
        Logger.d("responseBody:" + body);
        long nowDirSize = getDirSize();
        Logger.d("图片缓存文件夹当前大小:" + nowDirSize+"...配置大小"+dirSize);
        if (nowDirSize > dirSize) {
            //计算超出文件夹限制的size
            long reduceSize = nowDirSize + body.contentLength() - dirSize;
            reduceDirSize(reduceSize);
        }
        if (body != null) {
            Logger.d("responseBody:" + body.contentLength() + "..." + fileName);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            File file = new File(imageDir, fileName);
            BufferedSink sink = null;
            try {
                sink = Okio.buffer(Okio.sink(file));
                sink.write(body.source(), body.contentLength());
                sink.flush();
                Logger.d("图片"+file.getName() + "...保存到..." + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (sink != null) {
                    try {
                        sink.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static long getDirSize() {
        long size = 0;
        File[] files = imageDir.listFiles();
        for (File file : files) {
            Logger.d("图片:"+file.getName()+"...大小:"+ FileUtil.bytesToKb(file.length()));
            size += file.length();
        }
        return size;
    }

    /**
     * 降低图片文件夹大小
     */
    private static void reduceDirSize(long reduceSize) {
        File[] files = imageDir.listFiles();
        //按图片最后修改时间排序
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return (int) (file1.lastModified()-file2.lastModified());
            }
        });

        //获取足够图片数量大小来删除，以达到降低文件夹大小到设置值
        List<File> toRemove = new ArrayList<>();
        long toRemoveSize = 0;
        for (File file : files) {
            Logger.d("图片:"+file.getName()+"...最后修改时间:"+file.lastModified());
            toRemove.add(file);
            toRemoveSize+=file.length();
            if (toRemoveSize>reduceSize){
                break;
            }
        }

        //删除
        for (File file : toRemove) {
            Logger.d("图片被删除:"+file.getName());
            file.delete();
        }
    }
}
