package com.floatingmuseum.mocloud.utils;

import android.graphics.Color;

/**
 * Created by Floatingmuseum on 2017/2/8.
 */

public class ColorUtil {

    /**
     * @param color
     * @param level
     * level必须处于0.1~0.9之间
     * @return
     */
    public static int darkerColor(int color, double level) {
        int alpha = color >> 24;
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;
        red = (int) Math.floor(red * (1 - level));
        green = (int) Math.floor(green * (1 - level));
        blue = (int) Math.floor(blue * (1 - level));
        return Color.rgb(red, green, blue);
    }

//    public static int brighterColor(int color, double level) {
//        int alpha = color << 24;
//        int red = color << 16 & 0xFF;
//        int green = color << 8 & 0xFF;
//        int blue = color & 0xFF;
//        red = (int) Math.floor(red * (1 - level));
//        green = (int) Math.floor(green * (1 - level));
//        blue = (int) Math.floor(blue * (1 - level));
//        return Color.rgb(red, green, blue);
//    }
}
