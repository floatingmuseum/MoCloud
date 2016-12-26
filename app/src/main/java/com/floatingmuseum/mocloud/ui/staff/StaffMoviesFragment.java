package com.floatingmuseum.mocloud.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.orhanobut.logger.Logger;

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

    private String staffId;
    private StaffMoviesPresenter presenter;

    public static Fragment newInstance(String staffId) {
        StaffMoviesFragment fragment = new StaffMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("staffid", staffId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_movies, container, false);
        staffId = getArguments().getString("staffid");
        ButterKnife.bind(this, view);
        presenter = new StaffMoviesPresenter(this, Repository.getInstance());
        requestBaseData();
        return view;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void requestBaseData() {
        presenter.start(staffId);
    }

    public void onBaseDataSuccess(PeopleCredit peopleCredit) {

    }
}
