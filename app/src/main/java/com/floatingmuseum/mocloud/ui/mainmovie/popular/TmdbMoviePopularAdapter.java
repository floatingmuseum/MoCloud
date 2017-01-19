package com.floatingmuseum.mocloud.ui.mainmovie.popular;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class TmdbMoviePopularAdapter extends BaseQuickAdapter<TmdbMovieDetail, BaseViewHolder> {

    public TmdbMoviePopularAdapter(List<TmdbMovieDetail> data) {
        super(R.layout.item_movie_trending, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, TmdbMovieDetail movie) {
        holder.setText(R.id.tv_title, movie.getTitle()).setVisible(R.id.tv_title,false);
        File imageCacheFile = movie.getImageCacheFile();
        if (imageCacheFile != null) {
            Logger.d("图片下载:...加载自SdCard..." + movie.getTitle()+"..."+imageCacheFile);
            ImageLoader.load(mContext, imageCacheFile, (RatioImageView) holder.getView(R.id.iv_poster), R.drawable.default_movie_poster);
            return;
        }
        String posterUrl = movie.getPoster_path();
        if (posterUrl!=null && posterUrl.length()>0) {
            Logger.d("图片下载:...加载自网络..." + movie.getTitle()+"..."+StringUtil.buildPosterUrl(posterUrl));
            ImageLoader.load(mContext, StringUtil.buildPosterUrl(posterUrl), (RatioImageView) holder.getView(R.id.iv_poster), R.drawable.default_movie_poster);
            return;
        }
        holder.setText(R.id.tv_title, movie.getTitle()).setVisible(R.id.tv_title,true);
        ImageLoader.loadDefault(mContext,(RatioImageView) holder.getView(R.id.iv_poster));
    }
}
