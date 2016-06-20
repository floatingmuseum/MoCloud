package com.floatingmuseum.mocloud.mainmovie.played;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedAdapter extends RecyclerView.Adapter<MoviePlayedAdapter.PlayedViewHolder>{

    private List<BaseMovie> list;
    private Context context;

    public MoviePlayedAdapter(List<BaseMovie> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public PlayedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
        return new PlayedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlayedViewHolder holder, int position) {
        Movie movie = list.get(position).getMovie();
        holder.tv_title.setText(movie.getTitle());
        ImageLoader.load(context,movie.getImages().getPoster().getMedium(),holder.iv_poster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PlayedViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_poster)
        RatioImageView iv_poster;
        @Bind(R.id.tv_title)
        TextView tv_title;

        public PlayedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
