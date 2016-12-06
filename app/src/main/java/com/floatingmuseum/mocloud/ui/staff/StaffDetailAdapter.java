package com.floatingmuseum.mocloud.ui.staff;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailAdapter extends BaseQuickAdapter<TmdbStaff.Credits.Cast> {


    public StaffDetailAdapter(List data) {
        super(R.layout.item_movie_trending, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TmdbStaff.Credits.Cast cast) {
        String tmdbPosterUrl = StringUtil.buildPosterUrl(cast.getPoster_path());
        Logger.d("tmdbPosterUrl:" + tmdbPosterUrl);
        ImageLoader.load(mContext, tmdbPosterUrl, (ImageView) baseViewHolder.getView(R.id.iv_poster),
                R.drawable.default_movie_poster);
    }
}
