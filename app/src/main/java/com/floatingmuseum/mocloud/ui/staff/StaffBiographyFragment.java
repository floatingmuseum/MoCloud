package com.floatingmuseum.mocloud.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffBiographyFragment extends BaseFragment {

    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_deathday)
    TextView tvDeathday;
    @BindView(R.id.tv_birthplace)
    TextView tvBirthplace;
    @BindView(R.id.tv_homepage)
    TextView tvHomepage;
    @BindView(R.id.tv_biography)
    TextView tvBiography;
    @BindView(R.id.tv_birthday_title)
    TextView tvBirthdayTitle;
    @BindView(R.id.tv_deathday_title)
    TextView tvDeathdayTitle;
    @BindView(R.id.tv_birthplace_title)
    TextView tvBirthplaceTitle;
    @BindView(R.id.tv_homepage_title)
    TextView tvHomepageTitle;
    @BindView(R.id.tv_biography_title)
    TextView tvBiographyTitle;
    @BindView(R.id.tv_no_bio)
    TextView tvNoBio;

    private Staff staff;
    private StaffBiographyPresenter presenter;
    private boolean noAnyInfo = true;

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
        Logger.d("Birthday:" + person.getBirthday() + "...DeathDay:" + person.getDeath() + "...BirthPlace:" + person.getBirthplace() + "...HomePage:" + person.getHomepage() + "...Bio:" + person.getBiography());
        initText(person.getBirthday(), tvBirthday, tvBirthdayTitle);
        initText(person.getDeath(), tvDeathday, tvDeathdayTitle);
        initText(person.getBirthplace(), tvBirthplace, tvBirthplaceTitle);
        initText(person.getHomepage(), tvHomepage, tvHomepageTitle);
        initText(person.getBiography(), tvBiography, tvBiographyTitle);
        if (noAnyInfo) {
            tvNoBio.setVisibility(View.VISIBLE);
        }
    }

    private void initText(String text, TextView textView, TextView textViewTitle) {
        if (text != null && text.length() > 0) {
            noAnyInfo = false;
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
            textViewTitle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void requestBaseData() {
    }
}
