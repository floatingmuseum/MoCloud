package com.floatingmuseum.mocloud.mainmovie.Trending;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.model.entity.Image;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.entity.Trending;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseumon 2016/4/22.
 */
public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    private List<Trending> list;
    private List<Image> movieImages;
    private Context context;

    public TrendingAdapter(List<Trending> list,List<Image> movieImages,Context context){
        this.list = list;
        this.movieImages = movieImages;
        this.context = context;
    }

    @Override
    public TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
        return new TrendingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TrendingViewHolder holder, int position) {

        Trending trending = list.get(position);
        holder.tv_title.setText(trending.getMovie().getTitle());
        Logger.d("Trending:"+trending+"...image:"+movieImages.get(position));
        Glide.with(context)
                .load(movieImages.get(position).getPoster().getMedium())
                .into(holder.iv_poster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_poster)
        RatioImageView iv_poster;
        @Bind(R.id.tv_title)
        TextView tv_title;

        public TrendingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
