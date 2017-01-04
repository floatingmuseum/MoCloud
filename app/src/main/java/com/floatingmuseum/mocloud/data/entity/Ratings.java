package com.floatingmuseum.mocloud.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Floatingmuseum on 2017/1/4.
 */

public class Ratings {

    private double rating;
    private int votes;

    private Distribution distribution;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    public static class Distribution {
        @SerializedName("1")
        private int value1;
        @SerializedName("2")
        private int value2;
        @SerializedName("3")
        private int value3;
        @SerializedName("4")
        private int value4;
        @SerializedName("5")
        private int value5;
        @SerializedName("6")
        private int value6;
        @SerializedName("7")
        private int value7;
        @SerializedName("8")
        private int value8;
        @SerializedName("9")
        private int value9;
        @SerializedName("10")
        private int value10;

        public int getValue1() {
            return value1;
        }

        public void setValue1(int value1) {
            this.value1 = value1;
        }

        public int getValue2() {
            return value2;
        }

        public void setValue2(int value2) {
            this.value2 = value2;
        }

        public int getValue3() {
            return value3;
        }

        public void setValue3(int value3) {
            this.value3 = value3;
        }

        public int getValue4() {
            return value4;
        }

        public void setValue4(int value4) {
            this.value4 = value4;
        }

        public int getValue5() {
            return value5;
        }

        public void setValue5(int value5) {
            this.value5 = value5;
        }

        public int getValue6() {
            return value6;
        }

        public void setValue6(int value6) {
            this.value6 = value6;
        }

        public int getValue7() {
            return value7;
        }

        public void setValue7(int value7) {
            this.value7 = value7;
        }

        public int getValue8() {
            return value8;
        }

        public void setValue8(int value8) {
            this.value8 = value8;
        }

        public int getValue9() {
            return value9;
        }

        public void setValue9(int value9) {
            this.value9 = value9;
        }

        public int getValue10() {
            return value10;
        }

        public void setValue10(int value10) {
            this.value10 = value10;
        }
    }
}
