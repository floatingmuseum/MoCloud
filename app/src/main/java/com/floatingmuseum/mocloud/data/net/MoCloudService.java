package com.floatingmuseum.mocloud.data.net;


import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.MovieImage;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbImagesConfiguration;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.data.entity.TmdbPeopleImage;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.UserSettings;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
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


    @GET("users/settings")
    Observable<UserSettings> getUserSettings();

    @GET("users/settings")
    Call<UserSettings> getUserSetting();

    @POST("oauth/token")
    Observable<TraktToken> getNewAccessToken(@Body TokenRequest tokenRequest);

    @POST("oauth/revoke")
    Observable<Response> revokeToken(@Body String accessToken);

//*******************************************电  影*******************************************
    /**
     * 电影趋势
     * Returns all movies being watched right now. Movies with the most users are returned first.
     * limit每页数据的数量
     */
    @GET("movies/trending")
    Observable<List<BaseMovie>> getMovieTrending(@Query("page") int page,@Query("limit")int limit);

    /**
     * 电影流行
     * Returns the most popular movies.Popularity is calculated using the rating percentage and the number of ratings.
     */
    @GET("movies/popular?extended=full")
    Observable<List<Movie>> getMoviePopular(@Query("page") int page,@Query("limit")int limit);

    /**
     * 电影播放最多
     * Returns the most played (a single user can watch multiple times) movies in the specified time period
     */
    @GET("movies/played/{period}?extended=full")
    Observable<List<BaseMovie>> getMoviePlayed(@Path("period") String period,@Query("page") int page,@Query("limit")int limit);

    /**
     * 电影观看最多
     * Returns the most watched (unique users) movies in the specified time period
     */
    @GET("movies/watched/{period}?extended=full")
    Observable<List<BaseMovie>> getMovieWatched(@Path("period") String period,@Query("page") int page,@Query("limit")int limit);

    /**
     * 电影被收藏最多
     * Returns the most collected (unique users) movies in the specified time period
     */
    @GET("movies/collected/{period}?extended=full")
    Observable<List<BaseMovie>> getMovieCollected(@Path("period")String period,@Query("page") int page,@Query("limit")int limit);

    /**
     * Returns the most anticipated movies based on the number of lists a movie appears on.
     */
    @GET("movies/anticipated?extended=full")
    Observable<List<BaseMovie>> getMovieAnticipated(@Query("page") int page,@Query("limit")int limit);

    /**
     * Returns the top 10 grossing movies in the U.S. box office last weekend. Updated every Monday morning.
     */
    @GET("movies/boxoffice?extended=full")
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
     * 电影团队 from Trakt
     */
    @GET("movies/{id}/people")
    Observable<People> getMovieTeam(@Path("id") String movieId);

    /**
     * 电影团队 from TMDB
     */
    @GET("https://api.themoviedb.org/3/movie/{tmdbID}/credits")
    Observable<TmdbPeople> getMovieTeam(@Path("tmdbID") int tmdbId,@Query("api_key")String tmdbApiKey);

    @GET("https://api.themoviedb.org/3/person/{tmdbID}/images")
    Observable<TmdbPeopleImage> getPeopleImage(@Path("tmdbID") int tmdbId,@Query("api_key")String tmdbApiKey);

    /**
     * 影人详情 from TMDB
     */
    @GET("https://api.themoviedb.org/3/person/{tmdbID}")
    Observable<TmdbStaff> getStaff(@Path("tmdbID") int tmdbId, @Query("api_key")String tmdbApiKey,@Query("append_to_response")String append_to_response);

    @GET("people/{id}?extended=full")
    Observable<Person> getStaff(@Path("id") String id);

    @GET("people/{id}/movies")
    Observable<PeopleCredit> getPeopleMovieCredits(@Path("id")String imdbId);

    @GET("people/{id}/shows")
    Observable getPeopleShowsCredits(@Path("id")String imdbId);

    /**
     * 评论
     */
    @GET("movies/{id}/comments/{sort}?extended=images")
    Observable<List<Comment>> getComments(@Path("id")String id, @Path("sort")String sort, @Query("limit")int limit, @Query("page")int page);

//******************************************图 片*******************************************

    @GET("")
    Observable<TmdbImagesConfiguration> getTmdbImagesConfiguration(@Query("api_key")String tmdbApiKey);

    @GET(" https://api.themoviedb.org/3/movie/{tmdb_id}/images")
    Observable<TmdbMovieImage> getTmdbImages(@Path("tmdb_id")int tmdbID, @Query("api_key")String tmdbApiKey);

    @GET("http://webservice.fanart.tv/v3/movies/{imdbID}")
    Observable<MovieImage> getMovieImages(@Path("imdbID")String imdbID, @Query("api_key")String fanartApiKey);

    @GET("http://webservice.fanart.tv/v3/movies/{tmdbID}")
    Observable<MovieImage> getMovieImages(@Path("tmdbID")int tmdbID, @Query("api_key")String fanartApiKey);

    @Streaming
    @GET
    Observable<ResponseBody> downloadImage(@Url String url);
}
