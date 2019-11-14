package com.example.abdulahad.railwaysystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

public class AdminAfterLogin extends AppCompatActivity {
    //implements ViewSwitcher.ViewFactory{


    ViewFlipper viewFlipper;
    Button schedule_train, manage_train, view_ticket, view_passenger, manage_user, view_feedback, signout;
    private TextView tv , Log_ad_name;


    int gallery_grid_Images[] = {R.drawable.bg_image, R.drawable.bg_image2, R.drawable.bg_image3, R.drawable.bg_image4};


    public void onBackPressed() {
        Toast.makeText(AdminAfterLogin.this,"Please Log out to go back",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_after_login);

        schedule_train = (Button) findViewById(R.id.id_Schedule_train);
        manage_train = (Button) findViewById(R.id.id_manageTrain);
        view_ticket = (Button) findViewById(R.id.id_viewTickets);
        view_passenger = (Button) findViewById(R.id.id_viewpassengersbtn);
        manage_user = (Button) findViewById(R.id.id_manageusersbtn);
        view_feedback = (Button) findViewById(R.id.id_viewfeedbacks);
        signout = (Button) findViewById(R.id.id_signoutbtn);

        tv = (TextView) this.findViewById(R.id.mywidget);
        Log_ad_name=(TextView)findViewById(R.id.logger_adminname);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);


        tv.setSelected(true);

        Log_ad_name.setText("Logged in as : "+getUserName.userName);




        schedule_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Admin_ScheduleTrain.class);
                startActivity(i);

            }
        });


        manage_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Admin_ManageTrain.class);
                startActivity(i);

            }
        });


        view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Admin_viewTickets.class);
                startActivity(i);

            }
        });


        view_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Admin_ManagePassenger.class);
                startActivity(i);

            }
        });


        manage_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Admin_ManageUsers.class);
                startActivity(i);

            }
        });


        view_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Admin_ViewFeedback.class);
                startActivity(i);

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminAfterLogin.this, Login_activity.class);
                startActivity(i);

            }
        });


        for (int i = 0; i < gallery_grid_Images.length; i++) {
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
                Toast.makeText(AdminAfterLogin.this , ""+getUserName.Email , Toast.LENGTH_SHORT).show();
                break;

            case R.id.ph_no:
                Toast.makeText(AdminAfterLogin.this , ""+getUserName.Phon_no , Toast.LENGTH_SHORT).show();
                break;


            case R.id.tutorial:

                Intent i = new Intent(AdminAfterLogin.this , YoutubeVideo.class);
                startActivity(i);

                break;

            case R.id.help:

                Intent j = new Intent(AdminAfterLogin.this , TermsandCondition.class);
                startActivity(j);

                break;



            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


}




