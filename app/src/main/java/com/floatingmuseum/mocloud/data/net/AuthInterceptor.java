package com.floatingmuseum.mocloud.data.net;

import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Floatingmuseum on 2016/12/15.
 */

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken = SPUtil.getAccessToken();
        if (accessToken != null && accessToken.length() > 0) {
//            Logger.d("AccessToken:"+accessToken);
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            return chain.proceed(request);
        } else {
            return chain.proceed(chain.request());
        }
    }
}

