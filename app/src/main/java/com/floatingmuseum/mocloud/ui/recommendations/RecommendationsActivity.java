package com.floatingmuseum.mocloud.ui.recommendations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class RecommendationsActivity extends BaseActivity {

    @BindView(R.id.dsv_picker)
    DiscreteScrollView dsvPicker;
    @BindView(R.id.avl_recommend_loading)
    AVLoadingIndicatorView avlRecommendLoading;
    @BindView(R.id.ll_feature_lists_container)
    LinearLayout llFeatureListsContainer;


    private RecommendationsPresenter presenter;
    private List<Movie> data;
    private PickerAdapter pickerAdapter;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_recommendations;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new RecommendationsPresenter(this);
        initView();
        triggerRefreshRequest();
    }

    @Override
    protected void initView() {
        data = new ArrayList<>();
        pickerAdapter = new PickerAdapter(data);
        dsvPicker.setOffscreenItems(1);
        dsvPicker.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());
        dsvPicker.setAdapter(pickerAdapter);
        dsvPicker.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                Logger.d("Picker...onScrollStart:" + adapterPosition);
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                Logger.d("Picker...onScrollEnd:" + adapterPosition);
            }

            @Override
            public void onScroll(float scrollPosition, @NonNull RecyclerView.ViewHolder currentHolder, @NonNull RecyclerView.ViewHolder newCurrent) {
                Logger.d("Picker...onScroll:" + scrollPosition);
            }
        });
    }

    private void triggerRefreshRequest() {
        avlRecommendLoading.smoothToShow();
        presenter.getData();
    }

    public void onBaseDataSuccess(List<Movie> movies) {
        avlRecommendLoading.smoothToHide();
        data.clear();
        data.addAll(movies);
        pickerAdapter.notifyDataSetChanged();
    }

    public void onHideMovieSuccess() {
        ToastUtil.showToast("Hide success.");
    }

    @Override
    protected void onError(Exception e) {
        avlRecommendLoading.smoothToHide();
        ToastUtil.showToast("Loading lists failed.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
    }
}
