package com.floatingmuseum.mocloud.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffBiographyFragment extends BaseFragment {

    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_deathday)
    TextView tvDeathday;
    @BindView(R.id.ll_deathday)
    LinearLayout llDeathday;
    @BindView(R.id.tv_birthplace)
    TextView tvBirthplace;
    @BindView(R.id.ll_birthplace)
    LinearLayout llBirthplace;
    @BindView(R.id.tv_homepage)
    TextView tvHomepage;
    @BindView(R.id.ll_homepage)
    LinearLayout llHomepage;
    @BindView(R.id.tv_biography)
    TextView tvBiography;

    private String staffId;
    private StaffBiographyPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_bio, container, false);
        ButterKnife.bind(this, view);
        staffId = getArguments().getString("staffid");
        presenter = new StaffBiographyPresenter(this, Repository.getInstance());
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

    public void onBaseDataSuccess(Person person){
        tvBirthday.setText(person.getBirthday());
        tvBirthplace.setText(person.getBirthplace());
        tvHomepage.setText(person.getHomepage());
        tvBiography.setText(person.getBiography());
    }

    public static Fragment newInstance(String staffId) {
        StaffBiographyFragment fragment = new StaffBiographyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("staffid", staffId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
