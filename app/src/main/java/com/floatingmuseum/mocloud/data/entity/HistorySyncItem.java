package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/3/2.
 */

public class HistorySyncItem {
    private List<MovieHistorySyncItem> movies;
    private List<ShowsHistorySyncItem> shows;
    private List<SeasonsHistorySyncItem> seasons;
    private List<EpisodesHistorySyncItem> episodes;

    public List<MovieHistorySyncItem> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieHistorySyncItem> movies) {
        this.movies = movies;
    }

    public List<ShowsHistorySyncItem> getShows() {
        return shows;
    }

    public void setShows(List<ShowsHistorySyncItem> shows) {
        this.shows = shows;
    }

    public List<SeasonsHistorySyncItem> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonsHistorySyncItem> seasons) {
        this.seasons = seasons;
    }

    public List<EpisodesHistorySyncItem> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodesHistorySyncItem> episodes) {
        this.episodes = episodes;
    }
}
