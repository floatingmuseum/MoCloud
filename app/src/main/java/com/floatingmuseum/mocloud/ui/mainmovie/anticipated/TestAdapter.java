package com.floatingmuseum.mocloud.ui.mainmovie.anticipated;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/7.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    List<BaseMovie> data;
    Context context;
    public TestAdapter(Context context, List<BaseMovie> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_trending, parent, false);
        return new TestHolder(v);
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
        BaseMovie baseMovie = data.get(position);
        Movie movie = baseMovie.getMovie();
        loadPoster(holder,movie);
//        showTitle(holder.tv_title,movie);
    }

    protected void loadPoster(TestHolder holder, Movie movie) {
        holder.tv_title.setVisibility(View.GONE);
        TmdbMovieImage image = movie.getImage();
        Logger.d("MovieName:" + movie.getTitle() + "..." + image);
        if (image != null) {
            if (image.isHasCache()) {
                File file = image.getCacheFile();
                ImageLoader.load(context, file, holder.iv_poster, R.drawable.default_movie_poster);
                Logger.d("图片从本地加载:" + movie.getTitle() + "..." + file.getName());
                return;
            } else if (image.isHasPoster()) {
                String tmdbPosterUrl = StringUtil.buildPosterUrl(image.getPosters().get(0).getFile_path());
                ImageLoader.load(context, tmdbPosterUrl, holder.iv_poster, R.drawable.default_movie_poster);
                Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getId() + "...tmdbPosterUrl:" + tmdbPosterUrl);
                return;
            }
        } else {
            Logger.d("没有图片showImage:" + movie.getTitle());
            ImageLoader.loadDefault(context, holder.iv_poster);
            holder.tv_title.setVisibility(View.VISIBLE);
            holder.tv_title.setText(movie.getTitle());
        }
    }

    protected void showTitle(TextView titleView, Movie movie) {
        TmdbMovieImage image = movie.getImage();
        if (image == null || (!image.isHasPoster() & !image.isHasCache())) {
            Logger.d("没有图片showTitle:" + movie.getTitle());
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(movie.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TestHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_poster)
        RatioImageView iv_poster;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public TestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
