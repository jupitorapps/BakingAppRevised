package com.example.bakingappproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingappproject.DataModels.StepsDataModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    private static final String TAG = "TAGG";
    SimpleExoPlayer player;
    String videoUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        int clicked_position = intent.getIntExtra("clicked_position",0);
        ArrayList<StepsDataModel> stepsDataModelArrayList = intent.getParcelableArrayListExtra("steps_data");
        if (stepsDataModelArrayList != null){
             videoUrl = stepsDataModelArrayList.get(clicked_position).getVideoURL();
        }


        PlayerView playerView = findViewById(R.id.video_player_view);

        try {
           // PlayerView playerView = findViewById(R.id.video_player_view);

            player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());

            playerView.setPlayer(player);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),
                    Util.getUserAgent(getApplicationContext(), "Baking App"));

            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(videoUrl));

            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
      //  Log.d(TAG, "onPause: Player Released");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
