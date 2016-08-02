package com.floatingmuseum.mocloud.data.net;

import com.floatingmuseum.mocloud.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class HeaderIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type","application/json")
                .addHeader("trakt-api-version","2")
                .addHeader("trakt-api-key", BuildConfig.TraktID);

        return chain.proceed(builder.build());
    }
}
