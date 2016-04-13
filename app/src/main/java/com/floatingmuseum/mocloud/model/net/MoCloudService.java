package com.floatingmuseum.mocloud.model.net;


import com.floatingmuseum.mocloud.model.entity.Actor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface MoCloudService {
    @GET("people/{id}")
    Observable<Actor> getActor(@Path("id") String name);

    @GET("people/{id}")
    Call<Actor> getActorToo(@Path("id")String name);
}
