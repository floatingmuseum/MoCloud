package com.floatingmuseum.mocloud.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by Floatingmuseum on 2016/8/4.
 */
public class NumberFormatUtil {

    /**
     *
     * @param num
     *      被格式化的数字
     * @param roundingUp
     *       是否五入
     * @param maximumFraction
     *       保留几位
     * @return
     */
    public static String doubleFormatToString(double num,boolean roundingUp,int maximumFraction){
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(maximumFraction);

        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        if(roundingUp){
            nf.setRoundingMode(RoundingMode.UP);
        }else{
            nf.setRoundingMode(RoundingMode.DOWN);
        }

        return nf.format(num);
    }

    /**
     * 似乎无效啊
     *
     * @param num
     *      被格式化的数字
     * @param roundingUp
     *       是否五入
     * @param maximumFraction
     *       保留几位
     * @return
     */
    @Deprecated
    public static double doubleFormat(double num,boolean roundingUp,int maximumFraction){
        BigDecimal bg = new BigDecimal(num);
        if(roundingUp){
            bg.setScale(maximumFraction,BigDecimal.ROUND_HALF_UP);
        }else{
            bg.setScale(maximumFraction,BigDecimal.ROUND_HALF_DOWN);
        }
        return bg.doubleValue();
    }
}
