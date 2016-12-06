package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class TmdbStaff {

    private boolean adult;
    private String biography;
    private String birthday;
    private String deathday;
    private int gender;
    private String homepage;
    private int id;
    private String imdb_id;
    private String name;
    private String place_of_birth;
    private double popularity;
    private String profile_path;
    private Credits credits;
    private List<String> also_known_as;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(List<String> also_known_as) {
        this.also_known_as = also_known_as;
    }

    public static class Credits {

        private List<Cast> cast;
        private List<Crew> crew;

        public List<Cast> getCast() {
            return cast;
        }

        public void setCast(List<Cast> cast) {
            this.cast = cast;
        }

        public List<Crew> getCrew() {
            return crew;
        }

        public void setCrew(List<Crew> crew) {
            this.crew = crew;
        }

        public static class Cast implements Parcelable{
            private boolean adult;
            private String character;
            private String credit_id;
            private int id;
            private String original_title;
            private String poster_path;
            private String release_date;
            private String title;

            public boolean isAdult() {
                return adult;
            }

            public void setAdult(boolean adult) {
                this.adult = adult;
            }

            public String getCharacter() {
                return character;
            }

            public void setCharacter(String character) {
                this.character = character;
            }

            public String getCredit_id() {
                return credit_id;
            }

            public void setCredit_id(String credit_id) {
                this.credit_id = credit_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOriginal_title() {
                return original_title;
            }

            public void setOriginal_title(String original_title) {
                this.original_title = original_title;
            }

            public String getPoster_path() {
                return poster_path;
            }

            public void setPoster_path(String poster_path) {
                this.poster_path = poster_path;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
                dest.writeString(this.character);
                dest.writeString(this.credit_id);
                dest.writeInt(this.id);
                dest.writeString(this.original_title);
                dest.writeString(this.poster_path);
                dest.writeString(this.release_date);
                dest.writeString(this.title);
            }

            public Cast() {
            }

            protected Cast(Parcel in) {
                this.adult = in.readByte() != 0;
                this.character = in.readString();
                this.credit_id = in.readString();
                this.id = in.readInt();
                this.original_title = in.readString();
                this.poster_path = in.readString();
                this.release_date = in.readString();
                this.title = in.readString();
            }

            public static final Creator<Cast> CREATOR = new Creator<Cast>() {
                @Override
                public Cast createFromParcel(Parcel source) {
                    return new Cast(source);
                }

                @Override
                public Cast[] newArray(int size) {
                    return new Cast[size];
                }
            };
        }

        public static class Crew {
            private boolean adult;
            private String credit_id;
            private String department;
            private int id;
            private String job;
            private String original_title;
            private String poster_path;
            private String release_date;
            private String title;

            public boolean isAdult() {
                return adult;
            }

            public void setAdult(boolean adult) {
                this.adult = adult;
            }

            public String getCredit_id() {
                return credit_id;
            }

            public void setCredit_id(String credit_id) {
                this.credit_id = credit_id;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getOriginal_title() {
                return original_title;
            }

            public void setOriginal_title(String original_title) {
                this.original_title = original_title;
            }

            public String getPoster_path() {
                return poster_path;
            }

            public void setPoster_path(String poster_path) {
                this.poster_path = poster_path;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
