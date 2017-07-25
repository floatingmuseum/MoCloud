package com.floatingmuseum.mocloud.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.floatingmuseum.mocloud.MoCloud;
import com.orhanobut.logger.Logger;

import java.util.concurrent.ExecutionException;

/**
 * Created by Floatingmuseum on 2017/7/25.
 */

public class PaletteManager {
    private static PaletteManager paletteManager;
    private SparseArray<Bitmap> bitmaps = new SparseArray<>();

    private PaletteManager() {
    }

    public static PaletteManager getInstance() {
        if (paletteManager == null) {
            synchronized (PaletteManager.class) {
                if (paletteManager == null) {
                    paletteManager = new PaletteManager();
                }
            }
        }
        return paletteManager;
    }

    public void createBitmap(int id, Uri uri) {
        try {
            int index = bitmaps.indexOfKey(id);
            if (0 > index) {
                long startTime = System.currentTimeMillis();
                Bitmap bitmap = Glide.with(MoCloud.context).load(uri).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                Logger.d("获取Bitmap耗时:" + (System.currentTimeMillis() - startTime));
                bitmaps.put(id, bitmap);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(int id) {
        return bitmaps.get(id);
    }
}
