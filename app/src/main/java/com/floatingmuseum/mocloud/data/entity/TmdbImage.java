package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by Floatingmuseum on 2016/12/19.
 */

public class TmdbImage implements Parcelable {
    private int id;
    private boolean hasCache;
    private boolean hasPoster;
    private boolean hasAvatar;
    private File cacheFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasCache() {
        return hasCache;
    }

    public void setHasCache(boolean hasCache) {
        this.hasCache = hasCache;
    }

    public File getCacheFile() {
        return cacheFile;
    }

    public boolean isHasAvatar() {
        return hasAvatar;
    }

    public void setHasAvatar(boolean hasAvatar) {
        this.hasAvatar = hasAvatar;
    }

    public boolean isHasPoster() {
        return hasPoster;
    }

    public void setHasPoster(boolean hasPoster) {
        this.hasPoster = hasPoster;
    }

    public void setCacheFile(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    public TmdbImage() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte(this.hasCache ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasPoster ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasAvatar ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.cacheFile);
    }

    protected TmdbImage(Parcel in) {
        this.id = in.readInt();
        this.hasCache = in.readByte() != 0;
        this.hasPoster = in.readByte() != 0;
        this.hasAvatar = in.readByte() != 0;
        this.cacheFile = (File) in.readSerializable();
    }

}
