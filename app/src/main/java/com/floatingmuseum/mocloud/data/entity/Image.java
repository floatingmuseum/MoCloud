package com.floatingmuseum.mocloud.data.entity;

import java.io.Serializable;

/**
 * Created by Floatingmuseum on 2016/4/18.
 */
public class Image{

    private Fanart fanart;
    private Poster poster;
    private Logo logo;
    private Clearart clearart;
    private Banner banner;
    private Thumb thumb;
    private HeadShot headshot;
    private Avatar avatar;

    public HeadShot getHeadshot() {
        return headshot;
    }

    public void setHeadshot(HeadShot headshot) {
        this.headshot = headshot;
    }

    public Fanart getFanart() {
        return fanart;
    }

    public void setFanart(Fanart fanart) {
        this.fanart = fanart;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public Clearart getClearart() {
        return clearart;
    }

    public void setClearart(Clearart clearart) {
        this.clearart = clearart;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public class Fanart extends BaseImage{}
    public class Poster extends BaseImage{}
    public class Logo extends BaseImage{}
    public class Clearart extends BaseImage{}
    public class Banner extends BaseImage{}
    public class Thumb extends BaseImage{}
    public class HeadShot extends BaseImage{}
    public class Avatar extends BaseImage{}


    public class BaseImage{
        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        private String full;
        private String medium;
        private String thumb;
    }
}
