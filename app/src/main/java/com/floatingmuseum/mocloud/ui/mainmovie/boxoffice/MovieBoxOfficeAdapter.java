package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficeAdapter extends BaseQuickAdapter<BaseMovie>{

//    private List<BaseMovie> list;
//    private Context context;

    public MovieBoxOfficeAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_trending,data);
//        this.list = list;
//        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        holder.setText(R.id.tv_title,movie.getTitle());
        ImageLoader.load(mContext,movie.getImages().getPoster().getMedium(),(RatioImageView)holder.getView(R.id.iv_poster));
    }

//    @Override
//    public BoxOfficeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_trending,parent,false);
//        return new BoxOfficeViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(BoxOfficeViewHolder holder, int position) {
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
//    public class BoxOfficeViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.iv_poster)
//        RatioImageView iv_poster;
//        @Bind(R.id.tv_title)
//        TextView tv_title;
//
//        public BoxOfficeViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
