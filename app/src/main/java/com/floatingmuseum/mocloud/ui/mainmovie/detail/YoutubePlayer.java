package com.floatingmuseum.mocloud.ui.mainmovie.detail;



import android.os.Bundle;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2017/2/21.
 */

public class YoutubePlayer extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnInitializedListener {

    @BindView(R.id.youtube_view)
    YouTubePlayerView youtubeView;
    //    @BindView(R.id.thumbnail)
//    YouTubeThumbnailView thumbnail;
    private YouTubePlayer youTubePlayer;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        getVideoId(url);
        youtubeView.initialize(BuildConfig.YoutubeKey, this);
    }

    private void getVideoId(String url) {
//        http://youtube.com/watch?v=slib5X6KFXo"
        int subStart = url.indexOf("=");
        id = url.substring(subStart + 1);
        Logger.d("Youtube播放...id:" + id + "...url:" + url);
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return (YouTubePlayerView) findViewById(R.id.youtube_view);
        return youtubeView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(id);
        }
    }
}
