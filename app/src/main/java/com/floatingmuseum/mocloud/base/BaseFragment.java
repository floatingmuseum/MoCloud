package com.floatingmuseum.mocloud.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.dagger.presenter.DaggerMoviePresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MoviePresenterModule;
import com.floatingmuseum.mocloud.mainmovie.trending.MovieTrendingFragment;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class BaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
