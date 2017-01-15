package com.floatingmuseum.mocloud.ui.recommendations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class RecommendationsActivity extends BaseActivity {
    @Override
    protected int currentLayoutId() {
        return R.layout.activity_recommendations;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onError(Exception e) {

    }
}
