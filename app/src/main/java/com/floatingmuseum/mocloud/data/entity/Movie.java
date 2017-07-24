package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/18.
 */
public class Movie implements Parcelable{
    private String title;
    private int year;
    private Ids ids;
    private MovieImage images;
    private ArtImage image;
    private String tagline;
    private String overview;
    private String released;
    private int runtime;
    private String updated_at;
    private String trailer;
    private String homepage;
    private double rating;
    private int votes;
    private String language;
    private String certification;
    private List<String> available_translations;
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public MovieImage getImages() {
        return images;
    }

    public void setImages(MovieImage images) {
        this.images = images;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public List<String> getAvailable_translations() {
        return available_translations;
    }

    public void setAvailable_translations(List<String> available_translations) {
        this.available_translations = available_translations;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public ArtImage getImage() {
        return image;
    }

    public void setImage(ArtImage image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.year);
        dest.writeParcelable(this.ids, flags);
        dest.writeParcelable(this.images, flags);
        dest.writeParcelable(this.image, flags);
        dest.writeString(this.tagline);
        dest.writeString(this.overview);
        dest.writeString(this.released);
        dest.writeInt(this.runtime);
        dest.writeString(this.updated_at);
        dest.writeString(this.trailer);
        dest.writeString(this.homepage);
        dest.writeDouble(this.rating);
        dest.writeInt(this.votes);
        dest.writeString(this.language);
        dest.writeString(this.certification);
        dest.writeStringList(this.available_translations);
        dest.writeStringList(this.genres);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", ids=" + ids +
                ", images=" + images +
                ", image=" + image +
                ", tagline='" + tagline + '\'' +
                ", overview='" + overview + '\'' +
                ", released='" + released + '\'' +
                ", runtime=" + runtime +
                ", updated_at='" + updated_at + '\'' +
                ", trailer='" + trailer + '\'' +
                ", homepage='" + homepage + '\'' +
                ", rating=" + rating +
                ", votes=" + votes +
                ", language='" + language + '\'' +
                ", certification='" + certification + '\'' +
                ", available_translations=" + available_translations +
                ", genres=" + genres +
                '}';
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.year = in.readInt();
        this.ids = in.readParcelable(Ids.class.getClassLoader());
        this.images = in.readParcelable(MovieImage.class.getClassLoader());
        this.image = in.readParcelable(ArtImage.class.getClassLoader());
        this.tagline = in.readString();
        this.overview = in.readString();
        this.released = in.readString();
        this.runtime = in.readInt();
        this.updated_at = in.readString();
        this.trailer = in.readString();
        this.homepage = in.readString();
        this.rating = in.readDouble();
        this.votes = in.readInt();
        this.language = in.readString();
        this.certification = in.readString();
        this.available_translations = in.createStringArrayList();
        this.genres = in.createStringArrayList();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
