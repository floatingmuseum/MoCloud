package com.floatingmuseum.mocloud.mainmovie.Trending;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.model.entity.Image;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.entity.Trending;
import com.floatingmuseum.mocloud.model.net.MoCloudFactory;
import com.floatingmuseum.mocloud.model.net.MoCloudService;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmusuem on 2016/4/19.
 */
public class TrendingRepo {
    private MoCloudService service;
    public TrendingRepo(){
        service = MoCloudFactory.getInstance();
    }
    public Observable<List<Trending>> getData(){
        return service.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Movie> getMovie(String movieId){
        return service.getMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
