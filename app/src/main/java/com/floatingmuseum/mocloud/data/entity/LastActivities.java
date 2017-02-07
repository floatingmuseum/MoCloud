package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/7.
 */

public class LastActivities {

    private String all;
    private Movies movies;
    private Episodes episodes;
    private Shows shows;
    private Seasons seasons;
    private Comments comments;
    private Lists lists;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    public Episodes getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Episodes episodes) {
        this.episodes = episodes;
    }

    public Shows getShows() {
        return shows;
    }

    public void setShows(Shows shows) {
        this.shows = shows;
    }

    public Seasons getSeasons() {
        return seasons;
    }

    public void setSeasons(Seasons seasons) {
        this.seasons = seasons;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Lists getLists() {
        return lists;
    }

    public void setLists(Lists lists) {
        this.lists = lists;
    }

    public static class Movies {
        private String watched_at;
        private String collected_at;
        private String rated_at;
        private String watchlisted_at;
        private String commented_at;
        private String paused_at;
        private String hidden_at;

        public String getWatched_at() {
            return watched_at;
        }

        public void setWatched_at(String watched_at) {
            this.watched_at = watched_at;
        }

        public String getCollected_at() {
            return collected_at;
        }

        public void setCollected_at(String collected_at) {
            this.collected_at = collected_at;
        }

        public String getRated_at() {
            return rated_at;
        }

        public void setRated_at(String rated_at) {
            this.rated_at = rated_at;
        }

        public String getWatchlisted_at() {
            return watchlisted_at;
        }

        public void setWatchlisted_at(String watchlisted_at) {
            this.watchlisted_at = watchlisted_at;
        }

        public String getCommented_at() {
            return commented_at;
        }

        public void setCommented_at(String commented_at) {
            this.commented_at = commented_at;
        }

        public String getPaused_at() {
            return paused_at;
        }

        public void setPaused_at(String paused_at) {
            this.paused_at = paused_at;
        }

        public String getHidden_at() {
            return hidden_at;
        }

        public void setHidden_at(String hidden_at) {
            this.hidden_at = hidden_at;
        }
    }

    public static class Episodes {
        private String watched_at;
        private String collected_at;
        private String rated_at;
        private String watchlisted_at;
        private String commented_at;
        private String paused_at;

        public String getWatched_at() {
            return watched_at;
        }

        public void setWatched_at(String watched_at) {
            this.watched_at = watched_at;
        }

        public String getCollected_at() {
            return collected_at;
        }

        public void setCollected_at(String collected_at) {
            this.collected_at = collected_at;
        }

        public String getRated_at() {
            return rated_at;
        }

        public void setRated_at(String rated_at) {
            this.rated_at = rated_at;
        }

        public String getWatchlisted_at() {
            return watchlisted_at;
        }

        public void setWatchlisted_at(String watchlisted_at) {
            this.watchlisted_at = watchlisted_at;
        }

        public String getCommented_at() {
            return commented_at;
        }

        public void setCommented_at(String commented_at) {
            this.commented_at = commented_at;
        }

        public String getPaused_at() {
            return paused_at;
        }

        public void setPaused_at(String paused_at) {
            this.paused_at = paused_at;
        }
    }

    public static class Shows {
        private String rated_at;
        private String watchlisted_at;
        private String commented_at;
        private String hidden_at;

        public String getRated_at() {
            return rated_at;
        }

        public void setRated_at(String rated_at) {
            this.rated_at = rated_at;
        }

        public String getWatchlisted_at() {
            return watchlisted_at;
        }

        public void setWatchlisted_at(String watchlisted_at) {
            this.watchlisted_at = watchlisted_at;
        }

        public String getCommented_at() {
            return commented_at;
        }

        public void setCommented_at(String commented_at) {
            this.commented_at = commented_at;
        }

        public String getHidden_at() {
            return hidden_at;
        }

        public void setHidden_at(String hidden_at) {
            this.hidden_at = hidden_at;
        }
    }

    public static class Seasons {
        private String rated_at;
        private String watchlisted_at;
        private String commented_at;
        private String hidden_at;

        public String getRated_at() {
            return rated_at;
        }

        public void setRated_at(String rated_at) {
            this.rated_at = rated_at;
        }

        public String getWatchlisted_at() {
            return watchlisted_at;
        }

        public void setWatchlisted_at(String watchlisted_at) {
            this.watchlisted_at = watchlisted_at;
        }

        public String getCommented_at() {
            return commented_at;
        }

        public void setCommented_at(String commented_at) {
            this.commented_at = commented_at;
        }

        public String getHidden_at() {
            return hidden_at;
        }

        public void setHidden_at(String hidden_at) {
            this.hidden_at = hidden_at;
        }
    }

    public static class Comments {
        private String liked_at;

        public String getLiked_at() {
            return liked_at;
        }

        public void setLiked_at(String liked_at) {
            this.liked_at = liked_at;
        }
    }

    public static class Lists {
        private String liked_at;
        private String updated_at;
        private String commented_at;

        public String getLiked_at() {
            return liked_at;
        }

        public void setLiked_at(String liked_at) {
            this.liked_at = liked_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCommented_at() {
            return commented_at;
        }

        public void setCommented_at(String commented_at) {
            this.commented_at = commented_at;
        }
    }
}
