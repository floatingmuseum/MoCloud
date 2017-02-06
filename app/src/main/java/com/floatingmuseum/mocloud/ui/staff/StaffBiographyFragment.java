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
import com.floatingmuseum.mocloud.data.entity.Person;
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

    private Staff staff;
    private StaffBiographyPresenter presenter;

    public static Fragment newInstance(Staff staff) {
        StaffBiographyFragment fragment = new StaffBiographyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("staff", staff);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_bio, container, false);
        ButterKnife.bind(this, view);
        staff = getArguments().getParcelable("staff");
//        presenter = new StaffBiographyPresenter(this);
        initView();
        return view;
    }

    @Override
    protected void initView() {
        Person person = staff.getPerson();
        tvBirthday.setText(person.getBirthday());
        tvBirthplace.setText(person.getBirthplace());
        tvHomepage.setText(person.getHomepage());
        tvBiography.setText(person.getBiography());
        tvDeathday.setText(person.getDeath());
    }

    @Override
    protected void requestBaseData() {
    }
}
