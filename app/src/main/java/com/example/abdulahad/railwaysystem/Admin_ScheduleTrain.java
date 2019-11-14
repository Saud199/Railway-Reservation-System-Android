package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Admin_ScheduleTrain extends AppCompatActivity {

    Spinner T_Name,Dest_To,Dest_From;
    EditText date,month,year;
    Button scheduleBtn;
    TextView sres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__schedule_train);

        T_Name = (Spinner) findViewById(R.id.trainname);
        Dest_From = (Spinner) findViewById(R.id.trainfrom);
        Dest_To = (Spinner) findViewById(R.id.trianto);
        date = (EditText) findViewById(R.id.Traindate);
        month = (EditText) findViewById(R.id.Trainmonth);
        year = (EditText) findViewById(R.id.Trainyear);
        scheduleBtn = (Button) findViewById(R.id.btnschedule);
        sres=(TextView)findViewById(R.id.Scheduleresponce);

        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dv = date.getText().toString();
                String mv = month.getText().toString();
                String yv = year.getText().toString();

                String d_f=Dest_From.getSelectedItem().toString();
                String d_t=Dest_To.getSelectedItem().toString();

                if (dv.length()==0 || mv.length()==0 || yv.length()==0) {
                    Toast.makeText(Admin_ScheduleTrain.this,"Please fill out all the fields",Toast.LENGTH_SHORT).show();
                }

                else if (mv.equals("2") && ( dv.equals("29") || dv.equals("30") || dv.equals("31") )) {
                    Toast.makeText(Admin_ScheduleTrain.this,"Invalid Date",Toast.LENGTH_SHORT).show();
                }

                else if(!yv.equals("2018") ){

                    Toast.makeText(Admin_ScheduleTrain.this,"Invalid Year",Toast.LENGTH_SHORT).show();
                }
                else if (( mv.equals("4") || mv.equals("6") || mv.equals("9") || mv.equals("11") ) && ( dv.equals("31") )) {
                    Toast.makeText(Admin_ScheduleTrain.this,"Invalid Date",Toast.LENGTH_SHORT).show();
                }

                else if (d_f.equalsIgnoreCase(d_t)) {
                    Toast.makeText(Admin_ScheduleTrain.this,"Invalid Destination",Toast.LENGTH_SHORT).show();
                }

                else {
                    scheduleTrain st = new scheduleTrain();
                    st.execute("");
                }

            }
        });


    }


    class scheduleTrain extends AsyncTask<String , String , String> {

        String Data;

        String t_name=T_Name.getSelectedItem().toString();
        String dest_f=Dest_From.getSelectedItem().toString();
        String dest_t=Dest_To.getSelectedItem().toString();

        String dval = date.getText().toString();
        String mval = month.getText().toString();
        String yval = year.getText().toString();

        String finaldate=""+yval+"-"+mval+"-"+dval;

        ProgressDialog p = new ProgressDialog(Admin_ScheduleTrain.this);

        @Override
        public void onPreExecute() {

            sres.setText("Connecting to Server");

            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Scheduling Train...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String data = "INSERT INTO Train (Train_Name,Destination_From,Destination_To,Departure_Date)"+"VALUES(?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(data);
                preparedStatement.setString(1, ""+t_name);
                preparedStatement.setString(2, ""+dest_f);
                preparedStatement.setString(3, ""+dest_t);
                preparedStatement.setString(4, ""+finaldate);
                preparedStatement.executeUpdate();

                Data="Train has been scheduled";

            } catch (Exception e) {
                e.printStackTrace();
                Data="Connection Error";
            }

            return Data;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();

            if(Dat.equalsIgnoreCase("Train has been scheduled")){

                date.setText("");
                month.setText("");
                year.setText("");
            }


            sres.setText(Dat);

        }

    }


}
