package com.example.bakingappproject;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingappproject.DataModels.StepsDataModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioFocusManager;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection.Factory;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    private static final String TAG = "TAGG";
    SimpleExoPlayer player;
    String clicked_video_url = "";
    int total_videos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        int clicked_position = intent.getIntExtra("clicked_position",0);

        ArrayList<StepsDataModel> stepsDataModelArrayList = intent.getParcelableArrayListExtra("steps_data");

        if (stepsDataModelArrayList != null){
             clicked_video_url = stepsDataModelArrayList.get(clicked_position).getVideoURL();
            total_videos = stepsDataModelArrayList.size();
        }


        PlayerView playerView = findViewById(R.id.video_player_view);


          //  PlayerView playerView = findViewById(R.id.video_player_view);

            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(),trackSelector);

            playerView.setPlayer(player);


            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),
                    Util.getUserAgent(getApplicationContext(), "Baking App"));

//            MediaSource clicked_video_source = new ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(Uri.parse(clicked_video_url));
//            MediaSource clicked_video_source2 = new ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(Uri.parse(stepsDataModelArrayList.get(clicked_position+1).getVideoURL()));



            ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
            //concatenatingMediaSource.addMediaSource(clicked_video_source);

            for (int i=0; i<= total_videos-1; i++){

                MediaSource video_source = new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(stepsDataModelArrayList.get(i).getVideoURL()));
                concatenatingMediaSource.addMediaSource(video_source);

            //    Log.d(TAG, "onCreate: Video Source: "+video_source);
              //  Log.d(TAG, "onCreate: i is: "+i);


            }

        Log.d(TAG, "onCreate: concanating media source size: "+concatenatingMediaSource.getSize());

        player.prepare(concatenatingMediaSource);
        player.setPlayWhenReady(true);
        
        player.addListener(new EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d(TAG, "onPlayerStateChanged: "+playbackState);

            }
        });


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
