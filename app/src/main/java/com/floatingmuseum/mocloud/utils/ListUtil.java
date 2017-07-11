package com.floatingmuseum.mocloud.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2017/2/17.
 */

public class ListUtil {

    /**
     * 集合是否含有数据
     *
     * @param list
     * @return
     */
    public static boolean hasData(List<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当新一页没有数据时返回null
     */
    public static <T> List<T> subList(List<? extends T> list, int page, int limit) {
        int fromIndex = (page - 1) * limit;
        int toIndex = page * limit;

        if (fromIndex >= list.size()) {
            return null;
        } else if (list.size() - fromIndex == 1) {
            List<T> oneElementList = new ArrayList<>();
            oneElementList.add(list.get(list.size() - 1));
            return oneElementList;
        } else if (toIndex > list.size() - 1) {
            toIndex = list.size() - 1;
        }
        return (List<T>) list.subList(fromIndex, toIndex);
    }
}
