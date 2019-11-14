package com.example.abdulahad.railwaysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class UserAfterLogin extends AppCompatActivity {

    Button book_ticket , view_ticket , cancel_ticket , train_scedule , contact_us , give_feedback , logout, aboutUs, videoTutorial, updateInfo  ;
    ViewFlipper viewFlipper;

    private TextView tv , user_logername;

    int gallery_grid_Images[]={R.drawable.bg_image , R.drawable.bg_image2 , R.drawable.bg_image3 , R.drawable.bg_image4};


    public void onBackPressed() {
        Toast.makeText(UserAfterLogin.this,"Please Log out to go back",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_after_login);

        book_ticket = (Button)findViewById(R.id.id_user_bookticket);
        view_ticket = (Button)findViewById(R.id.id_user_viewtickets);
        cancel_ticket = (Button)findViewById(R.id.id_user_cancelticket);
        train_scedule = (Button)findViewById(R.id.id_user_viewTerain);
        contact_us = (Button)findViewById(R.id.id_user_contactus);
        give_feedback = (Button)findViewById(R.id.id_user_givefeedback);
        updateInfo = (Button) findViewById(R.id.id_user_update_info);
        aboutUs = (Button)findViewById(R.id.id_user_aboutus);
        videoTutorial = (Button)findViewById(R.id.id_user_video_tutorial);
        logout = (Button)findViewById(R.id.id_user_signoutbtn);

        viewFlipper = (ViewFlipper) findViewById(R.id.userflipper);

        tv = (TextView) this.findViewById(R.id.mywidgetuser);
        user_logername=(TextView)findViewById(R.id.logger_username);

        user_logername.setText("Logged in as : "+getUserName.userName);


        tv.setSelected(true);

        book_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_BookTicket.class);
                startActivity(i);

            }
        });




        view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_ViewAllTicket.class);
                startActivity(i);

            }
        });



        cancel_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_CancelTicket.class);
                startActivity(i);

            }
        });



        train_scedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_ViewScheduledTrain.class);
                startActivity(i);

            }
        });



        give_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_GiveFeedbacks.class);
                startActivity(i);

            }
        });



        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_ContactUs.class);
                startActivity(i);

            }
        });

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , User_UpdateInfo.class);
                startActivity(i);

            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserAfterLogin.this , User_AboutUs.class);
                startActivity(i);
            }
        });


        videoTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , YoutubeVideo.class);
                startActivity(i);


            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserAfterLogin.this , Login_activity.class);
                startActivity(i);

            }
        });


        for(int i=0; i<gallery_grid_Images.length; i++)
        {
            setFlipperImage(gallery_grid_Images[i]);
        }



    }
    void setFlipperImage(int res) {
        ImageView image = new ImageView(getApplicationContext());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
        viewFlipper.startFlipping();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.email:
                Toast.makeText(UserAfterLogin.this , ""+getUserName.Email , Toast.LENGTH_SHORT).show();
                break;

            case R.id.ph_no:
                Toast.makeText(UserAfterLogin.this , ""+getUserName.Phon_no , Toast.LENGTH_SHORT).show();
                break;


            case R.id.tutorial:

                Intent i = new Intent(UserAfterLogin.this , YoutubeVideo.class);
                startActivity(i);

                break;

            case R.id.help:

                Intent j = new Intent(UserAfterLogin.this , TermsandCondition.class);
                startActivity(j);

                break;


            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }





}
