package com.floatingmuseum.mocloud.data.net;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPersonImage;
import com.floatingmuseum.mocloud.utils.FileUtil;
import com.floatingmuseum.mocloud.utils.PermissionsUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by Floatingmuseum on 2016/11/30.
 */

public class ImageCacheManager {
    public static final int TYPE_POSTER = 0;
    public static final int TYPE_AVATAR = 1;
    public static final int TYPE_BACKDROP = 2;

    private static final String POSTER_DIR_NAME = "imageDirName";
    private static final String AVATAR_DIR_NAME = "avatarDirName";
    private static final String BACKDROP_DIR_NAME = "backdropDirName";

    private static final String IMAGE_DIR_SIZE = "imageDirSize";

    private static File posterDir;
    private static File avatarDir;
    private static File backdropDir;
    private static long dirSize;
    private static boolean reducing = false;

    public static void init(Context context) {
        String posterDirName = SPUtil.getString(POSTER_DIR_NAME, "poster");
        String avatarDirName = SPUtil.getString(AVATAR_DIR_NAME, "avatar");
        String backdropDirName = SPUtil.getString(BACKDROP_DIR_NAME, "backdrop");
//        MoCloudImageCache/
        //默认大小10mb
        long cacheDirSize = SPUtil.getLong(IMAGE_DIR_SIZE, 10 * 1024 * 1024);
        //海报和头像各占一半缓存
        dirSize = cacheDirSize / 2;
        posterDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), posterDirName);
        avatarDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), avatarDirName);
        backdropDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), backdropDirName);
        initDir();
    }

    private static void initDir() {
        if (!posterDir.exists()) {
            posterDir.mkdirs();
        }
        if (!avatarDir.exists()) {
            avatarDir.mkdirs();
        }
        if (!backdropDir.exists()) {
            backdropDir.mkdirs();
        }
    }

    public static File hasCacheImage(int tmdbID, int imageType) {
        long startTime = System.currentTimeMillis();
        if (!PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.d("没有读写权限，不检查Cache");
            return null;
        }

        File[] files = null;
        initDir();
        if (TYPE_POSTER == imageType) {
            files = posterDir.listFiles();
        } else if (TYPE_AVATAR == imageType) {
            files = avatarDir.listFiles();
        } else if (TYPE_BACKDROP == imageType) {
            files = backdropDir.listFiles();
        }
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String id = fileName.substring(fileName.indexOf("-") + 1, fileName.indexOf("."));
//            Logger.d("文件名:" + file.getName() + "...TmdbID:" + tmdbID +"切割后:"+id+ "...lastModifiedTime:" + file.lastModified());
                if (id.equals(String.valueOf(tmdbID))) {
//                Logger.d("文件已存在:" + tmdbID+"...Uri:"+file.toURI().toString());
                    long endTime = System.currentTimeMillis();
                    Logger.d("获取本地图片耗时:...查询到...TMDB-ID:" + tmdbID + "..." + (endTime - startTime));
                    return file;
                }
            }
        }
//        Logger.d("文件不存在:" + tmdbID);
        long successEndTime = System.currentTimeMillis();
        Logger.d("获取本地图片耗时:...未查询到...TMDB-ID:" + tmdbID + "..." + (successEndTime - startTime));
        return null;
    }

    public static Observable<ArtImage> localArtImage(int tmdbID, File file, int type) {
        ArtImage image = new ArtImage();
        image.setTmdbID(tmdbID);
        if (TYPE_POSTER == type) {
            image.setLocalPosterUri(Uri.fromFile(file));
        } else if (TYPE_AVATAR == type) {
            image.setLocalAvatarUri(Uri.fromFile(file));
        } else {
            image.setLocalBackdropUri(Uri.fromFile(file));
        }
        return Observable.just(image);
    }

    public static Observable<TmdbPersonImage> localAvatarImage(int tmdbID, File file) {
        TmdbPersonImage tmdbPeopleImage = new TmdbPersonImage();
        tmdbPeopleImage.setHasCache(true);
        tmdbPeopleImage.setId(tmdbID);
        tmdbPeopleImage.setCacheFile(file);
        return Observable.just(tmdbPeopleImage);
    }

    public static void writeToDisk(ResponseBody body, String fileName, int imageType) {
        File dir;
        if (TYPE_POSTER == imageType) {
            dir = posterDir;
        } else if (TYPE_BACKDROP == imageType) {
            dir = backdropDir;
        } else {
            dir = avatarDir;
        }
        long nowDirSize = getDirSize(dir);
        Logger.d("图片缓存文件夹当前大小:" + nowDirSize + "...配置大小:" + dirSize + "...KB大小:" + FileUtil.bytesToKb(nowDirSize) + "...MB大小:" + FileUtil.bytesToMb(nowDirSize));
//        Logger.d("图片耗时...reducing:"+reducing+"...文件夹大小MB:"+FileUtil.bytesToMb(nowDirSize));
//        if (nowDirSize > dirSize && !reducing) {
//            //计算超出文件夹限制的size
//            long reduceSize = nowDirSize + body.contentLength() - dirSize;
//            reduceDirSize(reduceSize, dir);
//        }
        if (body != null) {
//            Logger.d("responseBody:" + body.contentLength() + "..." + fileName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            BufferedSink sink = null;
            try {
                sink = Okio.buffer(Okio.sink(file));
                sink.write(body.source(), body.contentLength());
                sink.flush();
                Logger.d("图片下载:..." + file.getName() + "...保存到..." + file.getAbsolutePath());
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
                body.close();
            }
        }
    }

    private static long getDirSize(File dir) {
        long startTime = System.currentTimeMillis();
        long size = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
//            Logger.d("图片:" + file.getName() + "...大小:" + FileUtil.bytesToKb(file.length()));
            size += file.length();
        }
        long endTime = System.currentTimeMillis();
        Logger.d("图片耗时...获取文件夹大小耗时:" + (endTime - startTime) + "...文件数:" + files.length + "...文件夹大小:" + size + "...KB:" + FileUtil.bytesToKb(size) + "...MB:" + FileUtil.bytesToMb(size));
        return size;
    }

    /**
     * 降低图片文件夹大小
     */
    private static void reduceDirSize(long reduceSize, File dir) {
        reducing = true;
        reduceSize = 1000000;
        // TODO: 2017/1/17 这里耗时严重值得优化，不应该每一张图片遍历缩减一次，而是当全部图片获取完毕后，一次性遍历缩减
        // TODO: 2017/1/19 删除过程中可能导致adapter中一些item引用的图片文件被删掉，导致滑动回去时item显示不到图片
        long startTime = System.currentTimeMillis();

        File[] files = dir.listFiles();
        //按图片最后修改时间排序
        long sortStartTime = System.currentTimeMillis();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                // TODO: 2017/1/17  java.lang.IllegalArgumentException: Comparison method violates its general contract! 可能MainActivity的内存泄漏就是这里
                Logger.d("最后修改时间比较:" + (int) (file1.lastModified() - file2.lastModified()));
//                int result = (int) (file1.lastModified() - file2.lastModified());
                return (int) (file1.lastModified() - file2.lastModified());
            }
        });
        long sortEndTime = System.currentTimeMillis();
        Logger.d("图片耗时...排序文件夹耗时:" + (sortEndTime - sortStartTime));

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
        long endTime = System.currentTimeMillis();
        Logger.d("图片耗时...缩减文件夹耗时:" + (endTime - startTime));
        reducing = false;
    }
}
