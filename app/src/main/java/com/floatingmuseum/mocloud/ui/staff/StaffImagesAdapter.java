package com.floatingmuseum.mocloud.ui.staff;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.StaffImages;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/18.
 */

public class StaffImagesAdapter extends BaseQuickAdapter<StaffImages.Profiles,BaseViewHolder> {

    public StaffImagesAdapter( List<StaffImages.Profiles> data) {
        super(R.layout.item_staff_images, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffImages.Profiles item) {
        RatioImageView ivStaffImage = helper.getView(R.id.iv_staff_image);
        ImageLoader.load(mContext,StringUtil.buildPeopleHeadshotUrl(item.getFile_path()),ivStaffImage,R.drawable.default_movie_poster);
    }
}
