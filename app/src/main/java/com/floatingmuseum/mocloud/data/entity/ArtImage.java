package com.floatingmuseum.mocloud.data.entity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2017/7/10.
 */

public class ArtImage implements Parcelable{

    private int tmdbID;
    private Uri localImageUri;
    private String remoteImageUrl;
    private Bitmap bitmap;

    public int getTmdbID() {
        return tmdbID;
    }

    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    public Uri getLocalImageUri() {
        return localImageUri;
    }

    public void setLocalImageUri(Uri localImageUri) {
        this.localImageUri = localImageUri;
    }

    public String getRemoteImageUrl() {
        return remoteImageUrl;
    }

    public void setRemoteImageUrl(String remoteImageUrl) {
        this.remoteImageUrl = remoteImageUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tmdbID);
        dest.writeParcelable(this.localImageUri, flags);
        dest.writeString(this.remoteImageUrl);
        dest.writeParcelable(this.bitmap, flags);
    }

    public ArtImage() {
    }

    protected ArtImage(Parcel in) {
        this.tmdbID = in.readInt();
        this.localImageUri = in.readParcelable(Uri.class.getClassLoader());
        this.remoteImageUrl = in.readString();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<ArtImage> CREATOR = new Creator<ArtImage>() {
        @Override
        public ArtImage createFromParcel(Parcel source) {
            return new ArtImage(source);
        }

        @Override
        public ArtImage[] newArray(int size) {
            return new ArtImage[size];
        }
    };
}
