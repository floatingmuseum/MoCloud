package com.floatingmuseum.mocloud.model.net;

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
                .addHeader("trakt-api-key","6fa4d5c19efedfd51545deb2075831e7b467deb3e0cfd6d7b66c6f907a229ff1");

        return chain.proceed(builder.build());
    }
}
