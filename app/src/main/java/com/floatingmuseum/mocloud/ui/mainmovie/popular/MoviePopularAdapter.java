package com.floatingmuseum.mocloud.ui.mainmovie.popular;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularAdapter extends BaseQuickAdapter<Movie>{

//    private List<Movie> list;
//    private Context context;

    public MoviePopularAdapter(List<Movie> data){
        super(R.layout.item_movie_trending,data);
    }

//    @Override
//    public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
//        return new PopularViewHolder(v);
//    }

    @Override
    protected void convert(BaseViewHolder holder, Movie movie) {
        holder.setText(R.id.tv_title,movie.getTitle());
        ImageLoader.load(mContext,movie.getImages().getPoster().getMedium(),(RatioImageView)holder.getView(R.id.iv_poster));
    }

//    @Override
//    public void onBindViewHolder(PopularViewHolder holder, int position) {
//        Movie movie = list.get(position);
//        holder.tv_title.setText(movie.getTitle());
//        ImageLoader.load(context,movie.getImages().getPoster().getMedium(),holder.iv_poster);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class PopularViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.iv_poster)
//        RatioImageView iv_poster;
//        @Bind(R.id.tv_title)
//        TextView tv_title;
//
//        public PopularViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
