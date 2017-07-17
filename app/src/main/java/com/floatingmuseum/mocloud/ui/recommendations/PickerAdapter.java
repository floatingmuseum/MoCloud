package com.floatingmuseum.mocloud.ui.recommendations;


import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/7/11.
 */

class PickerAdapter extends BaseMovieItemAdapter<Movie, BaseViewHolder> {

    PickerAdapter(List data) {
        super(R.layout.item_recommend_picker, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie item) {
        Logger.d("Picker...convert:" + item.getImage().toString());
        ImageLoader.loadArtImage(mContext, item.getImage(), (RatioImageView) helper.getView(R.id.iv_poster), ImageCacheManager.TYPE_POSTER);
    }
}
