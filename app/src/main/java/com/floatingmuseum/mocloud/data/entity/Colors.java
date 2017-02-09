package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2017/2/9.
 */

public class Colors implements Parcelable {
    private int rgb;
    private int titleTextColor;
    private int bodyTextColor;
    private int population;
    private float[] hsl;

    public int getRgb() {
        return rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getBodyTextColor() {
        return bodyTextColor;
    }

    public void setBodyTextColor(int bodyTextColor) {
        this.bodyTextColor = bodyTextColor;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public float[] getHsl() {
        return hsl;
    }

    public void setHsl(float[] hsl) {
        this.hsl = hsl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rgb);
        dest.writeInt(this.titleTextColor);
        dest.writeInt(this.bodyTextColor);
        dest.writeInt(this.population);
        dest.writeFloatArray(this.hsl);
    }

    public Colors() {
    }

    protected Colors(Parcel in) {
        this.rgb = in.readInt();
        this.titleTextColor = in.readInt();
        this.bodyTextColor = in.readInt();
        this.population = in.readInt();
        this.hsl = in.createFloatArray();
    }

    public static final Creator<Colors> CREATOR = new Creator<Colors>() {
        @Override
        public Colors createFromParcel(Parcel source) {
            return new Colors(source);
        }

        @Override
        public Colors[] newArray(int size) {
            return new Colors[size];
        }
    };
}
