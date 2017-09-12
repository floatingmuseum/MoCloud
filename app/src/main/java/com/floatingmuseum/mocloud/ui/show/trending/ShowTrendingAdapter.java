package com.floatingmuseum.mocloud.ui.show.trending;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseShow;
import com.floatingmuseum.mocloud.data.entity.Show;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/9/12.
 */

public class ShowTrendingAdapter extends BaseItemAdapter<BaseShow, BaseViewHolder> {
    public ShowTrendingAdapter(List<BaseShow> trendingList) {
        super(R.layout.item_show_trending, trendingList);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseShow item) {
        Show show = item.getShow();
        showTitle((TextView) helper.getView(R.id.tv_title), show);
    }
}
