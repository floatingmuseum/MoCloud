package com.floatingmuseum.mocloud.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffMoviesFragment extends BaseFragment {
    @BindView(R.id.staff_movies_rv)
    RecyclerView staffMoviesRv;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Staff staff;
    private StaffMoviesPresenter presenter;
    private List<Staff> works;
    private StaffMoviesAdapter adapter;
    private LinearLayoutManager manager;
    private boolean alreadyGetAllImages = false;
    private int traktId;

    public static Fragment newInstance(Staff staff) {
        StaffMoviesFragment fragment = new StaffMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("staff", staff);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_movies, container, false);
        staff = getArguments().getParcelable("staff");
        traktId = staff.getPerson().getIds().getTrakt();
        ButterKnife.bind(this, view);
        presenter = new StaffMoviesPresenter(this);
        initView();
        requestBaseData();
        return view;
    }

    @Override
    protected void initView() {
        staffMoviesRv.setHasFixedSize(true);
        manager = new LinearLayoutManager(context);
        staffMoviesRv.setLayoutManager(manager);
        works = new ArrayList<>();
        adapter = new StaffMoviesAdapter(works);
        staffMoviesRv.setAdapter(adapter);

        staffMoviesRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openMovieDetailActivity(works.get(position).getMovie());
            }
        });

        staffMoviesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (alreadyGetAllImages) {
                    return;
                }
                int lastItemPosition = manager.findLastVisibleItemPosition();
                if ((lastItemPosition + 3) == adapter.getItemCount() && !swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                    presenter.start(traktId, true);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start(traktId, false);
            }
        });
    }

    @Override
    protected void requestBaseData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.start(traktId, false);
    }

    public void onGetWorksImagesSuccess(List<Staff> staffs, boolean isLoadMore) {
        swipeRefreshLayout.setRefreshing(false);
        if (staffs == null) {
            ToastUtil.showToast(R.string.already_get_all_data);
            alreadyGetAllImages = true;
            return;
        } else if (staffs.size() < 10) {
            ToastUtil.showToast(R.string.already_get_all_data);
            alreadyGetAllImages = true;
        } else {
            alreadyGetAllImages = false;
        }


        if (!isLoadMore) {
            works.clear();
        }
        works.addAll(staffs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
    }
}
