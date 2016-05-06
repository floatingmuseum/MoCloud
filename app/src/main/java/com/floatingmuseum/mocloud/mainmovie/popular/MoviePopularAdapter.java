package com.floatingmuseum.mocloud.mainmovie.popular;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularAdapter extends RecyclerView.Adapter<MoviePopularAdapter.PopularViewHolder>{

    private List<Movie> list;
    private Context context;

    public MoviePopularAdapter(List<Movie> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
        return new PopularViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PopularViewHolder holder, int position) {
        Movie movie = list.get(position);
        holder.tv_title.setText(movie.getTitle());

        Glide.with(context)
                .load(movie.getImages().getPoster().getMedium())
                .into(holder.iv_poster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_poster)
        RatioImageView iv_poster;
        @Bind(R.id.tv_title)
        TextView tv_title;

        public PopularViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
