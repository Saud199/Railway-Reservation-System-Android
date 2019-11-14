package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.Pools;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_activity extends AppCompatActivity {


    Button login_btn , signup_btn ,TandC;
    EditText email , password;
    TextView logresponce;

    public void onBackPressed() {
        Toast.makeText(Login_activity.this,"Press Home Button To Go Back",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        login_btn = (Button) findViewById(R.id.xlogin);
        signup_btn = (Button) findViewById(R.id.xsignup);
        TandC = (Button)findViewById(R.id.terms);

        logresponce=(TextView)findViewById(R.id.loginresponce);



        TandC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent homeIntent = new Intent(Login_activity.this, TermsandCondition.class);
                startActivity(homeIntent);

            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e,p;
                e=email.getText().toString();
                p=password.getText().toString();

                if (e.length()==0 || p.length()==0) {
                    Toast.makeText(Login_activity.this,"Please fill out all the fields",Toast.LENGTH_SHORT).show();
                }

                else {
                    LoginCheck l = new LoginCheck();
                    l.execute("");
                }

            }

        });

     signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login_activity.this, Signup_Activity.class);
                startActivity(i);

            }

        });

    }


    class LoginCheck extends AsyncTask<String, String, String> {


        String Uemail =email.getText().toString();
        String Upassword = password.getText().toString();


        String Data , pwd , Utype ;


        String ftype;
        int checker=0;

        ProgressDialog p = new ProgressDialog(Login_activity.this);

        @Override
        public void onPreExecute() {

            logresponce.setText("Connecting to Server");


            p.setTitle("Please Wait");
            p.setMessage("Logging In ...");
            p.setCancelable(false);
            p.show();



        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                DB_Connection obj_DB_Connection = new DB_Connection();
                java.sql.Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;
                if(connection==null){

                    Data="Connection is not established";

                }else{

                    String query="select * from User";
                    ps=connection.prepareStatement(query);
                    ResultSet rs=ps.executeQuery();
                    while(rs.next()){

                        Data = "" + rs.getString("U_Email");
                        pwd="" + rs.getString("U_Password");
                        Utype="" +rs.getString("Account_Type");

                        if(Uemail.equalsIgnoreCase(Data)  && Upassword.equalsIgnoreCase(pwd)){

                            getUserName.u_id=rs.getString("User_id");
                            getUserName.userName=rs.getString("U_Name");
                            getUserName.Email=rs.getString("U_Email");
                            getUserName.Phon_no=rs.getString("U_Mobile_No");

                            checker=1;

                            ftype=Utype;

                            break;
                        }

                        else{

                          Data = "Invalid Email or Password";

                        }

                    }

                }
                connection.close();
            } catch (Exception e) {

                Data = "Connection Error";
                e.printStackTrace();

            }

            return Data;
        }

        @Override
        public void onPostExecute(String Dat) {


            logresponce.setText(Dat);

            p.dismiss();

            if(checker==1){

                if(ftype.equalsIgnoreCase("Customer")){

                    Intent i = new Intent(Login_activity.this, UserAfterLogin.class);
                    startActivity(i);
                }

                if(ftype.equalsIgnoreCase("Admin")){

                    Intent i = new Intent(Login_activity.this, AdminAfterLogin.class);
                    startActivity(i);

                }

            }else{

                logresponce.setText(Dat);

            }


        }
    }


}





