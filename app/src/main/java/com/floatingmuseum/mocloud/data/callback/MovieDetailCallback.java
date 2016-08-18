package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieDetail;
import com.floatingmuseum.mocloud.data.entity.People;

import java.util.List;
import java.util.Objects;

/**
 * Created by yan on 2016/8/15.
 */
public interface MovieDetailCallback{
    void onSuccess(Movie movie);
    void onPeopleSuccess(People people);
    void onCommentsSuccess(List<Comment> comments);
    void onError(Throwable throwable);
}
