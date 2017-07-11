package com.floatingmuseum.mocloud.ui.recommendations;


import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.Movie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/7/11.
 */

public class PickerAdapter extends BaseMovieItemAdapter<Movie,BaseViewHolder> {

    public PickerAdapter( List data) {
        super(R.layout.item_picker, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie item) {

    }
}
