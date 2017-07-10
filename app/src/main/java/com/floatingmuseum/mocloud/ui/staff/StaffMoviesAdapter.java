package com.floatingmuseum.mocloud.ui.staff;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/27.
 */

public class StaffMoviesAdapter extends BaseQuickAdapter<Staff, BaseViewHolder> {

    List<Staff> data;

    public StaffMoviesAdapter(List<Staff> data) {
        super(R.layout.item_staff_works, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, Staff staff) {
        if (holder.getLayoutPosition() == 0) {
            holder.setVisible(R.id.item_type_title, true);
            if (staff.getItemType() == Staff.CAST_ITEM) {
                holder.setText(R.id.tv_item_title, "Actor");
            } else {
                holder.setText(R.id.tv_item_title, staff.getJob());
            }
        } else if (data.get(holder.getLayoutPosition() - 1).getItemType() != staff.getItemType()) {
            holder.setVisible(R.id.item_type_title, true);
            holder.setText(R.id.tv_item_title, staff.getJob());
        } else {
            holder.setVisible(R.id.item_type_title, false);
        }

        String year = staff.getMovie().getReleased();
        if (year == null || year.length() == 0) {
            holder.setText(R.id.tv_released_year, "N/A");
        } else {
            holder.setVisible(R.id.tv_released_year, true);
            holder.setText(R.id.tv_released_year, year);
        }
        holder.setText(R.id.tv_art_title, staff.getMovie().getTitle());
        if (staff.getItemType() == Staff.CAST_ITEM) {
            String character = staff.getCharacter();
            if (character == null || character.length() == 0) {
                holder.setText(R.id.tv_role, "N/A");
            } else {
                holder.setText(R.id.tv_role, staff.getCharacter());
            }
        } else {
            holder.setText(R.id.tv_role, staff.getJob());
        }
        holder.setVisible(R.id.tv_role, true);
        RatioImageView ivPoster = holder.getView(R.id.iv_poster);
        loadPoster(ivPoster,staff.getMovie());
    }

    protected void loadPoster(RatioImageView posterView, Movie movie) {
//        TmdbMovieImage image = movie.getImage();
//        Logger.d("MovieName:" + movie.getTitle() + "..." + image);
//        if (image != null) {
//            if (image.isHasCache()) {
//                File file = image.getCacheFile();
//                ImageLoader.load(mContext, file, posterView, R.drawable.default_movie_poster);
//                Logger.d("图片从本地加载:" + movie.getTitle() + "..." + file.getName());
//                return;
//            } else if (image.isHasPoster()) {
//                String tmdbPosterUrl = StringUtil.buildPosterUrl(image.getPosters().get(0).getFile_path());
//                ImageLoader.load(mContext, tmdbPosterUrl, posterView, R.drawable.default_movie_poster);
//                Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getId() + "...tmdbPosterUrl:" + tmdbPosterUrl);
//                return;
//            }
//        }
        Logger.d("没有图片showImage:" + movie.getTitle());
        ImageLoader.loadDefault(mContext, posterView);
    }
}
