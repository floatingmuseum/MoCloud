package com.floatingmuseum.mocloud.utils;

import android.graphics.Color;
import android.support.v7.graphics.Palette;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by Floatingmuseum on 2017/2/8.
 */

public class ColorUtil {

    /**
     * @param color RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *              Android中我们一般使用它的16进制，
     *              例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *              red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *              所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @param level level必须处于0.1~0.9之间
     * @return
     */
    public static int darkerColor(int color, double level) {
        int alpha = color >> 24;
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;
        Logger.d("颜色ARGB...alpha:" + alpha + "...red:" + red + "...green:" + green + "...blue:" + blue);
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

    public static ArrayList<Palette.Swatch> buildSwatchs(Palette palette) {
        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
        Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
        Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();

        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
        Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
//        Logger.d("PaletteTest...rgb:" + lightMutedSwatch.getRgb() + "...population:" + lightMutedSwatch.getPopulation());

        ArrayList<Palette.Swatch> swatches = new ArrayList<>();

        if (checkSwatch(mutedSwatch, darkMutedSwatch)) {
            Logger.d("PaletteTest...方案1");
            swatches.add(mutedSwatch);
            swatches.add(darkMutedSwatch);
            return swatches;
        } else if (checkSwatch(mutedSwatch, lightMutedSwatch)) {
            Logger.d("PaletteTest...方案2");
            swatches.add(mutedSwatch);
            swatches.add(lightMutedSwatch);
            return swatches;
        } else if (checkSwatch(vibrantSwatch, darkVibrantSwatch)) {
            Logger.d("PaletteTest...方案3");
            swatches.add(vibrantSwatch);
            swatches.add(darkVibrantSwatch);
            return swatches;
        } else if (checkSwatch(vibrantSwatch, lightVibrantSwatch)) {
            Logger.d("PaletteTest...方案4");
            swatches.add(vibrantSwatch);
            swatches.add(lightVibrantSwatch);
            return swatches;
        } else if (checkSwatch(darkMutedSwatch, darkVibrantSwatch)) {
            Logger.d("PaletteTest...方案5");
            swatches.add(darkMutedSwatch);
            swatches.add(darkVibrantSwatch);
            return swatches;
        } else if (checkSwatch(lightMutedSwatch, lightVibrantSwatch)) {
            Logger.d("PaletteTest...方案6");
            swatches.add(lightMutedSwatch);
            swatches.add(lightVibrantSwatch);
            return swatches;
        } else if (checkSwatch(lightMutedSwatch, darkMutedSwatch)) {
            Logger.d("PaletteTest...方案7");
            swatches.add(lightMutedSwatch);
            swatches.add(darkMutedSwatch);
            return swatches;
        } else if (checkSwatch(lightVibrantSwatch, darkVibrantSwatch)) {
            Logger.d("PaletteTest...方案8");
            swatches.add(lightVibrantSwatch);
            swatches.add(darkVibrantSwatch);
            return swatches;
        } else if (checkSwatch(mutedSwatch, darkVibrantSwatch)) {
            Logger.d("PaletteTest...方案9");
            swatches.add(mutedSwatch);
            swatches.add(darkVibrantSwatch);
            return swatches;
        } else if (checkSwatch(mutedSwatch, lightVibrantSwatch)) {
            Logger.d("PaletteTest...方案10");
            swatches.add(mutedSwatch);
            swatches.add(lightVibrantSwatch);
            return swatches;
        } else if (checkSwatch(vibrantSwatch, lightMutedSwatch)) {
            Logger.d("PaletteTest...方案11");
            swatches.add(vibrantSwatch);
            swatches.add(lightMutedSwatch);
            return swatches;
        } else if (checkSwatch(vibrantSwatch, darkMutedSwatch)) {
            Logger.d("PaletteTest...方案12");
            swatches.add(vibrantSwatch);
            swatches.add(darkMutedSwatch);
            return swatches;
        } else {
            Logger.d("PaletteTest...方案13");
            return swatches;
        }
    }

    private static boolean checkSwatch(Palette.Swatch first, Palette.Swatch second) {
        return first != null && second != null;
    }
}
