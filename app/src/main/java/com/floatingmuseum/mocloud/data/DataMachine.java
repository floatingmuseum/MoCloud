package com.floatingmuseum.mocloud.data;

import com.floatingmuseum.mocloud.data.entity.Crew;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/27.
 * <p>
 * 数据加工处理
 */

public class DataMachine {

    public static List<Staff> mixingStaffWorks(PeopleCredit peopleCredit) {
        List<Staff> works = new ArrayList();
        List<Staff> casts = peopleCredit.getCast();
        if (casts != null && casts.size() > 0) {
            Logger.d("人物作品...作为角色");
            for (Staff staff : casts) {
                staff.setItemType(Staff.CAST_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...饰演:" + staff.getCharacter());
            }
        }

        Logger.d("人物作品...作为工作人员");
        Crew crew = peopleCredit.getCrew();
        if (crew == null) {
            return works;
        }
        List<Staff> directing = crew.getDirecting();
        if (directing != null && directing.size() > 0) {
            for (Staff staff : directing) {
                staff.setItemType(Staff.DIRECTING_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        List<Staff> sound = crew.getSound();
        if (sound != null && sound.size() > 0) {
            for (Staff staff : sound) {
                staff.setItemType(Staff.SOUND_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        List<Staff> writing = crew.getWriting();
        if (writing != null && writing.size() > 0) {
            for (Staff staff : writing) {
                staff.setItemType(Staff.WRITING_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        List<Staff> production = crew.getProduction();
        if (production != null && production.size() > 0) {
            for (Staff staff : production) {
                staff.setItemType(Staff.PRODUCTION_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        List<Staff> art = crew.getArt();
        if (art != null && art.size() > 0) {
            for (Staff staff : art) {
                staff.setItemType(Staff.ART_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        List<Staff> camera = crew.getCamera();
        if (camera != null && camera.size() > 0) {
            for (Staff staff : camera) {
                staff.setItemType(Staff.CAMERA_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        List<Staff> costumeAndMakeUp = crew.getCostumeAndMakeUp();
        if (costumeAndMakeUp != null && costumeAndMakeUp.size() > 0) {
            for (Staff staff : costumeAndMakeUp) {
                staff.setItemType(Staff.COSTUME_AND_MAKEUP_ITEM);
                works.add(staff);
                Logger.d("人物作品...电影:" + staff.getMovie().getTitle() + "...负责:" + staff.getJob());
            }
        }

        return works;
    }
}
