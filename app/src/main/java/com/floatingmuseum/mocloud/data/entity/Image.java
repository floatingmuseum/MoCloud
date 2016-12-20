package com.floatingmuseum.mocloud.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2016/4/18.
 */
public class Image implements Parcelable{

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

    public static class Fanart extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Fanart() {
        }

        protected Fanart(Parcel in) {
            super(in);
        }

        public static final Creator<Fanart> CREATOR = new Creator<Fanart>() {
            @Override
            public Fanart createFromParcel(Parcel source) {
                return new Fanart(source);
            }

            @Override
            public Fanart[] newArray(int size) {
                return new Fanart[size];
            }
        };
    }
    public static class Poster extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Poster() {
        }

        protected Poster(Parcel in) {
            super(in);
        }

        public static final Creator<Poster> CREATOR = new Creator<Poster>() {
            @Override
            public Poster createFromParcel(Parcel source) {
                return new Poster(source);
            }

            @Override
            public Poster[] newArray(int size) {
                return new Poster[size];
            }
        };
    }
    public static class Logo extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Logo() {
        }

        protected Logo(Parcel in) {
            super(in);
        }

        public static final Creator<Logo> CREATOR = new Creator<Logo>() {
            @Override
            public Logo createFromParcel(Parcel source) {
                return new Logo(source);
            }

            @Override
            public Logo[] newArray(int size) {
                return new Logo[size];
            }
        };
    }
    public static class Clearart extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Clearart() {
        }

        protected Clearart(Parcel in) {
            super(in);
        }

        public static final Creator<Clearart> CREATOR = new Creator<Clearart>() {
            @Override
            public Clearart createFromParcel(Parcel source) {
                return new Clearart(source);
            }

            @Override
            public Clearart[] newArray(int size) {
                return new Clearart[size];
            }
        };
    }
    public static class Banner extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Banner() {
        }

        protected Banner(Parcel in) {
            super(in);
        }

        public static final Creator<Banner> CREATOR = new Creator<Banner>() {
            @Override
            public Banner createFromParcel(Parcel source) {
                return new Banner(source);
            }

            @Override
            public Banner[] newArray(int size) {
                return new Banner[size];
            }
        };
    }
    public static class Thumb extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Thumb() {
        }

        protected Thumb(Parcel in) {
            super(in);
        }

        public static final Creator<Thumb> CREATOR = new Creator<Thumb>() {
            @Override
            public Thumb createFromParcel(Parcel source) {
                return new Thumb(source);
            }

            @Override
            public Thumb[] newArray(int size) {
                return new Thumb[size];
            }
        };
    }
    public static class HeadShot extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public HeadShot() {
        }

        protected HeadShot(Parcel in) {
            super(in);
        }

        public static final Creator<HeadShot> CREATOR = new Creator<HeadShot>() {
            @Override
            public HeadShot createFromParcel(Parcel source) {
                return new HeadShot(source);
            }

            @Override
            public HeadShot[] newArray(int size) {
                return new HeadShot[size];
            }
        };
    }
    public static class Avatar extends BaseImage implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }

        public Avatar() {
        }

        protected Avatar(Parcel in) {
            super(in);
        }

        public static final Creator<Avatar> CREATOR = new Creator<Avatar>() {
            @Override
            public Avatar createFromParcel(Parcel source) {
                return new Avatar(source);
            }

            @Override
            public Avatar[] newArray(int size) {
                return new Avatar[size];
            }
        };
    }


    public static class BaseImage implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.full);
            dest.writeString(this.medium);
            dest.writeString(this.thumb);
        }

        public BaseImage() {
        }

        protected BaseImage(Parcel in) {
            this.full = in.readString();
            this.medium = in.readString();
            this.thumb = in.readString();
        }

        public static final Creator<BaseImage> CREATOR = new Creator<BaseImage>() {
            @Override
            public BaseImage createFromParcel(Parcel source) {
                return new BaseImage(source);
            }

            @Override
            public BaseImage[] newArray(int size) {
                return new BaseImage[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.fanart, flags);
        dest.writeParcelable(this.poster, flags);
        dest.writeParcelable(this.logo, flags);
        dest.writeParcelable(this.clearart, flags);
        dest.writeParcelable(this.banner, flags);
        dest.writeParcelable(this.thumb, flags);
        dest.writeParcelable(this.headshot, flags);
        dest.writeParcelable(this.avatar, flags);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.fanart = in.readParcelable(Fanart.class.getClassLoader());
        this.poster = in.readParcelable(Poster.class.getClassLoader());
        this.logo = in.readParcelable(Logo.class.getClassLoader());
        this.clearart = in.readParcelable(Clearart.class.getClassLoader());
        this.banner = in.readParcelable(Banner.class.getClassLoader());
        this.thumb = in.readParcelable(Thumb.class.getClassLoader());
        this.headshot = in.readParcelable(HeadShot.class.getClassLoader());
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
