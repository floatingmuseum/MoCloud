package com.floatingmuseum.mocloud.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.orhanobut.logger.Logger;

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

    private int staffId;
    private StaffMoviesPresenter presenter;
    private List<Staff> works;
    private StaffMoviesAdapter adapter;

    public static Fragment newInstance(int staffId) {
        StaffMoviesFragment fragment = new StaffMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("staffid", staffId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_movies, container, false);
        staffId = getArguments().getInt("staffid",-1);
        ButterKnife.bind(this, view);
        presenter = new StaffMoviesPresenter(this);
        initView();
        requestBaseData();
        return view;
    }

    @Override
    protected void initView() {
        staffMoviesRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        staffMoviesRv.setLayoutManager(manager);
        works = new ArrayList<>();
        adapter = new StaffMoviesAdapter(works);
        staffMoviesRv.setAdapter(adapter);

        staffMoviesRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                openMovieDetailActivity(works.get(i));
            }
        });
    }

    @Override
    protected void requestBaseData() {
        presenter.start(staffId);
    }

    public void onBaseDataSuccess(List<Staff> works) {
        this.works.addAll(works);
        adapter.notifyDataSetChanged();
    }
}
