package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Admin_ManageUsers extends AppCompatActivity {

    ListView userlis;

    String Details;
    String a,b,c,d,e;
    int i=0;

    ArrayList<String> list = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__manage_users);

        userlis=(ListView)findViewById(R.id.userlist);

        viewlist vlist = new viewlist();
        vlist.execute("");


    }


    class viewlist extends AsyncTask<String , String , String> {


        ProgressDialog p = new ProgressDialog(Admin_ManageUsers.this);

        @Override
        public void onPreExecute() {

            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Displaying Users...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                DB_Connection obj_DB_Connection=new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;

                String query="select * from User";
                ps=connection.prepareStatement(query);
                ResultSet rs=ps.executeQuery();

                while (rs.next()) {
                    a = rs.getString("U_Name");
                    b = rs.getString("U_Gender");
                    c = rs.getString("U_Email");
                    d = rs.getString("U_Mobile_No");
                    e=rs.getString("User_id");

                    Details =  "\nUser ID : "+ e + "\nName : " + a + "\nGender : " + b + "\nEmail : " + c + "\nMobile no : " + d;

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
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list ){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);
                    TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                    ListItemShow.setTextColor(Color.parseColor("#000000"));
                    return view;
                }

            };
            userlis.setAdapter(arrayAdapter);
        }


    }


}
