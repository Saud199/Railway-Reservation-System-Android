package com.example.abdulahad.railwaysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TermsandCondition extends AppCompatActivity {

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condition);

        send = (Button) findViewById(R.id.btnsend);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Intent homeIntent = new Intent(TermsandCondition.this, Login_activity.class);
                //startActivity(homeIntent);

                Toast.makeText(TermsandCondition.this , "Go Back To Continue " , Toast.LENGTH_SHORT).show();


            }
        });



    }

    }

