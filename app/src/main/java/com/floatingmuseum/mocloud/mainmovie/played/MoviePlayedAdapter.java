package com.floatingmuseum.mocloud.mainmovie.played;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.date.entity.BaseMovie;
import com.floatingmuseum.mocloud.date.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedAdapter extends BaseQuickAdapter<BaseMovie>{

//    private List<BaseMovie> list;
//    private Context context;

    public MoviePlayedAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_trending,data);
    }

//    @Override
//    public PlayedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
//        return new PlayedViewHolder(v);
//    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        holder.setText(R.id.tv_title,movie.getTitle());
        ImageLoader.load(mContext,movie.getImages().getPoster().getMedium(),(RatioImageView)holder.getView(R.id.iv_poster));
    }

//    @Override
//    public void onBindViewHolder(PlayedViewHolder holder, int position) {
//        Movie movie = list.get(position).getMovie();
//        holder.tv_title.setText(movie.getTitle());
//        ImageLoader.load(context,movie.getImages().getPoster().getMedium(),holder.iv_poster);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class PlayedViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.iv_poster)
//        RatioImageView iv_poster;
//        @Bind(R.id.tv_title)
//        TextView tv_title;
//
//        public PlayedViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
