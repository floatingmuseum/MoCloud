package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.os.Bundle;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2017/2/21.
 */

public class YoutubePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

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
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Logger.d("Youtube播放onInitializationSuccess");
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.cueVideo(id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Logger.d("Youtube播放onInitializationFailure..." + youTubeInitializationResult.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (youTubePlayer != null) {
            youTubePlayer.release();
        }
    }
}
