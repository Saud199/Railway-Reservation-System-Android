package com.example.abdulahad.railwaysystem;

import android.app.Dialog;
import android.graphics.PixelFormat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity {


    Button b;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);


        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.yplayer);
        b=(Button)findViewById(R.id.btn);



        y=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("biWuMLdw5Fs");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


            }
        };


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                youTubePlayerView.initialize("AIzaSyDuw7j_hVc4jhMtoTTIPtpduxyihujpIUE",y);
            }
        });


    }



            //AIzaSyDuw7j_hVc4jhMtoTTIPtpduxyihujpIUE

        //AIzaSyBVrTE3vWXP5QDsDTaR0c6D2tp_vi4VZ4E

    }



 /*
        final Dialog dialog = new Dialog(YoutubeVideo.this) ;
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Video...");

        youTubePlayerView=(YouTubePlayerView)dialog.findViewById(R.id.zplayer);
        youTubePlayerView.initialize("AIzaSyBVrTE3vWXP5QDsDTaR0c6D2tp_vi4VZ4E",y);

        TextView text = (TextView) dialog.findViewById(R.id.dtext);
        text.setText("Video Tutorial");

        y=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("ZRFmZwvGtRA");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        Button dialogButton = (Button) dialog.findViewById(R.id.ply_video);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();*/


