package com.example.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView=findViewById(R.id.playerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
        playerView.setPlayer(exoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,getString(R.string.app_name));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse("https://r4---sn-ci5gup-cvhz.googlevideo.com/videoplayback?expire=1616635173&ei=xZBbYMfUCJLQgwPi2p-gCg&ip=91.149.203.9&id=o-ANUke-_q0Cishk03PnVli5Uz-WHagZWfXVYYJRqO9z12&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=GbcNVBX4rV1n70o_LzWYN5oF&gir=yes&clen=29088647&ratebypass=yes&dur=404.979&lmt=1580632917454574&fvip=4&fexp=24001374,24007246&c=WEB&txp=5531432&n=niUlxh9xup0zc5QCdjisx&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAON5zdp8TuEUIWgKOl5TIFVy9jEoIPGsMizRAXskZ3XEAiEAv1SIY8vFeTbCir1ELdGMiyvClY6Jlp0xxxRxqZpc9RU%3D&redirect_counter=1&rm=sn-p5qk67z&req_id=6026e9087076a3ee&cms_redirect=yes&ipbypass=yes&mh=ZQ&mip=202.8.117.156&mm=31&mn=sn-ci5gup-cvhz&ms=au&mt=1616617205&mv=u&mvi=4&pl=22&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgOIPeu16XgWLK4WILXgwccYI1C3ztzpnSGcMAQ1h1jicCIBhcWVfJtja-ramK8GuBtRDeoSlbLK-CdLvLpNqdZnWC"));
        exoPlayer.prepare(videoSource);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        exoPlayer.release();
        exoPlayer = null;
    }
}