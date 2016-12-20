package com.floatingmuseum.mocloud.data.net;

import android.Manifest;
import android.net.Uri;
import android.os.Environment;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.TmdbImage;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeopleImage;
import com.floatingmuseum.mocloud.utils.FileUtil;
import com.floatingmuseum.mocloud.utils.PermissionsUtil;
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
    public static final int TYPE_POSTER = 0;
    public static final int TYPE_AVATAR = 1;

    private static final String POSTER_DIR_NAME = "imageDirName";
    private static final String AVATAR_DIR_NAME = "avatarDirName";
    private static final String IMAGE_DIR_SIZE = "imageDirSize";
    private static File posterDir;
    private static File avatarDir;
    private static long dirSize;

    public static void init() {
        String posterDirName = SPUtil.getString(POSTER_DIR_NAME, "MoCloudImageCache/poster");
        String avatarDirName = SPUtil.getString(AVATAR_DIR_NAME, "MoCloudImageCache/avatar");
        //默认大小10mb
        long cacheDirSize = SPUtil.getLong(IMAGE_DIR_SIZE, 10 * 1024 * 1024);
        //海报和头像各占一半缓存
        dirSize = cacheDirSize / 2;
        posterDir = new File(Environment.getExternalStorageDirectory(), posterDirName);
        avatarDir = new File(Environment.getExternalStorageDirectory(), avatarDirName);
        initDir();
    }

    private static void initDir(){
        if (!posterDir.exists()) {
            posterDir.mkdirs();
        }
        if (!avatarDir.exists()) {
            avatarDir.mkdirs();
        }
    }

    public static File hasCacheImage(int tmdbID, int imageType) {
        if (!PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.d("没有读写权限，不检查Cache");
            return null;
        }

        File[] files;
        initDir();
        if (imageType == TYPE_POSTER) {
            files = posterDir.listFiles();
        } else {
            files = avatarDir.listFiles();
        }

        for (File file : files) {
            String fileName = file.getName();
            String id = fileName.substring(fileName.indexOf("-")+1,fileName.indexOf("."));
            Logger.d("文件名:" + file.getName() + "...TmdbID:" + tmdbID +"切割后:"+id+ "...lastModifiedTime:" + file.lastModified());
            if (id.equals(String.valueOf(tmdbID))) {
                Logger.d("文件已存在:" + tmdbID+"...Uri:"+file.toURI().toString());
                return file;
            }
        }
        Logger.d("文件不存在:" + tmdbID);
        return null;
    }

    public static Observable<TmdbMovieImage> localPosterImage(int tmdbID, File file) {
        TmdbMovieImage tmdbMovieImage = new TmdbMovieImage();
        tmdbMovieImage.setHasCache(true);
        tmdbMovieImage.setId(tmdbID);
        tmdbMovieImage.setCacheFile(file);
        return Observable.just(tmdbMovieImage);
    }

    public static Observable<TmdbPeopleImage> localAvatarImage(int tmdbID, File file) {
        TmdbPeopleImage tmdbPeopleImage = new TmdbPeopleImage();
        tmdbPeopleImage.setHasCache(true);
        tmdbPeopleImage.setId(tmdbID);
        tmdbPeopleImage.setCacheFile(file);
        return Observable.just(tmdbPeopleImage);
    }

    public static void writeToDisk(ResponseBody body, String fileName, int imageType) {
        File dir = imageType == TYPE_POSTER ? posterDir : avatarDir;
        long nowDirSize = getDirSize(dir);
        Logger.d("图片缓存文件夹当前大小:" + nowDirSize + "...配置大小:" + dirSize + "...KB大小:" + FileUtil.bytesToKb(nowDirSize) + "...MB大小:" + FileUtil.bytesToMb(nowDirSize));
        if (nowDirSize > dirSize) {
            //计算超出文件夹限制的size
            long reduceSize = nowDirSize + body.contentLength() - dirSize;
            reduceDirSize(reduceSize, dir);
        }
        if (body != null) {
            Logger.d("responseBody:" + body.contentLength() + "..." + fileName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            BufferedSink sink = null;
            try {
                sink = Okio.buffer(Okio.sink(file));
                sink.write(body.source(), body.contentLength());
                sink.flush();
                Logger.d("图片" + file.getName() + "...保存到..." + file.getAbsolutePath());
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
                if (body != null) {
                    body.close();
                }
            }
        }
    }

    private static long getDirSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            Logger.d("图片:" + file.getName() + "...大小:" + FileUtil.bytesToKb(file.length()));
            size += file.length();
        }
        return size;
    }

    /**
     * 降低图片文件夹大小
     */
    private static void reduceDirSize(long reduceSize, File dir) {
        File[] files = dir.listFiles();
        //按图片最后修改时间排序
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return (int) (file1.lastModified() - file2.lastModified());
            }
        });

        //获取足够图片数量大小来删除，以达到降低文件夹大小到设置值
        List<File> toRemove = new ArrayList<>();
        long toRemoveSize = 0;
        for (File file : files) {
            Logger.d("图片:" + file.getName() + "...最后修改时间:" + file.lastModified());
            toRemove.add(file);
            toRemoveSize += file.length();
            if (toRemoveSize > reduceSize) {
                break;
            }
        }

        //删除
        for (File file : toRemove) {
            Logger.d("图片被删除:" + file.getName());
            file.delete();
        }
    }
}
