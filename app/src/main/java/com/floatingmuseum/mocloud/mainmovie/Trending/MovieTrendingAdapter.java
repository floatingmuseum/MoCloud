package com.floatingmuseum.mocloud.mainmovie.trending;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseumon 2016/4/22.
 */
public class MovieTrendingAdapter extends BaseQuickAdapter<BaseMovie> {
//    RecyclerView.Adapter<MovieTrendingAdapter.TrendingViewHolder>
//    private List<BaseMovie> data;
//    private Context context;

    public MovieTrendingAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_trending,data);
//        this.data = data;
    }


//    public MovieTrendingAdapter(List<BaseMovie> list, Context context){
//        this.list = list;
//        this.context = context;
//    }

//    @Override
//    public TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
//        return new TrendingViewHolder(v);
//    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        holder.setText(R.id.tv_title,movie.getTitle());
        ImageLoader.load(mContext,movie.getImages().getPoster().getMedium(),(RatioImageView)holder.getView(R.id.iv_poster));
    }

//    @Override
//    public void onBindViewHolder(TrendingViewHolder holder, int position) {
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
//    public class TrendingViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.iv_poster)
//        RatioImageView iv_poster;
//        @Bind(R.id.tv_title)
//        TextView tv_title;
//
//        public TrendingViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
