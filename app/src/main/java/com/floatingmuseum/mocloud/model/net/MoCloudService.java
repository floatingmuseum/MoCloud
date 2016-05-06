package com.floatingmuseum.mocloud.model.net;


import com.floatingmuseum.mocloud.model.entity.Actor;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public interface MoCloudService {
    /**
     * Actor 演员
     * @param name
     * @return
     */
    @GET("people/{id}")
    Observable<Actor> getActor(@Path("id") String name);


//*******************************************电  影*******************************************
    /**
     * 电影趋势
     * Returns all movies being watched right now. Movies with the most users are returned first.
     * limit每页数据的数量
     */
    @GET("movies/trending?limit=10")
    Observable<List<BaseMovie>> getMovieTrending(@Query("page") int page);

    /**
     * 电影流行
     * Returns the most popular movies.Popularity is calculated using the rating percentage and the number of ratings.
     */
    @GET("movies/popular?limit=10")
    Observable<List<Movie>> getMoviePopular(@Query("page") int page);

    /**
     * 电影播放最多
     * Returns the most played (a single user can watch multiple times) movies in the specified time period
     */
    @GET("movies/played/{period}?limit=10")
    Observable<List<BaseMovie>> getMoviePlayed(@Path("period") String period,@Query("page") int page);

    /**
     * 电影观看最多
     * Returns the most watched (unique users) movies in the specified time period
     */
    @GET("movies/watched/{period}?limit=10")
    Observable<List<BaseMovie>> getMovieWatched(@Path("period") String period,@Query("page") int page);

    /**
     * 电影被收藏最多
     * Returns the most collected (unique users) movies in the specified time period
     */
    @GET("movies/collected/{period}?limit=10")
    Observable<List<BaseMovie>> getMovieCollocted(@Path("period")String period,@Query("page") int page);

    /**
     * Returns the most anticipated movies based on the number of lists a movie appears on.
     */
    @GET("movies/anticipated?limit=10")
    Observable<List<BaseMovie>> getMovieAnticipated(@Query("page") int page);

    /**
     * Returns the top 10 grossing movies in the U.S. box office last weekend. Updated every Monday morning.
     */
    @GET("movies/boxoffice")
    Observable<List<BaseMovie>> getMovieBoxOffice();

    /**
     * 电影海报
     */
    @GET("movies/{id}?extended=images")
    Observable<Movie> getMovieImage(@Path("id") String movieId);
}
