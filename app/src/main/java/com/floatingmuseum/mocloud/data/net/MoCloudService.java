package com.floatingmuseum.mocloud.data.net;


import android.content.Loader;

import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.ExpireTime;
import com.floatingmuseum.mocloud.data.entity.FeatureList;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.SyncData;
import com.floatingmuseum.mocloud.data.entity.LastActivities;
import com.floatingmuseum.mocloud.data.entity.MovieCollectionItem;
import com.floatingmuseum.mocloud.data.entity.MovieImage;
import com.floatingmuseum.mocloud.data.entity.MovieRatingItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchlistItem;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.StaffImages;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.SyncResponse;
import com.floatingmuseum.mocloud.data.entity.TmdbImagesConfiguration;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPersonImage;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.data.entity.UserCommentLike;
import com.floatingmuseum.mocloud.data.entity.UserListLike;
import com.floatingmuseum.mocloud.data.entity.UserSettings;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
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
     *
     * @param name
     * @return
     */
    @GET("people/{id}")
    Observable<Person> getPerson(@Path("id") String name);

    //*******************************************OAUTH*******************************************
    @POST("oauth/token")
    Observable<TraktToken> getToken(@Body TokenRequest tokenRequest);


    @GET("users/settings")
    Observable<UserSettings> syncUserSettings();

    @GET("users/settings")
    Call<UserSettings> getUserSetting();

    @POST("oauth/token")
    Observable<TraktToken> getNewAccessToken(@Body TokenRequest tokenRequest);

    @POST("oauth/revoke")
    Observable<Response> revokeToken(@Body String accessToken);

    //*******************************************用 户*******************************************
    @GET("users/{id}?extended=full")
    Observable<User> getUserProfile(@Path("id") String slug);

    @GET("users/{id}/followers?extended=full")
    Observable<List<Follower>> getUserFollowers(@Path("id") String slug);

    @GET("users/{id}/following?extended=full")
    Observable<List<Follower>> getUserFollowing(@Path("id") String slug);

    //用户统计数据
    @GET("users/{id}/stats")
    Observable<Stats> getUserStats(@Path("id") String slug);

    //******************************************* Sync *******************************************

    //用户最新变动
    @GET("sync/last_activities")
    Observable<LastActivities> getLastActivities();

    @GET("sync/watched/movies")
    Observable<List<MovieWatchedItem>> syncMovieWatched();

    @GET("sync/watchlist/movies")
    Observable<List<MovieWatchlistItem>> syncMovieWatchlist();

    @GET("sync/ratings/movies")
    Observable<List<MovieRatingItem>> syncMovieRatings();

    @GET("sync/collection/movies")
    Observable<List<MovieCollectionItem>> syncMoviesCollections();

    @GET("users/likes/comments")
    Observable<List<UserCommentLike>> syncUserCommentsLikes();

    @GET("users/likes/lists")
    Observable<List<UserListLike>> syncUserListsLikes();

    @POST("sync/history")
    Observable<SyncResponse> addMovieToWatched(@Body SyncData syncData);

    @POST("sync/history/remove")
    Observable<SyncResponse> removeMovieFromWatched(@Body SyncData syncData);

    @POST("sync/watchlist")
    Observable<SyncResponse> addMovieToWatchlist(@Body SyncData syncData);

    @POST("sync/watchlist/remove")
    Observable<SyncResponse> removeMovieFromWatchlist(@Body SyncData syncData);

    @POST("sync/collection")
    Observable<SyncResponse> addMovieToCollection(@Body SyncData syncData);

    @POST("sync/collection/remove")
    Observable<SyncResponse> removeMovieFromCollection(@Body SyncData syncData);

    @POST("comments/{id}/like")
    Observable<ResponseBody> addCommentToLikes(@Path("id") long commentId);

    @DELETE("comments/{id}/like")
    Observable<ResponseBody> removeCommentFromLikes(@Path("id") long commentId);

    @GET("users/{id}/following")
    Observable<List<Follower>> syncUserFollowing(@Path("id") String slug);

    @GET("users/{id}/followers")
    Observable<List<Follower>> syncUserFollowers(@Path("id") String slug);

    @POST("users/{id}/follow")
    Observable followUser(@Path("id") String slug);

    @DELETE("users/{id}/follow")
    Observable unfollowUser(@Path("id") String slug);

