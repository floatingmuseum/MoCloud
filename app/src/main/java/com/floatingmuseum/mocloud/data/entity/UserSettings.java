package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public class UserSettings extends User {

    private Account account;

    private Connections connections;

    private SharingText sharing_text;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Connections getConnections() {
        return connections;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    public SharingText getSharing_text() {
        return sharing_text;
    }

    public void setSharing_text(SharingText sharing_text) {
        this.sharing_text = sharing_text;
    }

    public static class Account {
        private String timezone;
        private boolean time_24hr;
        private String cover_image;

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public boolean isTime_24hr() {
            return time_24hr;
        }

        public void setTime_24hr(boolean time_24hr) {
            this.time_24hr = time_24hr;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }
    }

    public static class Connections {
        private boolean facebook;
        private boolean twitter;
        private boolean google;
        private boolean tumblr;
        private boolean medium;
        private boolean slack;

        public boolean isFacebook() {
            return facebook;
        }

        public void setFacebook(boolean facebook) {
            this.facebook = facebook;
        }

        public boolean isTwitter() {
            return twitter;
        }

        public void setTwitter(boolean twitter) {
            this.twitter = twitter;
        }

        public boolean isGoogle() {
            return google;
        }

        public void setGoogle(boolean google) {
            this.google = google;
        }

        public boolean isTumblr() {
            return tumblr;
        }

        public void setTumblr(boolean tumblr) {
            this.tumblr = tumblr;
        }

        public boolean isMedium() {
            return medium;
        }

        public void setMedium(boolean medium) {
            this.medium = medium;
        }

        public boolean isSlack() {
            return slack;
        }

        public void setSlack(boolean slack) {
            this.slack = slack;
        }
    }

    public static class SharingText {
        private String watching;
        private String watched;

        public String getWatching() {
            return watching;
        }

        public void setWatching(String watching) {
            this.watching = watching;
        }

        public String getWatched() {
            return watched;
        }

        public void setWatched(String watched) {
            this.watched = watched;
        }
    }
}
