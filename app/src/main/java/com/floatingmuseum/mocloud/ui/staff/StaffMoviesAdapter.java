package com.floatingmuseum.mocloud.ui.staff;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/27.
 */

public class StaffMoviesAdapter extends BaseQuickAdapter<Staff> {

    List<Staff> data;

    public StaffMoviesAdapter(List<Staff> data) {
        super(R.layout.item_staff_works, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, Staff staff) {
        if (holder.getLayoutPosition() == 0) {
            holder.setVisible(R.id.item_type_title, true);
            if (staff.getItemType() == Staff.CAST_ITEM) {
                holder.setText(R.id.tv_item_title, "Actor");
            } else {
                holder.setText(R.id.tv_item_title, staff.getJob());
            }
        } else if (data.get(holder.getLayoutPosition() - 1).getItemType() != staff.getItemType()) {
            holder.setVisible(R.id.item_type_title, true);
            holder.setText(R.id.tv_item_title, staff.getJob());
        } else {
            holder.setVisible(R.id.item_type_title, false);
        }

        String year = staff.getRelease_date();
        if (year == null || year.length()==0) {
            holder.setVisible(R.id.tv_released_year, false);
        } else {
            holder.setText(R.id.tv_released_year, year);
        }
        holder.setText(R.id.tv_art_title, staff.getOriginal_title());
        if (staff.getItemType() == Staff.CAST_ITEM) {
            String character = staff.getCharacter();
            if (character == null || character.length() == 0) {
                holder.setVisible(R.id.tv_role, false);
            }
            holder.setText(R.id.tv_role, staff.getCharacter());
        } else {
            holder.setText(R.id.tv_role, staff.getJob());
        }
    }
}
