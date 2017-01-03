package com.floatingmuseum.mocloud.utils;


import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.data.entity.User;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/29.
 * <p>
 * 主要处理一下各网站独有数据的处理
 */

public class MoCloudUtil {

    public static String getUsername(User user) {
        if (user.isPrivateX()) {
            return user.getUsername();
        }
        String name = user.getName();
        if (name != null && name.length() > 0) {
            return name;
        } else {
            return user.getUsername();
        }
    }

    public static String getUserAvatar(User user) {
        if (user.isPrivateX()) {
            return "";
        } else {
            return user.getImages().getAvatar().getFull();
        }
    }

    public static float getAverageRating(Stats.RatingsCount ratingsCount) {
        if (ratingsCount.getTotal() == 0) {
            return 0;
        }
        Stats.RatingsCount.DistributionCount distributionCount = ratingsCount.getDistribution();
        return (distributionCount.getValue1() * 1
                + distributionCount.getValue2() * 2
                + distributionCount.getValue3() * 3
                + distributionCount.getValue4() * 4
                + distributionCount.getValue5() * 5
                + distributionCount.getValue6() * 6
                + distributionCount.getValue7() * 7
                + distributionCount.getValue8() * 8
                + distributionCount.getValue9() * 9
                + distributionCount.getValue10() * 10) / ratingsCount.getTotal();
    }

    public static String getTimeCost(int minutes) {
        if (minutes < 60) {
            return minutes + "minutes";
        }
        int minutesRemainder = minutes % 60;
        int hours = minutes / 60;
        if (hours < 24 && minutesRemainder > 0) {
            return hours + "hours " + minutesRemainder + " minutes";
        } else if (hours < 24) {
            return hours + "hours";
        }

        int hoursRemainder = hours % 24;
        int day = hours / 24;
        if (hoursRemainder != 0 && minutesRemainder != 0) {
            return day + "day " + hoursRemainder + "hours " + minutesRemainder + " minutes";
        } else if (hoursRemainder != 0) {
            return day + "day " + hoursRemainder + "hours ";
        } else if (minutesRemainder != 0) {
            return day + "day " + minutesRemainder + " minutes";
        }
        return day + "days";
    }

    public static boolean isFollowing(List<Follower> followers) {
        String slug = SPUtil.getString(SPUtil.SP_USER_SETTINGS, "slug");
        for (Follower follower : followers) {
            if (follower.getUser().getIds().getSlug().equals(slug)) {
                return true;
            }
        }
        return false;
    }

    public static Staff getDirector(List<Staff> crews) {
        if (crews != null && crews.size() > 0) {
            for (Staff staff : crews) {
                if (staff.getJob().equals("Director")) {
                    return staff;
                }
            }
        }
        return null;
    }
}
