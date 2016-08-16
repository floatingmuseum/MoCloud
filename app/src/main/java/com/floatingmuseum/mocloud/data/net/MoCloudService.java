package com.floatingmuseum.mocloud.data.net;


import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public interface MoCloudService {
    /**
     * Person
     * @param name
     * @return
     */
    @GET("people/{id}")
    Observable<Person> getPerson(@Path("id") String name);

//*******************************************OAUTH*******************************************
    @POST("oauth/token")
    Observable<TraktToken> getToken(@Body TokenRequest tokenRequest);

//*******************************************电  影*******************************************
    /**
     * 电影趋势
     * Returns all movies being watched right now. Movies with the most users are returned first.
     * limit每页数据的数量
     */
    @GET("movies/trending?limit=12;extended=images")
    Observable<List<BaseMovie>> getMovieTrending(@Query("page") int page);

    /**
     * 电影流行
     * Returns the most popular movies.Popularity is calculated using the rating percentage and the number of ratings.
     */
    @GET("movies/popular?limit=12;extended=images")
    Observable<List<Movie>> getMoviePopular(@Query("page") int page);

    /**
     * 电影播放最多
     * Returns the most played (a single user can watch multiple times) movies in the specified time period
     */
    @GET("movies/played/{period}?limit=12;extended=images")
    Observable<List<BaseMovie>> getMoviePlayed(@Path("period") String period,@Query("page") int page);

    /**
     * 电影观看最多
     * Returns the most watched (unique users) movies in the specified time period
     */
    @GET("movies/watched/{period}?limit=12;extended=images")
    Observable<List<BaseMovie>> getMovieWatched(@Path("period") String period,@Query("page") int page);

    /**
     * 电影被收藏最多
     * Returns the most collected (unique users) movies in the specified time period
     */
    @GET("movies/collected/{period}?limit=12;extended=images")
    Observable<List<BaseMovie>> getMovieCollected(@Path("period")String period,@Query("page") int page);

    /**
     * Returns the most anticipated movies based on the number of lists a movie appears on.
     */
    @GET("movies/anticipated?limit=12;extended=images")
    Observable<List<BaseMovie>> getMovieAnticipated(@Query("page") int page);

    /**
     * Returns the top 10 grossing movies in the U.S. box office last weekend. Updated every Monday morning.
     */
    @GET("movies/boxoffice?extended=images")
    Observable<List<BaseMovie>> getMovieBoxOffice();

    /**
     * 电影海报
     */
    @GET("movies/{id}?extended=images")
    Observable<Movie> getMovieImage(@Path("id") String movieId);

    /**
     * 电影详情
     */
    @GET("movies/{id}?extended=full,images")
    Observable<Movie> getMovieDetail(@Path("id") String movieId);

    /**
     * 电影团队
     */
    @GET("movies/{id}/people?extended=images")
    Observable<People> getMoviePeople(@Path("id") String movieId);

    /**
     * 评论
     */
    @GET("movies/{id}/comments/likes")
    Observable<List<Comment>> getComments(@Path("id")String id);
}
