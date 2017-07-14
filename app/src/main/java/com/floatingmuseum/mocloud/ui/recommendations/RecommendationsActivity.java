package com.floatingmuseum.mocloud.ui.recommendations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.entity.FeatureList;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.github.lzyzsd.circleprogress.DonutProgress;
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
    @BindView(R.id.ll_recommend_list_container)
    LinearLayout llRecommendListContainer;


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
        llRecommendListContainer.setVisibility(View.VISIBLE);
    }

    public void onGetFeatureListSuccess(FeatureList list) {
        CardView cvFeatureList = (CardView) LayoutInflater.from(this).inflate(R.layout.layout_recommend_list, llFeatureListsContainer, false);
        TextView listName = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_name);
        TextView listDesc = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_desc);
        TextView listUsername = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_username);
        TextView listUpdateTime = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_update_time);
        TextView listItemsCount = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_items_count);
        DonutProgress dtListProgress = (DonutProgress) cvFeatureList.findViewById(R.id.dt_feature_list_progress);
        TextView tvListProgress = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_progress);
        TextView listLikes = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_likes);
        TextView listComments = (TextView) cvFeatureList.findViewById(R.id.tv_feature_list_comments);

        listName.setText(list.getName());
        listDesc.setText(list.getDescription());
        listUsername.setText(list.getUser().getName());
        listUpdateTime.setText(TimeUtil.formatGmtTime(list.getUpdated_at()));
        listItemsCount.setText(String.valueOf(list.getItem_count()));
        dtListProgress.setProgress(42f);
        tvListProgress.setText("42/" + list.getItem_count());
        listLikes.setText(String.valueOf(list.getLikes()));
        listComments.setText(String.valueOf(list.getComment_count()));
        llFeatureListsContainer.addView(cvFeatureList);
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
