package com.floatingmuseum.mocloud.ui.movie.boxoffice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieBoxOfficeFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_BOXOFFICE_FRAGMENT = "MovieBoxOfficeFragment";
    @BindView(R.id.tv_loaded_failed)
    TextView tvLoadedFailed;
    private List<BaseMovie> boxOfficeList;
    private BoxOfficeAdapter adapter;

    private MovieBoxOfficePresenter presenter;


    public static MovieBoxOfficeFragment newInstance() {
        return new MovieBoxOfficeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieBoxOfficePresenter(this);

        initView();
        return rootView;
    }

    protected void initView() {
        boxOfficeList = new ArrayList<>();
        adapter = new BoxOfficeAdapter(boxOfficeList);
        rv.setHasFixedSize(true);
//        manager = new GridLayoutManager(context,2);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start(true);
            }
        });

        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openMovieDetailActivity(boxOfficeList.get(position).getMovie());
            }
        });

        requestBaseDataIfUserNotScrollToFragments(srl, presenter);

//        isViewPrepared = true;
    }

    @Override
    protected void requestBaseData() {
        srl.setRefreshing(true);
        presenter.start(true);
    }

    public void refreshData(List<BaseMovie> newData) {
        stopRefresh(srl);
        boxOfficeList.clear();
        boxOfficeList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    public void onError() {
        showErrorInfo(srl, tvLoadedFailed, boxOfficeList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
