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

public class User_GiveFeedbacks extends AppCompatActivity {

    EditText fb;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__give_feedbacks);


        fb = (EditText) findViewById(R.id.write_feedback);
        btn = (Button) findViewById(R.id.submit_feedback);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fback = fb.getText().toString();

                if (fback.length()==0)  {
                    Toast.makeText(User_GiveFeedbacks.this,"Please write some feedback",Toast.LENGTH_SHORT).show();
                }

                else {
                    submitfeedback sf = new submitfeedback();
                    sf.execute("");
                }

            }
        });

    }

    class submitfeedback extends AsyncTask<String , String , String> {

        String feedBack = fb.getText().toString();

        ProgressDialog p = new ProgressDialog(User_GiveFeedbacks.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Submitting Feedback...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String sql = "INSERT INTO Feedbacks (User_id,User_Name,Feedback)" +"VALUES (?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ""+getUserName.u_id);
                preparedStatement.setString(2, ""+getUserName.userName);
                preparedStatement.setString(3, ""+feedBack);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();
            Toast.makeText(User_GiveFeedbacks.this,"Your Feedback has been submitted",Toast.LENGTH_SHORT).show();
        }

    }


}
