package com.floatingmuseum.mocloud.ui.movie.anticipated;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
public class MovieAnticipatedFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tv_loaded_failed)
    TextView tvLoadedFailed;

    public final static String MOVIE_ANTICIPATED_FRAGMENT = "MovieAnticipatedFragment";

    private List<BaseMovie> anticipatedList;
    private AnticipatedAdapter adapter;
    //    private TestAdapter testAdapter;
    private MovieAnticipatedPresenter presenter;
    private GridLayoutManager manager;

    public static MovieAnticipatedFragment newInstance() {
        return new MovieAnticipatedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieAnticipatedPresenter(this);

        initView();
        return rootView;
    }

    protected void initView() {
        anticipatedList = new ArrayList<>();
        adapter = new AnticipatedAdapter(anticipatedList);
//        testAdapter = new TestAdapter(getActivity(),anticipatedList);
        rv.setHasFixedSize(true);
        manager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
//        rv.setAdapter(testAdapter);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start(true);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(manager, adapter, presenter, srl);
            }
        });

        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openMovieDetailActivity(anticipatedList.get(position).getMovie());
            }
        });

        requestBaseDataIfUserNotScrollToFragments(srl, presenter);
    }

    @Override
    protected void requestBaseData() {
        srl.setRefreshing(true);
        presenter.start(true);
    }

    public void refreshData(List<BaseMovie> newData, boolean shouldClean) {
        stopRefresh(srl);
        checkDataSize(newData, presenter.getLimit());
        if (shouldClean) {
            anticipatedList.clear();
        }
        anticipatedList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    public void onError() {
        showErrorInfo(srl, tvLoadedFailed, anticipatedList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
