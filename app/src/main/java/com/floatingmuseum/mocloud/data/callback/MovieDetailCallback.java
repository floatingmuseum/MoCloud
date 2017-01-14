package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieDetail;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;

import java.util.List;
import java.util.Objects;

/**
 * Created by Floatingmuseum on 2016/8/15.
 */
public interface MovieDetailCallback<T> extends CommentsCallback<T>{
    void onCommentsSuccess(List<Comment> comments);
    void onTraktRatingsSuccess(Ratings ratings);
    void onImdbRatingsSuccess(OmdbInfo omdbInfo);
}
