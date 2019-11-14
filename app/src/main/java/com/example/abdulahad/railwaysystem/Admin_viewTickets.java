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
import java.util.Date;

import static com.example.abdulahad.railwaysystem.R.id.parent;

public class Admin_viewTickets extends AppCompatActivity {


    TextView Ticketdetails;
    ListView Trainlist;



    String Details;
    String a,b,c,d,e,f,g;
    int i=0;

    ArrayList<String> list = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_tickets);


    Ticketdetails=(TextView)findViewById(R.id.Train_tickets_detail);
    Trainlist=(ListView)findViewById(R.id.ticketlist);



        AdminTickets adminTickets =new  AdminTickets();
        adminTickets.execute("");

    }

    class AdminTickets extends AsyncTask<String , String , String> {

        ProgressDialog p = new ProgressDialog(Admin_viewTickets.this);

        @Override
        public void onPreExecute() {
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Displaying Tickets...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String query="select * from BookedTickets";
                ps=connection.prepareStatement(query);
                ResultSet rs=ps.executeQuery();

                while (rs.next()) {
                    a = rs.getString("Ticket_No");
                    b = rs.getString("P_id");
                    c = rs.getString("P_Name");
                    d = rs.getString("Destination_From");
                    e = rs.getString("Destination_To");
                    f = rs.getString("Train_id");
                    g=rs.getString("User_id");

                    Details =  "\nUser id : "+g + "\nTicket No : " + a + "\nPassenger ID : " + b + "\nPassenger Name : " + c + "\nFrom : " + d + "\nTo : " + e + "\nTrain ID :" +f;

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
                Ticketdetails.setText("You haven't booked any tickets yet");
            }
            else {

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 , list ){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);
                    TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                    ListItemShow.setTextColor(Color.parseColor("#000000"));
                    return view;
                }

            };
                Trainlist.setAdapter(arrayAdapter);
            }
        }

    }


}
