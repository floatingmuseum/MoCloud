package com.floatingmuseum.mocloud.data.net;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MoCloudFactory {
    private static MoCloudService service = new MoCloudRetrofit().getService();
    public static MoCloudService getInstance(){
        return service;
    }
}
