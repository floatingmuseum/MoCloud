package com.floatingmuseum.mocloud.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MoCloudRetrofit {
    final MoCloudService service;
    /**
     * 使用Realm数据库后需要添加setExclusionStrategies方法。
     */
    final static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();

    MoCloudRetrofit(){
        /**
         * OkHttp 在Retrofit 1.9里是可选的。如果你想让Retrofit 使用OkHttp 作为HTTP 连接接口，你需要手动包含okhttp 依赖。
         * 但是在Retrofit 2.0中，OkHttp 是必须的，并且自动设置为了依赖。
         */
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new HeaderIntercept())
                .addInterceptor(new AuthInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                //baseUrl方法指定了请求地址的前半部分，即服务器地址
                .baseUrl("https://api.trakt.tv/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(MoCloudService.class);
    }

    public MoCloudService getService(){
        return  service;
    }
}
