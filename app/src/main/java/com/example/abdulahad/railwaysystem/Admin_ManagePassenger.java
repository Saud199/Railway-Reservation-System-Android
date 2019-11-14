package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Admin_ManagePassenger extends AppCompatActivity {

    ListView trainList;
    TextView trainsStatus;

    String a,b,c,d,e;

    String Details;

    int i=0;

    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__manage_passenger);

        trainList = (ListView) findViewById(R.id.Train_Schedulelist_admin);
        trainsStatus = (TextView) findViewById(R.id.Train_Scheduletxt_admin);

        scheduledTrainsAdmin sta = new scheduledTrainsAdmin();
        sta.execute("");

    }

    class scheduledTrainsAdmin extends AsyncTask<String , String , String> {

        ProgressDialog p = new ProgressDialog(Admin_ManagePassenger.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Displaying Trains...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String query="select * from Train";
                ps=connection.prepareStatement(query);
                ResultSet rs=ps.executeQuery();

                while (rs.next()) {
                    a = rs.getString("Train_id");
                    b = rs.getString("Train_Name");
                    c = rs.getString("Destination_From");
                    d = rs.getString("Destination_To");
                    e = rs.getString("Departure_Date");

                    Details = "\nTrain ID : " + a + "\nTrain Name : " + b + "\nFrom : " + c + "\nTo : " + d + "\nDeparture Date : " + e;

                    list.add(i , Details);

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
                trainsStatus.setText("No Trains Available");
            }
            else {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list ){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                        ListItemShow.setTextColor(Color.parseColor("#000000"));
                        return view;
                    }

                };
                trainList.setAdapter(arrayAdapter);
            }
        }

    }

}
