package app.example.baking.bakingapp.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.model.Recipe;
import app.example.baking.bakingapp.model.Step;

// https://github.com/udacity/AdvancedAndroid_ClassicalMusicQuiz/tree/TMED.04-Solution-AddMediaSession
// https://stackoverflow.com/questions/45481775/exoplayer-restore-state-when-resumed/45482017#45482017
public class StepOverviewFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = StepOverviewFragment.class.getSimpleName();
    public static final String PLAYER_POSITION = "player_position";
    public static final String PLAY_WHEN_READY = "play_when_ready";

    private String description;
    private String recipeThumbnailUrl;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private String recipeUrl;
    private Step step;
    private ImageView mThumbnail;
    private static Bundle mBundle;

    private boolean isPlayWhenReady;
    private long playerPosition;

    public StepOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_overview, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);
        mThumbnail = rootView.findViewById(R.id.iv_step_overview_thumbnail);


        if (savedInstanceState != null) {
            Log.e(TAG, "savedInstance ");

            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            isPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);

            step = savedInstanceState.getParcelable("step");
            if (step != null)
                setVideoOrImage(step);

        } else {
            Log.e(TAG, "No saved instance ");

            step = getActivity().getIntent().getParcelableExtra("stepObject");
            if (step != null)
                setVideoOrImage(step);

            //Get Steps for Tablet Layout
            if (mBundle != null) {
                step = mBundle.getParcelable("bundleStep");
                Log.e(TAG, "bundle step " + step);
                if (step != null)
                    setVideoOrImage(step);
            }
        }

        TextView mName = rootView.findViewById(R.id.tv_step_overview_description);
        mName.setText(description);

        Recipe recipe = getActivity().getIntent().getParcelableExtra("recipeObject");
        String  title = recipe.getName();
        getActivity().setTitle(title);
        return rootView;
    }

    private void setVideoOrImage(Step step) {
        description = step.getDescription();
        recipeThumbnailUrl = step.getThumbnailURL();
        recipeUrl = step.getVideoURL();

        if (!step.getVideoURL().equals("")) {
            mThumbnail.setVisibility(View.GONE);
            initializeMediaSession();
            initializePlayer(Uri.parse(recipeUrl));

        } else if (!step.getThumbnailURL().equals("")) {
            mPlayerView.setVisibility(View.GONE);

            Picasso.Builder builder = new Picasso.Builder(getContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                    Log.e(TAG, "image error ");
                    mThumbnail.setVisibility(View.GONE);
                }
            });
            builder.build().load(recipeThumbnailUrl).into(mThumbnail);
        } else {
            mPlayerView.setVisibility(View.GONE);
            mThumbnail.setVisibility(View.GONE);
            // mThumbnail.setImageResource(R.drawable.fallback);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", step);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(PLAY_WHEN_READY, isPlayWhenReady);
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);
            mExoPlayer.seekTo(playerPosition);
        }
    }

    // ExoPlayer Event Listeners
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            Log.e(TAG, "onPlayerStateChanged: PLAYING");
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            Log.e(TAG, "onPlayerStateChanged: PAUSED");
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            isPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!step.getVideoURL().equals("")) {
            releasePlayer();
            mMediaSession.setActive(false);
        }
    }

    public static void putItemT(Bundle bundle) {
        mBundle = bundle;
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer(Uri.parse(recipeUrl));
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(recipeUrl));
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }


}
