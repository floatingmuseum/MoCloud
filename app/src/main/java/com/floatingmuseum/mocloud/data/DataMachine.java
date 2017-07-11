package com.floatingmuseum.mocloud.data;

import com.floatingmuseum.mocloud.data.entity.Crew;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/27.
 * <p>
 * 数据加工处理
 */

class DataMachine {

    static MovieTeam mixingStaffsWorks(PeopleCredit people) {
        ArrayList<Staff> team = new ArrayList<>();
        if (people != null) {
            if (people.getCrew() != null) {
                List<Staff> directors = people.getCrew().getDirecting();
                if (directors != null && directors.size() > 0) {
                    for (Staff director : directors) {
                        if (director.getJob().equals("Director")) {
                            team.add(director);
                            break;
                        }
                    }
                }
            }
            if (people.getCast() != null) {
                List<Staff> casts = people.getCast();
                int castNum;
                if (team.size() > 0) {
                    castNum = casts.size() < 3 ? casts.size() : 3;
                } else {
                    castNum = casts.size() < 4 ? casts.size() : 4;
                }
                for (int i = 0; i < castNum; i++) {
                    team.add(casts.get(i));
                }
            }
        }
        MovieTeam movieTeam = new MovieTeam();
        movieTeam.setDetailShowList(team);
        movieTeam.setPeopleCredit(people);
        return movieTeam;
    }

    static List<Staff> mixingPersonWorks(PeopleCredit people) {
        List<Staff> works = new ArrayList<>();
        List<Staff> casts = people.getCast();
        if (casts != null && casts.size() > 0) {
            sort(casts);
            for (Staff staff : casts) {
                staff.setItemType(Staff.CAST_ITEM);
                works.add(staff);
            }
        }

        Crew crew = people.getCrew();
        if (crew != null) {
            mergeJobToWorks(crew.getDirecting(), works, Staff.DIRECTING_ITEM);
            mergeJobToWorks(crew.getWriting(), works, Staff.WRITING_ITEM);
            mergeJobToWorks(crew.getSound(), works, Staff.SOUND_ITEM);
            mergeJobToWorks(crew.getProduction(), works, Staff.PRODUCTION_ITEM);
            mergeJobToWorks(crew.getEditing(), works, Staff.EDITING_ITEM);
            mergeJobToWorks(crew.getArt(), works, Staff.ART_ITEM);
            mergeJobToWorks(crew.getCamera(), works, Staff.CAMERA_ITEM);
            mergeJobToWorks(crew.getCostumeAndMakeUp(), works, Staff.COSTUME_AND_MAKEUP_ITEM);
            mergeJobToWorks(crew.getVisualEffects(), works, Staff.VISUAL_EFFECTS_ITEM);
            mergeJobToWorks(crew.getLighting(), works, Staff.LIGHTING_ITEM);
            mergeJobToWorks(crew.getCrew(), works, Staff.CREW_ITEM);
        }
        return works;
    }

    private static void mergeJobToWorks(List<Staff> jobs, List<Staff> works, String itemType) {
        if (jobs != null && jobs.size() > 0) {
            for (Staff job : jobs) {
                job.setItemType(itemType);
                works.add(job);
            }
        }
    }

    /**
     * 按作品Release日期来重新排序
     */
    private static List<Staff> sort(List<Staff> staffs) {
        Collections.sort(staffs, new Comparator<Staff>() {
            @Override
            public int compare(Staff lhs, Staff rhs) {
                boolean has1 = hasDate(lhs.getMovie().getReleased());
                boolean has2 = hasDate(rhs.getMovie().getReleased());
                if (!has1 && !has2) {
                    return 0;
                } else if (!has1) {
                    return 1;
                } else if (!has2) {
                    return -1;
                }
                Date date1 = TimeUtil.formatStringToDate(lhs.getMovie().getReleased(), TimeUtil.TIME_FORMAT2);
                Date date2 = TimeUtil.formatStringToDate(rhs.getMovie().getReleased(), TimeUtil.TIME_FORMAT2);
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
        return !(date == null || date.length() == 0);
    }
}
