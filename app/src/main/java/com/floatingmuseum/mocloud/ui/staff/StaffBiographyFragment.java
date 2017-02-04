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
import com.floatingmuseum.mocloud.data.entity.Staff;

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

    private int staffId;
    private StaffBiographyPresenter presenter;

    public static Fragment newInstance(int staffId) {
        StaffBiographyFragment fragment = new StaffBiographyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("staffid", staffId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_bio, container, false);
        ButterKnife.bind(this, view);
        staffId = getArguments().getInt("staffid",-1);
        presenter = new StaffBiographyPresenter(this);
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

    public void onBaseDataSuccess(Staff staff){
//        tvBirthday.setText(staff.getPerson());
//        tvBirthplace.setText(staff.getPlace_of_birth());
//        tvHomepage.setText(staff.getHomepage());
//        tvBiography.setText(staff.getBiography());
    }
}
