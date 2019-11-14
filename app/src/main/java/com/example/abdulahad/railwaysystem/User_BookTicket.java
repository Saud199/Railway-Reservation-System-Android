package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class User_BookTicket extends AppCompatActivity {


    EditText Pname, Page;
    Spinner Pgender, Pclass, PTrain_id;
    Button Pbookbtn;
    TextView btres;

    String from, to, pass_id;
    String t_id;

    ArrayList<String> alist = new ArrayList<String>();
    int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__book_ticket);


        Pname = (EditText) findViewById(R.id.Tbooking_passenger_name);
        Page = (EditText) findViewById(R.id.Tboking_passenger_age);

        Pgender = (Spinner) findViewById(R.id.Tbookin_user_gender);
        Pclass = (Spinner) findViewById(R.id.Tbokking_user_class);
        PTrain_id = (Spinner) findViewById(R.id.Tbooking_trainid);

        Pbookbtn = (Button) findViewById(R.id.btnschedule);
        btres = (TextView) findViewById(R.id.bookticketresponce);


        getTrain gt = new getTrain();
        gt.execute("");


        Pbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bookres bookres = new Bookres();
                bookres.execute("");

            }
        });


       PTrain_id .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


               t_id = PTrain_id.getSelectedItem().toString();

                 if (t_id.equalsIgnoreCase("Select Train")) {

                     Toast.makeText(User_BookTicket.this ," Please Select a Train First",Toast.LENGTH_SHORT).show();

                 }else {

                     gTrainDetail gt = new gTrainDetail();
                     gt.execute("");
                 }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {}
    });

    }


    class getTrain extends AsyncTask<String, String, String> {


        String Data;
        ProgressDialog p = new ProgressDialog(User_BookTicket.this);

        @Override
        public void onPreExecute() {


            p.setTitle("Please Wait");
            p.setMessage("Fetching Scheduled Trains ....");
            p.setCancelable(false);
            p.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            alist.add(0, "Select Train");

            DB_Connection obj_DB_Connection = new DB_Connection();
            java.sql.Connection connection = obj_DB_Connection.getConnection();
            PreparedStatement ps = null;

            try {
                String query = "select Train_id from Train";
                ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    alist.add(counter, "" + rs.getString("Train_id"));
                    counter++;
                }

                Data = "";

            } catch (Exception e) {
                Data = "Connection Error";
            }


            return Data;
        }

        @Override
        public void onPostExecute(String Dat) {

            p.dismiss();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, alist){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);
                    TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                    ListItemShow.setTextColor(Color.parseColor("#000000"));
                    return view;
                }

            };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            PTrain_id.setAdapter(adapter);

            btres.setText("" + Dat);

        }
    }


    class Bookres extends AsyncTask<String, String, String> {

        String Data;
        ProgressDialog p = new ProgressDialog(User_BookTicket.this);

        String name = Pname.getText().toString();
        String age = Page.getText().toString();
        String gender = Pgender.getSelectedItem().toString();
        String pclass = Pclass.getSelectedItem().toString();




        @Override
        public void onPreExecute() {

            btres.setText("Please Wait");


            p.setTitle("Please Wait");
            p.setMessage("Booking Ticket ...");
            p.setCancelable(false);
            p.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            if (name.length() == 0 || age.length() == 0) {
                Data = "Please Fill out the Fields";
            } else if (t_id.equalsIgnoreCase("No Trains Available")) {

                Data = "No Train Selected";
            } else {


                try {

                    DB_Connection obj_DB_Connection = new DB_Connection();
                    java.sql.Connection connection = obj_DB_Connection.getConnection();
                    PreparedStatement ps = null;
                    if (connection == null) {

                        Data = "Connection is not established";

                    } else {


                        try {

                            int emptycount = 0;

                            String qry = "SELECT count(*) From Passenger";
                            ps = connection.prepareStatement(qry);
                            ResultSet rs11 = ps.executeQuery();
                            while (rs11.next()) {
                                emptycount = rs11.getInt("count(*)");
                            }

                            if (emptycount == 0) {
                                String sql = "INSERT INTO Passenger (P_Name,P_Age,P_Gender,Destination_From,Destination_To,User_id,Class,Train_id)" + "VALUES (?,?,?,?,?,?,?,?)";

                                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setString(1, "" + name);
                                preparedStatement.setString(2, "" + age);
                                preparedStatement.setString(3, "" + gender);
                                preparedStatement.setString(4, "" + from);
                                preparedStatement.setString(5, "" + to);
                                preparedStatement.setString(6, "" + getUserName.u_id);
                                preparedStatement.setString(7, "" + pclass);
                                preparedStatement.setString(8, "" + t_id);
                                preparedStatement.executeUpdate();

                                String query1 = "select P_id from Passenger where P_Name='" + name + "' AND Train_id=" + t_id;
                                ps = connection.prepareStatement(query1);
                                ResultSet rs = ps.executeQuery();

                                while (rs.next()) {
                                    pass_id = "" + rs.getString("P_id");
                                }

                                String sql1 = "INSERT INTO BookedTickets (P_id,P_Name,Destination_From,Destination_To,Train_id,User_id)" + "VALUES(?,?,?,?,?,?)";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                                preparedStatement1.setString(1, "" + pass_id);
                                preparedStatement1.setString(2, "" + name);
                                preparedStatement1.setString(3, "" + from);
                                preparedStatement1.setString(4, "" + to);
                                preparedStatement1.setString(5, "" + t_id);
                                preparedStatement1.setString(6, "" + getUserName.u_id);
                                preparedStatement1.executeUpdate();

                                Data = "Your ticket has been booked";

                            } else {

                                String sql = "INSERT INTO Passenger (P_Name,P_Age,P_Gender,Destination_From,Destination_To,User_id,Class,Train_id)" + "VALUES (?,?,?,?,?,?,?,?)";

                                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setString(1, "" + name);
                                preparedStatement.setString(2, "" + age);
                                preparedStatement.setString(3, "" + gender);
                                preparedStatement.setString(4, "" + from);
                                preparedStatement.setString(5, "" + to);
                                preparedStatement.setString(6, "" + getUserName.u_id);
                                preparedStatement.setString(7, "" + pclass);
                                preparedStatement.setString(8, "" + t_id);
                                preparedStatement.executeUpdate();

                                String query1 = "select P_id from Passenger where P_Name='" + name + "' AND Train_id=" + t_id;
                                ps = connection.prepareStatement(query1);
                                ResultSet rs = ps.executeQuery();

                                while (rs.next()) {
                                    pass_id = "" + rs.getString("P_id");
                                }

                                String sql1 = "INSERT INTO BookedTickets (P_id,P_Name,Destination_From,Destination_To,Train_id,User_id)" + "VALUES(?,?,?,?,?,?)";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                                preparedStatement1.setString(1, "" + pass_id);
                                preparedStatement1.setString(2, "" + name);
                                preparedStatement1.setString(3, "" + from);
                                preparedStatement1.setString(4, "" + to);
                                preparedStatement1.setString(5, "" + t_id);
                                preparedStatement1.setString(6, "" + getUserName.u_id);
                                preparedStatement1.executeUpdate();

                                Data = "Your ticket has been booked";


                            }
                        } catch (Exception e) {
                            Data = "Connection Error";

                        }

                    }

                } catch (Exception e) {
                    Data = "Connection Error";

                }

                return Data;
            }

            return Data;
        }

        @Override
        public void onPostExecute(String Dat) {

            btres.setText(Dat);
            p.dismiss();

        }
    }


    class gTrainDetail extends AsyncTask<String, String, String> {

        String Data;

        ProgressDialog p = new ProgressDialog(User_BookTicket.this);

        @Override
        public void onPreExecute() {

            btres.setText("Please Wait");

            p.setTitle("Please Wait");
            p.setMessage("Fetching Train Details ...");
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


                    String query="select Destination_From,Destination_To from Train where Train_id="+t_id;
                    ps=connection.prepareStatement(query);
                    ResultSet rs=ps.executeQuery();
                    while(rs.next()){
                        from=""+rs.getString("Destination_From");
                        to=""+rs.getString("Destination_To");
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



            btres.setText(Dat);
            p.dismiss();



        }
    }

}

