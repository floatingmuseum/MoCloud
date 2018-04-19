package com.floatingmuseum.mocloud.utils;

import com.floatingmuseum.mocloud.Constants;

import retrofit2.HttpException;

/**
 * Created by Floatingmuseum on 2016/12/15.
 */

public class ErrorUtil {

    public static boolean is401Error(Throwable throwable){
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.code() == Constants.STATUS_CODE_UNAUTHORISED) {
                return true;
            }
        }
        return false;
    }
}
