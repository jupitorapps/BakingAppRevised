package com.example.bakingappproject;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.DataModels.BakingReceipeDataModel;
import com.example.bakingappproject.DataModels.ReceipeAdapterClickListener;
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
import java.util.Objects;

public class VideoPlayerActivity extends AppCompatActivity implements ReceipeAdapterClickListener {

    private static final String TAG = "TAGG";
    SimpleExoPlayer player;
    String clicked_video_url = "";
    int total_videos = 0;

    RecyclerView stepsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);

        stepsRecyclerView = findViewById(R.id.rv_steps_player_screen);

        Intent intent = getIntent();
        int clicked_position = intent.getIntExtra("clicked_position", 0);

        ArrayList<StepsDataModel> stepsDataModelArrayList = intent.getParcelableArrayListExtra("steps_data");

        if (stepsDataModelArrayList != null) {
            clicked_video_url = stepsDataModelArrayList.get(clicked_position).getVideoURL();
            total_videos = stepsDataModelArrayList.size();
        }

        PlayerView playerView = findViewById(R.id.video_player_view);

        TrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);

        playerView.setPlayer(player);

        if (stepsDataModelArrayList != null) {
            playVideo(clicked_position,stepsDataModelArrayList);
        }

        setupStepsList(stepsDataModelArrayList);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void playVideo(int position, ArrayList<StepsDataModel> stepsDataModelArrayList){

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),
                Util.getUserAgent(getApplicationContext(), "Baking App"));

        MediaSource clicked_video_source = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(stepsDataModelArrayList.get(position).getVideoURL()));

        player.prepare(clicked_video_source);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
        player.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void setupStepsList(ArrayList<StepsDataModel> stepsDataModel) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        StepsAdapter stepsAdapter = new StepsAdapter(this);
        stepsRecyclerView.hasFixedSize();
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.loadData(stepsDataModel);
    }


    @Override
    public void onItemClicks(BakingReceipeDataModel bakingReceipeDataModel) {

    }

    @Override
    public void onStepItemClickListener(int position, ArrayList<StepsDataModel> stepsDataModelArrayList) {

        if (stepsDataModelArrayList.get(position).getVideoURL().isEmpty()){
            Toast.makeText(this, getString(R.string.video_not_available), Toast.LENGTH_SHORT).show();
            return;
        }

        playVideo(position,stepsDataModelArrayList);

    }
}
