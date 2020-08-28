package com.istrides.inztastudy;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoView extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    String id;
    YouTubePlayerView youTubeView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);


        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize("AIzaSyCchrXjIXnvU-WQj5qZ3PQWqMl4tMuFLm0", this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


        youTubePlayer.loadVideo(getIntent().getStringExtra("VIDID"));
        youTubePlayer.setFullscreen(true);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


        youTubeInitializationResult.getErrorDialog(this, 1);
        Log.i("skandha", "sasti");

    }
}
