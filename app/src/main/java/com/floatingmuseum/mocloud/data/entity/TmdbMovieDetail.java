package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class TmdbMovieDetail implements Parcelable {

    private boolean fromStaffWorks;
    private boolean isTraktItem;
    private boolean adult;
    private String backdrop_path;
    private BelongsToCollection belongs_to_collection;
    private int budget;
    private String homepage;
    private int id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private long revenue;
    private int runtime;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
    private double traktRatings;
    private int traktVotes;
    private TmdbStaff.Credits credits;

    private TmdbMovieImage image;

    private List<Genres> genres;

    private List<ProductionCompanies> production_companies;

    private List<ProductionCountries> production_countries;

    private List<SpokenLanguages> spoken_languages;

    public boolean isFromStaffWorks() {
        return fromStaffWorks;
    }

    public void setFromStaffWorks(boolean fromStaffWorks) {
        this.fromStaffWorks = fromStaffWorks;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public BelongsToCollection getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public void setBelongs_to_collection(BelongsToCollection belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
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

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
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

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<ProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompanies> production_companies) {
        this.production_companies = production_companies;
    }

    public List<ProductionCountries> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<ProductionCountries> production_countries) {
        this.production_countries = production_countries;
    }

    public List<SpokenLanguages> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<SpokenLanguages> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public TmdbStaff.Credits getCredits() {
        return credits;
    }

    public void setCredits(TmdbStaff.Credits credits) {
        this.credits = credits;
    }

    public boolean isTraktItem() {
        return isTraktItem;
    }

    public void setTraktItem(boolean traktItem) {
        isTraktItem = traktItem;
    }

    public int getTraktVotes() {
        return traktVotes;
    }

    public void setTraktVotes(int traktVotes) {
        this.traktVotes = traktVotes;
    }

    public double getTraktRatings() {
        return traktRatings;
    }

    public void setTraktRatings(double traktRatings) {
        this.traktRatings = traktRatings;
    }

    public TmdbMovieImage getImage() {
        return image;
    }

    public void setImage(TmdbMovieImage image) {
        this.image = image;
    }

    public static class Genres implements Parcelable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        public Genres() {
        }

        protected Genres(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
        }

        public static final Creator<Genres> CREATOR = new Creator<Genres>() {
            @Override
            public Genres createFromParcel(Parcel source) {
                return new Genres(source);
            }

            @Override
            public Genres[] newArray(int size) {
                return new Genres[size];
            }
        };
    }

    public static class ProductionCompanies implements Parcelable {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.id);
        }

        public ProductionCompanies() {
        }

        protected ProductionCompanies(Parcel in) {
            this.name = in.readString();
            this.id = in.readInt();
        }

        public static final Creator<ProductionCompanies> CREATOR = new Creator<ProductionCompanies>() {
            @Override
            public ProductionCompanies createFromParcel(Parcel source) {
                return new ProductionCompanies(source);
            }

            @Override
            public ProductionCompanies[] newArray(int size) {
                return new ProductionCompanies[size];
            }
        };
    }

    public static class ProductionCountries implements Parcelable {
        private String iso_3166_1;
        private String name;

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.iso_3166_1);
            dest.writeString(this.name);
        }

        public ProductionCountries() {
        }

        protected ProductionCountries(Parcel in) {
            this.iso_3166_1 = in.readString();
            this.name = in.readString();
        }

        public static final Creator<ProductionCountries> CREATOR = new Creator<ProductionCountries>() {
            @Override
            public ProductionCountries createFromParcel(Parcel source) {
                return new ProductionCountries(source);
            }

            @Override
            public ProductionCountries[] newArray(int size) {
                return new ProductionCountries[size];
            }
        };
    }

    public static class SpokenLanguages implements Parcelable {
        private String iso_639_1;
        private String name;

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.iso_639_1);
            dest.writeString(this.name);
        }

        public SpokenLanguages() {
        }

        protected SpokenLanguages(Parcel in) {
            this.iso_639_1 = in.readString();
            this.name = in.readString();
        }

        public static final Creator<SpokenLanguages> CREATOR = new Creator<SpokenLanguages>() {
            @Override
            public SpokenLanguages createFromParcel(Parcel source) {
                return new SpokenLanguages(source);
            }

            @Override
            public SpokenLanguages[] newArray(int size) {
                return new SpokenLanguages[size];
            }
        };
    }

    public static class BelongsToCollection implements Parcelable {

        private int id;
        private String name;
        private String poster_path;
        private String backdrop_path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.poster_path);
            dest.writeString(this.backdrop_path);
        }

        public BelongsToCollection() {
        }

        protected BelongsToCollection(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.poster_path = in.readString();
            this.backdrop_path = in.readString();
        }

        public static final Creator<BelongsToCollection> CREATOR = new Creator<BelongsToCollection>() {
            @Override
            public BelongsToCollection createFromParcel(Parcel source) {
                return new BelongsToCollection(source);
            }

            @Override
            public BelongsToCollection[] newArray(int size) {
                return new BelongsToCollection[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.fromStaffWorks ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isTraktItem ? (byte) 1 : (byte) 0);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdrop_path);
        dest.writeParcelable(this.belongs_to_collection, flags);
        dest.writeInt(this.budget);
        dest.writeString(this.homepage);
        dest.writeInt(this.id);
        dest.writeString(this.imdb_id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeDouble(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeLong(this.revenue);
        dest.writeInt(this.runtime);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
        dest.writeString(this.title);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
        dest.writeDouble(this.traktRatings);
        dest.writeInt(this.traktVotes);
        dest.writeParcelable(this.credits, flags);
        dest.writeParcelable(this.image, flags);
        dest.writeTypedList(this.genres);
        dest.writeTypedList(this.production_companies);
        dest.writeTypedList(this.production_countries);
        dest.writeTypedList(this.spoken_languages);
    }

    public TmdbMovieDetail() {
    }

    protected TmdbMovieDetail(Parcel in) {
        this.fromStaffWorks = in.readByte() != 0;
        this.isTraktItem = in.readByte() != 0;
        this.adult = in.readByte() != 0;
        this.backdrop_path = in.readString();
        this.belongs_to_collection = in.readParcelable(BelongsToCollection.class.getClassLoader());
        this.budget = in.readInt();
        this.homepage = in.readString();
        this.id = in.readInt();
        this.imdb_id = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.revenue = in.readLong();
        this.runtime = in.readInt();
        this.status = in.readString();
        this.tagline = in.readString();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
        this.traktRatings = in.readDouble();
        this.traktVotes = in.readInt();
        this.credits = in.readParcelable(TmdbStaff.Credits.class.getClassLoader());
        this.image = in.readParcelable(TmdbMovieImage.class.getClassLoader());
        this.genres = in.createTypedArrayList(Genres.CREATOR);
        this.production_companies = in.createTypedArrayList(ProductionCompanies.CREATOR);
        this.production_countries = in.createTypedArrayList(ProductionCountries.CREATOR);
        this.spoken_languages = in.createTypedArrayList(SpokenLanguages.CREATOR);
    }

    public static final Creator<TmdbMovieDetail> CREATOR = new Creator<TmdbMovieDetail>() {
        @Override
        public TmdbMovieDetail createFromParcel(Parcel source) {
            return new TmdbMovieDetail(source);
        }

        @Override
        public TmdbMovieDetail[] newArray(int size) {
            return new TmdbMovieDetail[size];
        }
    };
}
