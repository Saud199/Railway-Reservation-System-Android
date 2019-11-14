package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User_UpdateInfo extends AppCompatActivity {

    EditText c_pass,n_pass,n_mobNo;
    Button passBtn,mobileBtn;

    String matchPassword;
    int passcheck=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__update_info);

        c_pass = (EditText) findViewById(R.id.current_pass);
        n_pass = (EditText) findViewById(R.id.new_pass);
        n_mobNo = (EditText) findViewById(R.id.new_mob_no);
        passBtn = (Button) findViewById(R.id.changepass_btn);
        mobileBtn = (Button) findViewById(R.id.changemobno_btn);



        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gc_pass = c_pass.getText().toString();
                String gn_pass = n_pass.getText().toString();

                if (gc_pass.length()==0 || gn_pass.length()==0) {
                    Toast.makeText(User_UpdateInfo.this,"Current Password or New Password field is empty",Toast.LENGTH_SHORT).show();
                }

                else if (gc_pass.equals(gn_pass)) {
                    Toast.makeText(User_UpdateInfo.this,"Both passwords are same",Toast.LENGTH_SHORT).show();
                }

                else {
                    updatePass up = new updatePass();
                    up.execute("");
                }

            }
        });

        mobileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gMobile_No = n_mobNo.getText().toString();

                if (gMobile_No.length()==0) {
                    Toast.makeText(User_UpdateInfo.this,"Please type new mobile number",Toast.LENGTH_SHORT).show();
                }

                else {
                    changeMobileNo cmn = new changeMobileNo();
                    cmn.execute("");
                }

            }
        });


    }


    class updatePass extends AsyncTask<String , String , String> {


        String bgc_pass = c_pass.getText().toString();
        String bgn_pass = n_pass.getText().toString();

        ProgressDialog p = new ProgressDialog(User_UpdateInfo.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Changing Password...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection = new DB_Connection();
                Connection connection = obj_DB_Connection.getConnection();
                PreparedStatement ps = null;

                String query="select U_Password from User where User_id="+getUserName.u_id;
                ps=connection.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    matchPassword=rs.getString("U_Password");
                }

                if (!bgc_pass.equals(matchPassword)) {
                    //Toast.makeText(User_UpdateInfo.this,"Current password is incorrect",Toast.LENGTH_SHORT).show();
                    passcheck=2;
                }

                else {
                    String sql1="UPDATE User SET U_Password='"+bgn_pass+"' where User_id="+getUserName.u_id;
                    PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                    preparedStatement.executeUpdate();
                    passcheck=1;
                }


            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();
            if (passcheck==1) {
                Toast.makeText(User_UpdateInfo.this,"Your password has been changed",Toast.LENGTH_SHORT).show();
            }
            else if (passcheck==2) {
                Toast.makeText(User_UpdateInfo.this,"Current password is incorrect",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(User_UpdateInfo.this,"Your Password is not changed",Toast.LENGTH_SHORT).show();
            }
        }

    }

    class changeMobileNo extends AsyncTask<String , String , String> {

        String bgMobile_No = n_mobNo.getText().toString();

        ProgressDialog p = new ProgressDialog(User_UpdateInfo.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Changing Mobile Number...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection = new DB_Connection();
                Connection connection = obj_DB_Connection.getConnection();
                PreparedStatement ps = null;

                String sql1="UPDATE User SET U_Mobile_No ='"+bgMobile_No+"' where User_id="+getUserName.u_id;
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.executeUpdate();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();
            Toast.makeText(User_UpdateInfo.this,"Your Mobile No has been changed",Toast.LENGTH_SHORT).show();
        }

    }

}
