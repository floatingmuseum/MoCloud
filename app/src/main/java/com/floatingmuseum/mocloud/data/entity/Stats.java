package com.floatingmuseum.mocloud.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Floatingmuseum on 2016/12/28.
 */

public class Stats {

    private MoviesCount movies;
    private ShowsCount shows;
    private SeasonsCount seasons;
    private EpisodesCount episodes;
    private NetworkCount network;
    private RatingsCount ratings;

    public MoviesCount getMovies() {
        return movies;
    }

    public void setMovies(MoviesCount movies) {
        this.movies = movies;
    }

    public ShowsCount getShows() {
        return shows;
    }

    public void setShows(ShowsCount shows) {
        this.shows = shows;
    }

    public SeasonsCount getSeasons() {
        return seasons;
    }

    public void setSeasons(SeasonsCount seasons) {
        this.seasons = seasons;
    }

    public EpisodesCount getEpisodes() {
        return episodes;
    }

    public void setEpisodes(EpisodesCount episodes) {
        this.episodes = episodes;
    }

    public NetworkCount getNetwork() {
        return network;
    }

    public void setNetwork(NetworkCount network) {
        this.network = network;
    }

    public RatingsCount getRatings() {
        return ratings;
    }

    public void setRatings(RatingsCount ratings) {
        this.ratings = ratings;
    }

    public static class MoviesCount {
        private int plays;
        private int watched;
        private int minutes;
        private int collected;
        private int ratings;
        private int comments;

        public int getPlays() {
            return plays;
        }

        public void setPlays(int plays) {
            this.plays = plays;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getCollected() {
            return collected;
        }

        public void setCollected(int collected) {
            this.collected = collected;
        }

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
            this.ratings = ratings;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }
    }

    public static class ShowsCount {
        private int watched;
        private int collected;
        private int ratings;
        private int comments;

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public int getCollected() {
            return collected;
        }

        public void setCollected(int collected) {
            this.collected = collected;
        }

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
            this.ratings = ratings;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }
    }

    public static class SeasonsCount {
        private int ratings;
        private int comments;

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
            this.ratings = ratings;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }
    }

    public static class EpisodesCount {
        private int plays;
        private int watched;
        private int minutes;
        private int collected;
        private int ratings;
        private int comments;

        public int getPlays() {
            return plays;
        }

        public void setPlays(int plays) {
            this.plays = plays;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getCollected() {
            return collected;
        }

        public void setCollected(int collected) {
            this.collected = collected;
        }

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
            this.ratings = ratings;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }
    }

    public static class NetworkCount {
        private int friends;
        private int followers;
        private int following;

        public int getFriends() {
            return friends;
        }

        public void setFriends(int friends) {
            this.friends = friends;
        }

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }
    }

    public static class RatingsCount {
        private int total;
        private DistributionCount distribution;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public DistributionCount getDistribution() {
            return distribution;
        }

        public void setDistribution(DistributionCount distribution) {
            this.distribution = distribution;
        }

        public static class DistributionCount {
            @SerializedName("1")
            private int value1;
            @SerializedName("2")
            private int value2;
            @SerializedName("3")
            private int value3;
            @SerializedName("4")
            private int value4;
            @SerializedName("5")
            private int value5;
            @SerializedName("6")
            private int value6;
            @SerializedName("7")
            private int value7;
            @SerializedName("8")
            private int value8;
            @SerializedName("9")
            private int value9;
            @SerializedName("10")
            private int value10;

            public int getValue1() {
                return value1;
            }

            public void setValue1(int value1) {
                this.value1 = value1;
            }

            public int getValue2() {
                return value2;
            }

            public void setValue2(int value2) {
                this.value2 = value2;
            }

            public int getValue3() {
                return value3;
            }

            public void setValue3(int value3) {
                this.value3 = value3;
            }

            public int getValue4() {
                return value4;
            }

            public void setValue4(int value4) {
                this.value4 = value4;
            }

            public int getValue5() {
                return value5;
            }

            public void setValue5(int value5) {
                this.value5 = value5;
            }

            public int getValue6() {
                return value6;
            }

            public void setValue6(int value6) {
                this.value6 = value6;
            }

            public int getValue7() {
                return value7;
            }

            public void setValue7(int value7) {
                this.value7 = value7;
            }

            public int getValue8() {
                return value8;
            }

            public void setValue8(int value8) {
                this.value8 = value8;
            }

            public int getValue9() {
                return value9;
            }

            public void setValue9(int value9) {
                this.value9 = value9;
            }

            public int getValue10() {
                return value10;
            }

            public void setValue10(int value10) {
                this.value10 = value10;
            }
        }
    }
}
