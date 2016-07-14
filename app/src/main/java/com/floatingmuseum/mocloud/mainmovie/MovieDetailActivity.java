package com.floatingmuseum.mocloud.mainmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.date.entity.Movie;
import com.floatingmuseum.mocloud.date.net.MoCloudFactory;
import com.orhanobut.logger.Logger;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends AppCompatActivity {
    public static final String MOVIE_ID = "MOVIE_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);

        String movieID = getIntent().getStringExtra(MOVIE_ID);
        Logger.d("MOVIE_ID："+movieID);

        MoCloudFactory.getInstance().getMovieDetail(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError：");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Logger.d("名称："+movie.getTitle());
                    }
                });
    }
}
