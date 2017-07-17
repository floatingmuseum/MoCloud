package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
class MovieBoxOfficeAdapter extends BaseMovieItemAdapter<BaseMovie, BaseViewHolder> {

    MovieBoxOfficeAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_box_office, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        Logger.d("图片显示...box..."+movie.getTitle()+"..."+movie.getImage().toString());

        holder.setText(R.id.tv_box_movie_title, movie.getTitle())
                .setText(R.id.tv_revenue, "$"+baseMovie.getRevenue());
        ArtImage image = movie.getImage();
        ImageLoader.loadArtImage(mContext, image, (ImageView) holder.getView(R.id.iv_boxoffice_poster), ImageCacheManager.TYPE_BACKDROP);
    }
}
