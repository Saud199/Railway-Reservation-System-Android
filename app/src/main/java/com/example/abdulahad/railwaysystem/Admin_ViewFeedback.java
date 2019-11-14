package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Admin_ViewFeedback extends AppCompatActivity {

    Button deleteBtn;
    ListView feedbackList;
    TextView feedbackstatus;

    String fbdetails;

    String a,b,c;

    int i=0;

    ArrayList<String> fblist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__view_feedback);

        deleteBtn = (Button) findViewById(R.id.deletefeedback_btn);
        feedbackList = (ListView) findViewById(R.id.feedback_list);
        feedbackstatus = (TextView) findViewById(R.id.emptyfeedbaks);

        retrievefeedBacks rfb = new retrievefeedBacks();
        rfb.execute("");

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_ViewFeedback.this);
                builder.setCancelable(false);
                builder.setTitle("Delete Feedbacks");
                builder.setMessage("Are you sure you want to delete all feedbacks ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deletefeedBacks dfb = new deletefeedBacks();
                        dfb.execute("");
                    }
                });

                builder.setNegativeButton("Cancel" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
            }
        });

    }


    class retrievefeedBacks extends AsyncTask<String , String , String> {

        ProgressDialog p = new ProgressDialog(Admin_ViewFeedback.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Fetching Feedbacks...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String query="select * from Feedbacks";
                ps=connection.prepareStatement(query);
                ResultSet rs=ps.executeQuery();

                while (rs.next()) {
                    a = rs.getString("User_id");
                    b = rs.getString("User_Name");
                    c = rs.getString("Feedback");

                    fbdetails = "\nUser ID : " +a+ "\nUser Name : " +b+ "\nFeedback : "+c;

                    fblist.add(i , fbdetails);

                    i++;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();
            if (i==0) {
                feedbackstatus.setText("No Feedbacks yet");
            }
            else {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fblist ){

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                        ListItemShow.setTextColor(Color.parseColor("#000000"));
                        return view;
                    }

                    };

                feedbackList.setAdapter(arrayAdapter);
            }
        }

    }

    class deletefeedBacks extends AsyncTask<String , String , String> {

        ProgressDialog p = new ProgressDialog(Admin_ViewFeedback.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Deleting Feedbacks...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String sql1="Delete from Feedbacks";
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.executeUpdate();

                fblist.removeAll(fblist);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();
            feedbackstatus.setText("No Feedbacks yet");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 , fblist ){

                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);
                    TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                    ListItemShow.setTextColor(Color.parseColor("#000000"));
                    return view;
                }

            };

            feedbackList.setAdapter(arrayAdapter);
            Toast.makeText(Admin_ViewFeedback.this,"All feedbacks have been deleted",Toast.LENGTH_SHORT).show();
        }

    }



}
