package com.floatingmuseum.mocloud.mainmovie.popular;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.Movie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularPresenter implements MoviePopularContract.Presenter,BaseRepo.DataCallback<List<Movie>> {

    private MoviePopularContract.View mPopularView;
    private MoviePopularRepo repo;
    private int pageNum = 1;
    protected Boolean shouldClean;

    public MoviePopularPresenter(MoviePopularContract.View popularView){
        mPopularView = popularView;
        mPopularView.setPresenter(this);
        repo = new MoviePopularRepo(this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repo.getData(pageNum);
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        mPopularView.refreshData(movies,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        mPopularView.stopRefresh();
    }
}
