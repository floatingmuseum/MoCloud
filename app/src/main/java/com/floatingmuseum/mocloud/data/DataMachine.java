package com.floatingmuseum.mocloud.data;

import android.text.format.DateUtils;
import android.util.SparseArray;

import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbStaffMovieCredits;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/27.
 * <p>
 * 数据加工处理
 */

public class DataMachine {

    public static List<Staff> mixingStaffWorks(TmdbStaffMovieCredits peopleCredit) {
        List<Staff> works = new ArrayList();
        List<Staff> casts = peopleCredit.getCast();
        if (casts != null && casts.size() > 0) {
            sort(casts);
            for (Staff staff : casts) {
                staff.setItemType(Staff.CAST_ITEM);
                works.add(staff);
            }
        }

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
            directors = sort(directors);
            works.addAll(directors);
        }
        if (writings.size() > 0) {
            writings = sort(writings);
            works.addAll(writings);
        }
        if (cameras.size() > 0) {
            cameras = sort(cameras);
            works.addAll(cameras);
        }
        if (editings.size() > 0) {
            editings = sort(editings);
            works.addAll(editings);
        }
        if (arts.size() > 0) {
            arts = sort(arts);
            works.addAll(arts);
        }
        if (makeups.size() > 0) {
            makeups = sort(makeups);
            works.addAll(makeups);
        }
        if (productions.size() > 0) {
            productions = sort(productions);
            works.addAll(productions);
        }
        if (sounds.size() > 0) {
            sounds = sort(sounds);
            works.addAll(sounds);
        }
        if (visualEffects.size() > 0) {
            visualEffects = sort(visualEffects);
            works.addAll(visualEffects);
        }
        if (lightings.size() > 0) {
            lightings = sort(lightings);
            works.addAll(lightings);
        }
        return works;
    }

    /**
     * 按作品Release日期来重新排序
     */
    private static List<Staff> sort(List<Staff> staffs) {
        Collections.sort(staffs, new Comparator<Staff>() {
            @Override
            public int compare(Staff lhs, Staff rhs) {
                boolean has1 = hasDate(lhs.getRelease_date());
                boolean has2 = hasDate(rhs.getRelease_date());
                if (!has1 && !has2) {
                    return 0;
                } else if (!has1) {
                    return 1;
                } else if (!has2) {
                    return -1;
                }
                Date date1 = TimeUtil.formatStringToDate(lhs.getRelease_date(), TimeUtil.TIME_FORMAT2);
                Date date2 = TimeUtil.formatStringToDate(rhs.getRelease_date(), TimeUtil.TIME_FORMAT2);
                if (!date1.before(date2) && !date1.after(date2)) {
                    return 0;
                }
                if (date1.before(date2)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return staffs;
    }

    private static boolean hasDate(String date) {
        if (date == null || date.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static MovieTeam mixingStaffWorks(People people){
        ArrayList team = new ArrayList();
        List<Staff> directors = people.getCrew().getDirecting();
        if (directors!=null && directors.size()>0){
            team.add(directors.get(0));
        }
        List<Staff> casts = people.getCast();
        int castNum;
        if (team.size()>0){
            castNum = casts.size()<3?casts.size():3;
        }else{
            castNum = casts.size()<4?casts.size():4;
        }
        for (int i = 0; i < castNum; i++) {
            team.add(casts.get(i));
        }

        MovieTeam movieTeam = new MovieTeam();
        movieTeam.setDetailShowList(team);
        movieTeam.setPeople(people);
        return movieTeam;
    }
}
