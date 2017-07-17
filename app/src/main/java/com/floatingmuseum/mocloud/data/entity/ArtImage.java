package com.floatingmuseum.mocloud.data.entity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2017/7/10.
 */

public class ArtImage implements Parcelable {

    private int tmdbID;
    private Uri localPosterUri;
    private Uri localBackdropUri;
    private Uri localAvatarUri;
    private String remotePosterUrl;
    private String remoteBackdropUrl;
    private String remoteAvatarUrl;
    private Bitmap bitmap;

    public int getTmdbID() {
        return tmdbID;
    }

    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    public Uri getLocalPosterUri() {
        return localPosterUri;
    }

    public void setLocalPosterUri(Uri localPosterUri) {
        this.localPosterUri = localPosterUri;
    }

    public Uri getLocalBackdropUri() {
        return localBackdropUri;
    }

    public void setLocalBackdropUri(Uri localBackdropUri) {
        this.localBackdropUri = localBackdropUri;
    }

    public String getRemotePosterUrl() {
        return remotePosterUrl;
    }

    public void setRemotePosterUrl(String remotePosterUrl) {
        this.remotePosterUrl = remotePosterUrl;
    }

    public String getRemoteBackdropUrl() {
        return remoteBackdropUrl;
    }

    public void setRemoteBackdropUrl(String remoteBackdropUrl) {
        this.remoteBackdropUrl = remoteBackdropUrl;
    }

    public Uri getLocalAvatarUri() {
        return localAvatarUri;
    }

    public void setLocalAvatarUri(Uri localAvatarUri) {
        this.localAvatarUri = localAvatarUri;
    }

    public String getRemoteAvatarUrl() {
        return remoteAvatarUrl;
    }

    public void setRemoteAvatarUrl(String remoteAvatarUrl) {
        this.remoteAvatarUrl = remoteAvatarUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "ArtImage{" +
                "tmdbID=" + tmdbID +
                ", localPosterUri=" + localPosterUri +
                ", localBackdropUri=" + localBackdropUri +
                ", localAvatarUri=" + localAvatarUri +
                ", remotePosterUrl='" + remotePosterUrl + '\'' +
                ", remoteBackdropUrl='" + remoteBackdropUrl + '\'' +
                ", remoteAvatarUrl='" + remoteAvatarUrl + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tmdbID);
        dest.writeParcelable(this.localPosterUri, flags);
        dest.writeParcelable(this.localBackdropUri, flags);
        dest.writeParcelable(this.localAvatarUri, flags);
        dest.writeString(this.remotePosterUrl);
        dest.writeString(this.remoteBackdropUrl);
        dest.writeString(this.remoteAvatarUrl);
        dest.writeParcelable(this.bitmap, flags);
    }

    public ArtImage() {
    }

    protected ArtImage(Parcel in) {
        this.tmdbID = in.readInt();
        this.localPosterUri = in.readParcelable(Uri.class.getClassLoader());
        this.localBackdropUri = in.readParcelable(Uri.class.getClassLoader());
        this.localAvatarUri = in.readParcelable(Uri.class.getClassLoader());
        this.remotePosterUrl = in.readString();
        this.remoteBackdropUrl = in.readString();
        this.remoteAvatarUrl = in.readString();
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
