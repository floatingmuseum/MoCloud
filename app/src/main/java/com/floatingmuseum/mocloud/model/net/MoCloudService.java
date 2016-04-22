package com.floatingmuseum.mocloud.model.net;


import com.floatingmuseum.mocloud.model.entity.Actor;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.entity.Trending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface MoCloudService {
    /**
     * Actor 演员
     * @param name
     * @return
     */
    @GET("people/{id}")
    Observable<Actor> getActor(@Path("id") String name);

    /**
     * Trending 趋势
     * @return
     */
    @GET("movies/trending")
    Observable<List<Trending>> getTrending();

    @GET("movies/{id}?extended=images")
    Observable<Movie> getMovie(@Path("id") String movieId);
}
