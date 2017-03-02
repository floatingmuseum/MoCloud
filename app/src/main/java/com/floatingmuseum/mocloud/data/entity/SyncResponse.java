package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/3/2.
 */

public class SyncResponse {

    private Added added;
    private Deleted deleted;
    private NotFound not_found;

    public Added getAdded() {
        return added;
    }

    public void setAdded(Added added) {
        this.added = added;
    }

    public NotFound getNot_found() {
        return not_found;
    }

    public void setNot_found(NotFound not_found) {
        this.not_found = not_found;
    }

    public Deleted getDeleted() {
        return deleted;
    }

    public void setDeleted(Deleted deleted) {
        this.deleted = deleted;
    }

    public static class Added {
        private int movies;
        private int episodes;

        public int getMovies() {
            return movies;
        }

        public void setMovies(int movies) {
            this.movies = movies;
        }

        public int getEpisodes() {
            return episodes;
        }

        public void setEpisodes(int episodes) {
            this.episodes = episodes;
        }
    }

    public static class Deleted {
        private int movies;
        private int episodes;

        public int getMovies() {
            return movies;
        }

        public void setMovies(int movies) {
            this.movies = movies;
        }

        public int getEpisodes() {
            return episodes;
        }

        public void setEpisodes(int episodes) {
            this.episodes = episodes;
        }
    }

    public static class NotFound {

        private List<Movies> movies;
        private List<?> shows;
        private List<?> seasons;
        private List<?> episodes;

        public List<Movies> getMovies() {
            return movies;
        }

        public void setMovies(List<Movies> movies) {
            this.movies = movies;
        }

        public List<?> getShows() {
            return shows;
        }

        public void setShows(List<?> shows) {
            this.shows = shows;
        }

        public List<?> getSeasons() {
            return seasons;
        }

        public void setSeasons(List<?> seasons) {
            this.seasons = seasons;
        }

        public List<?> getEpisodes() {
            return episodes;
        }

        public void setEpisodes(List<?> episodes) {
            this.episodes = episodes;
        }

        public static class Movies {

            private Ids ids;

            public Ids getIds() {
                return ids;
            }

            public void setIds(Ids ids) {
                this.ids = ids;
            }

            public static class Ids {
                private String imdb;

                public String getImdb() {
                    return imdb;
                }

                public void setImdb(String imdb) {
                    this.imdb = imdb;
                }
            }
        }
    }
}
