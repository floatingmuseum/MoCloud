package com.floatingmuseum.mocloud.utils;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/2/17.
 */

public class ListUtil {

    /**
     * 集合是否含有数据
     * @param list
     * @return
     */
    public static boolean hasData(List<?> list){
        if (list!=null&&list.size()>0) {
            return true;
        }else{
            return false;
        }
    }
}