//*******************************************电  影*******************************************

    /**
     * 电影趋势
     * Returns all movies being watched right now. Movies with the most users are returned first.
     * limit每页数据的数量
     */
    @GET("movies/trending?extended=full")
    Observable<List<BaseMovie>> getMovieTrending(@Query("page") int page, @Query("limit") int limit);

    /**
     * 电影流行
     * Returns the most popular movies.Popularity is calculated using the rating percentage and the number of ratings.
     */
    @GET("movies/popular?extended=full")
    Observable<List<Movie>> getMoviePopular(@Query("page") int page, @Query("limit") int limit);

    /**
     * 电影播放最多
     * Returns the most played (a single user can watch multiple times) movies in the specified time period
     */
    @GET("movies/played/{period}?extended=full")
    Observable<List<BaseMovie>> getMoviePlayed(@Path("period") String period, @Query("page") int page, @Query("limit") int limit);

    /**
     * 电影观看最多
     * Returns the most watched (unique users) movies in the specified time period
     */
    @GET("movies/watched/{period}?extended=full")
    Observable<List<BaseMovie>> getMovieWatched(@Path("period") String period, @Query("page") int page, @Query("limit") int limit);

    /**
     * 电影被收藏最多
     * Returns the most collected (unique users) movies in the specified time period
     */
    @GET("movies/collected/{period}?extended=full")
    Observable<List<BaseMovie>> getMovieCollected(@Path("period") String period, @Query("page") int page, @Query("limit") int limit);

    /**
     * Returns the most anticipated movies based on the number of lists a movie appears on.
     */
    @GET("movies/anticipated?extended=full")
    Observable<List<BaseMovie>> getMovieAnticipated(@Query("page") int page, @Query("limit") int limit);

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
    @GET("movies/{id}")
    Observable<Movie> getMovieDetail(@Path("id") String movieId);

    /**
     * 电影详情 from Tmdb
     */
//    @GET("https://api.themoviedb.org/3/movie/{id}")
//    Observable<TmdbMovieDetail> getMovieDetail(@Path("id") int tmdbId,@Query("api_key")String tmdbApiKey,@Query("append_to_response")String appendToResponse);

    /**
     * Trakt评分
     */
//    @GET("movies/{id}/ratings")
//    Observable<Ratings> getMovieTraktRatings(@Path("id")String imdbId);

    /**
     * Imdb评分
     * tomato评分
     */
    @GET("http://www.omdbapi.com/")
    Observable<OmdbInfo> getMovieOtherRatings(@Query("i") String imdbId, @Query("tomatoes") String b);

    /**
     * 电影Popular from Tmdb
     */
    @GET("https://api.themoviedb.org/3/movie/popular")
    Observable<TmdbMovieDataList> getMoviePopular(@Query("page") int page, @Query("api_key") String tmdbApiKey);

    /**
     * 电影NowPlaying from Tmdb
     */
    @GET("https://api.themoviedb.org/3/movie/now_playing")
    Observable<TmdbMovieDataList> getMovieNowPlaying(@Query("page") int page, @Query("api_key") String tmdbApiKey);

    /**
     * 电影TopRated from Tmdb
     */
    @GET("https://api.themoviedb.org/3/movie/top_rated")
    Observable<TmdbMovieDataList> getMovieTopRated(@Query("page") int page, @Query("api_key") String tmdbApiKey);

    /**
     * 电影Upcoming from Tmdb
     */
    @GET("https://api.themoviedb.org/3/movie/upcoming")
    Observable<TmdbMovieDataList> getMovieUpcoming(@Query("page") int page, @Query("api_key") String tmdbApiKey);

    /**
     * 电影团队 from Trakt
     */
    @GET("movies/{id}/people?extended=full")
    Observable<PeopleCredit> getMovieTeam(@Path("id") String movieId);

    /**
     * 电影团队 from TMDB
     */
