package com.floatingmuseum.mocloud.data;

import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbStaffMovieCredits;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/27.
 * <p>
 * 数据加工处理
 */

public class DataMachine {

    public static List<Staff> mixingStaffWorks(TmdbStaffMovieCredits peopleCredit) {
        // TODO: 2017/1/3 数据不是按照Released_Date来排列的，等一个排序
        List<Staff> works = new ArrayList();
        List<Staff> casts = peopleCredit.getCast();
        if (casts != null && casts.size() > 0) {
            Logger.d("人物作品...作为角色");
            for (Staff staff : casts) {
                staff.setItemType(Staff.CAST_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getOriginal_title() + "...饰演:" + staff.getCharacter());
            }
        }

        Logger.d("人物作品...作为工作人员");
        List<Staff> directors = new ArrayList<>();
        List<Staff> writings = new ArrayList<>();
        List<Staff> cameras = new ArrayList<>();
        List<Staff> editings = new ArrayList<>();
        List<Staff> arts = new ArrayList<>();
        List<Staff> makeups = new ArrayList<>();
        List<Staff> productions = new ArrayList<>();
        List<Staff> sounds = new ArrayList<>();
        List<Staff> visualEffects = new ArrayList<>();
        List<Staff> lightings = new ArrayList<>();

        List<Staff> crews = peopleCredit.getCrew();
        if (crews != null && crews.size() > 0) {
            for (Staff staff : crews) {
                switch (staff.getDepartment()) {
                    case Staff.DIRECTING_ITEM:
                        staff.setItemType(Staff.DIRECTING_ITEM);
                        directors.add(staff);
                        break;
                    case Staff.WRITING_ITEM:
                        staff.setItemType(Staff.WRITING_ITEM);
                        writings.add(staff);
                        break;
                    case Staff.CAMERA_ITEM:
                        staff.setItemType(Staff.CAMERA_ITEM);
                        cameras.add(staff);
                        break;
                    case Staff.EDITING_ITEM:
                        staff.setItemType(Staff.EDITING_ITEM);
                        editings.add(staff);
                        break;
                    case Staff.ART_ITEM:
                        staff.setItemType(Staff.ART_ITEM);
                        arts.add(staff);
                        break;
                    case Staff.COSTUME_AND_MAKEUP_ITEM:
                        staff.setItemType(Staff.COSTUME_AND_MAKEUP_ITEM);
                        makeups.add(staff);
                        break;
                    case Staff.PRODUCTION_ITEM:
                        staff.setItemType(Staff.PRODUCTION_ITEM);
                        productions.add(staff);
                        break;
                    case Staff.SOUND_ITEM:
                        staff.setItemType(Staff.SOUND_ITEM);
                        sounds.add(staff);
                        break;
                    case Staff.VISUAL_EFFECTS_ITEM:
                        staff.setItemType(Staff.VISUAL_EFFECTS_ITEM);
                        visualEffects.add(staff);
                        break;
                    case Staff.LIGHTING_ITEM:
                        staff.setItemType(Staff.LIGHTING_ITEM);
                        lightings.add(staff);
                        break;
                }
            }
        }

        if (directors.size() > 0) {
            works.addAll(directors);
        }
        if (writings.size() > 0) {
            works.addAll(writings);
        }
        if (cameras.size() > 0) {
            works.addAll(cameras);
        }
        if (editings.size() > 0) {
            works.addAll(editings);
        }
        if (arts.size() > 0) {
            works.addAll(arts);
        }
        if (makeups.size() > 0) {
            works.addAll(makeups);
        }
        if (productions.size() > 0) {
            works.addAll(productions);
        }
        if (sounds.size() > 0) {
            works.addAll(sounds);
        }
        if (visualEffects.size() > 0) {
            works.addAll(visualEffects);
        }
        if (lightings.size() > 0) {
            works.addAll(lightings);
        }
        return works;
    }
}
