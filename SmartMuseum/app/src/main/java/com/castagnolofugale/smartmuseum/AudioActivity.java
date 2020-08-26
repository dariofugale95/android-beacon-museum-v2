package com.castagnolofugale.smartmuseum;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity implements OnPreparedListener, MediaController.MediaPlayerControl{


    public static final String AUDIO_FILE_NAME = "audioFileName";

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private String audioFile;
    private String audioTitle;
    private String urlImage;

    private Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Audio Player");
        }
        setContentView(R.layout.audio_artwork_player);
        audioFile = this.getIntent().getStringExtra("AudioUrl");
        audioTitle=this.getIntent().getStringExtra("AudioTitle");
        urlImage=this.getIntent().getStringExtra("Image");
        if(audioTitle!=null){
            ((TextView)findViewById(R.id.audioTitle)).setText(audioTitle);
        }
        else{
            ((TextView)findViewById(R.id.audioTitle)).setText("Title not available");
        }

        Picasso.get().load(urlImage).fit()
                .centerCrop().error(R.mipmap.no_image).into((ImageView) findViewById(R.id.audioImageView));
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mediaController = new MediaController(this);

        try {
            mediaPlayer.setDataSource(audioFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("[MediaPlayer]", "IO Error" + audioFile + " cannot open resource.", e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mediaController.show(1000);
        return false;
    }

    //MediaPlayerControl
    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("[MediaPlayer]", "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.viewAudio));

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}