//    @GET("https://api.themoviedb.org/3/movie/{tmdbID}/credits")
//    Observable<TmdbPeople> getMovieTeam(@Path("tmdbID") int tmdbId,@Query("api_key")String tmdbApiKey);
    @GET("https://api.themoviedb.org/3/person/{tmdbID}/images")
    Observable<TmdbPersonImage> getPersonImagesFromTmdb(@Path("tmdbID") int tmdbId, @Query("api_key") String tmdbApiKey);

    @GET("recommendations/movies?extended=full")
    Observable<List<Movie>> getRecommendations();

    @GET("users/{userID}/lists/{listID}")
    Observable<FeatureList> getFeatureList(@Path("userID") String userID, @Path("listID") String listID);

    @DELETE("recommendations/movies/{id}")
    Observable<ResponseBody> hideMovie(@Path("id") String slug);

    /**
     * 影人详情 from TMDB
     */
//    @GET("https://api.themoviedb.org/3/person/{tmdbID}")
//    Observable<TmdbStaff> getStaff(@Path("tmdbID") int tmdbId, @Query("api_key")String tmdbApiKey);
    @GET("people/{id}?extended=full")
    Observable<Person> getStaff(@Path("id") String id);

    @GET("people/{id}/movies?extended=full")
    Observable<PeopleCredit> getStaffMovieCredits(@Path("id") int traktId);

    @GET("people/{id}/shows?extended=full")
    Observable getStaffShowsCredits(@Path("id") String traktId);

//    @GET("https://api.themoviedb.org/3/person/{tmdbID}/movie_credits")
//    Observable<TmdbStaffMovieCredits> getStaffMovieCredits(@Path("tmdbID") int tmdbId, @Query("api_key")String tmdbApiKey);

    @GET("https://api.themoviedb.org/3/person/{tmdbID}/images")
    Observable<StaffImages> getStaffImages(@Path("tmdbID") int tmdbId, @Query("api_key") String tmdbApiKey);

//******************************************评 论*******************************************

    /**
     * 获取电影评论
     */
    @GET("movies/{id}/comments/{sort}?extended=full,images")
    Observable<List<Comment>> getComments(@Path("id") String id, @Path("sort") String sort, @Query("limit") int limit, @Query("page") int page);

    //评论电影 OAuth Required
    @POST("comments?extended=full,images")
    Observable<Comment> sendComment(@Body Comment comment);

    //点赞评论 OAuth Required 204状态码成功
    @POST("comments/{id}/like")
    Observable sendLikeComment(@Path("id") long id);

    //获取单个评论下的评论
    @GET("comments/{id}/replies?extended=full,images")
    Observable<List<Comment>> getCommentReplies(@Path("id") long id);

    //回复某个评论 OAuth Required
    @POST("comments/{id}/replies?extended=full,images")
    Observable<Comment> sendReply(@Path("id") long id, @Body Reply reply);


//******************************************图 片*******************************************

    @GET("")
    Observable<TmdbImagesConfiguration> getTmdbImagesConfiguration(@Query("api_key") String tmdbApiKey);

    @GET("https://api.themoviedb.org/3/movie/{tmdb_id}/images")
    Observable<TmdbMovieImage> getTmdbImages(@Path("tmdb_id") int tmdbID, @Query("api_key") String tmdbApiKey);

    @GET("http://webservice.fanart.tv/v3/movies/{imdbID}")
    Observable<MovieImage> getMovieImages(@Path("imdbID") String imdbID, @Query("api_key") String fanartApiKey);

    @GET("http://webservice.fanart.tv/v3/movies/{tmdbID}")
    Observable<MovieImage> getMovieImages(@Path("tmdbID") int tmdbID, @Query("api_key") String fanartApiKey);

    @Streaming
    @GET
    Observable<ResponseBody> downloadImage(@Url String url);

}
