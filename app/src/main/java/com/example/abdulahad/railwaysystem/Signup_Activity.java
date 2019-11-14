package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.mysql.jdbc.integration.c3p0.MysqlConnectionTester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Signup_Activity extends AppCompatActivity {


    EditText Ename , Eage , Eemail , Epassword , Estate , Ephoneno  , Eadminvd;
    Spinner  Egender , Eacctype;
    Button sbmt;
    TextView Tresponce;

    String Name , Age ,Email , Password ,Gender , Acctype , phno , State , Adminvd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);

        Ename = (EditText)findViewById(R.id.sname);
        Eage = (EditText)findViewById(R.id.sage);
        Eemail = (EditText)findViewById(R.id.semail);
        Epassword = (EditText)findViewById(R.id.spassword);
        Estate = (EditText)findViewById(R.id.sstate);
        Ephoneno = (EditText)findViewById(R.id.sgetphoneno);
        Eadminvd = (EditText)findViewById(R.id.admin_key);

        Egender = (Spinner) findViewById(R.id.select_gender);
        Eacctype = (Spinner) findViewById(R.id.select_acc_type);

        sbmt = (Button)findViewById(R.id.sbtn_Submit);

        Tresponce=(TextView)findViewById(R.id.responce);


        Eadminvd.setEnabled(false);


        Eacctype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                   String value = (String) adapterView.getItemAtPosition(i);

                                                   if (value.equalsIgnoreCase("Admin")) {
                                                       Eadminvd.setEnabled(true);
                                                       Eadminvd.setHint("Enter admin key");
                                                   }
                                                   else {
                                                       Eadminvd.setText("");
                                                       Eadminvd.setHint("");
                                                       Eadminvd.setEnabled(false);
                                                   }

                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> adapterView) {

                                               }
                                           });




        sbmt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                try {

                    create();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    void create() throws SQLException, ClassNotFoundException {


        synchronized (Signup_Activity.class) {

             Name = Ename.getText().toString();
             Age = Eage.getText().toString();
             Email = Eemail.getText().toString();
             Password = Epassword.getText().toString();
             phno = Ephoneno.getText().toString();
             Adminvd = Eadminvd.getText().toString();
             State = Estate.getText().toString();
             Gender = Egender.getSelectedItem().toString();
             Acctype = Eacctype.getSelectedItem().toString();

            int chk = 0;


            if (Acctype.equalsIgnoreCase("Admin") && Adminvd == "") {

                Toast.makeText(Signup_Activity.this, "Enter admin key", Toast.LENGTH_SHORT).show();
            } else if (Name.length() == 0 || Age.length() == 0 || phno.length() == 0 || Email.length() == 0 || Password.length() == 0 || State.length() == 0) {
                Toast.makeText(Signup_Activity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else if (Acctype.equalsIgnoreCase("Admin") && !Adminvd.equals("12345")) {
                Toast.makeText(Signup_Activity.this, "Enter Correct admin key", Toast.LENGTH_SHORT).show();
            } else if (!Email.contains("@") || !Email.contains(".com")) {
                Toast.makeText(Signup_Activity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
            } else{

                SignupInfo SI = new SignupInfo();
                SI.execute("");
            }
        }

    }


    class SignupInfo extends AsyncTask<String , String , String> {

        String  Data ;
        int counter=0;

        ProgressDialog p = new ProgressDialog(Signup_Activity.this);



        @Override
        public void onPreExecute() {

            Tresponce.setText("Connecting to Server");
            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Creating Account");
            p.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                DB_Connection obj_DB_Connection = new DB_Connection();
                Connection connection=obj_DB_Connection.getConnection();
                PreparedStatement ps=null;
                if(connection==null){

                    Data="Connection is not established";

                }else{

                    String query="select * from User";
                    ps=connection.prepareStatement(query);
                    ResultSet rs=ps.executeQuery();

                    while(rs.next()){

                        String Checker=""+rs.getString("U_Email");

                        if(Checker.equalsIgnoreCase(Email))
                        {
                            counter = -1;
                            break;

                        }

                    }

                }



                if(counter!=-1){


                String data = "INSERT INTO User (U_Name,U_Gender,U_Age,U_Mobile_No,U_Email,U_Password,State,Account_Type)"+
                        "VALUES(?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(data);
                preparedStatement.setString(1, ""+Name);
                preparedStatement.setString(2, ""+Gender);
                preparedStatement.setString(3, ""+Age);
                preparedStatement.setString(4, ""+phno);
                preparedStatement.setString(5, ""+Email);
                preparedStatement.setString(6, ""+Password);
                preparedStatement.setString(7, ""+State);
                preparedStatement.setString(8, ""+Acctype);
                preparedStatement.executeUpdate();

                connection.close();

                    Data = "Account Created Successfully";


            }
            else{
                    Data="Email Already Exists";
                }
            } catch (Exception e) {

                Data="Connection Error" ;
                e.printStackTrace();

            }

            return Data;
        }

        @Override
        public void onPostExecute(String Dat){
            p.dismiss();
            Tresponce.setText(Dat);

            if(Dat.equalsIgnoreCase("Account Created Successfully")){

                Toast.makeText(Signup_Activity.this , "Account Created Successfully" , Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Signup_Activity.this , Login_activity.class);
                startActivity(i);
            }


          /*  if(Dat.equalsIgnoreCase("Account Created Suceccfully")){
                Toast.makeText(Signup_Activity.this , "1" , Toast.LENGTH_SHORT).show();
                if(Acctype.equalsIgnoreCase("Customer")){

                    Intent i = new Intent(Signup_Activity.this, UserAfterLogin.class);
                    startActivity(i);
                }

                if(Acctype.equalsIgnoreCase("Admin")){

                    Intent i = new Intent(Signup_Activity.this, AdminAfterLogin.class);
                    startActivity(i);

                }
            }*/

        }
    }
}

















            /*else {


                // PreparedStatement ps = null;

            ProgressDialog pd = new ProgressDialog(Signup_Activity.this);
            pd.setCancelable(false);
            pd.setTitle("Login");
            pd.setMessage("loading...  Please wait!");
            pd.show();

            try {

                Toast.makeText(Signup_Activity.this, "Enter in else", Toast.LENGTH_SHORT).show();

                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection connection = DriverManager.getConnection(MainActivity.DB_URL, MainActivity.USER, MainActivity.PASS);
               // PreparedStatement preparedStatement;
                Statement preparedStatement;

                if(connection==null){
                    Toast.makeText(Signup_Activity.this, "Connection not established", Toast.LENGTH_SHORT).show();
                }
                else {
                 //   String query = "select * from User";
                 //   preparedStatement = connection.prepareStatement(query);
                   // ResultSet rs = preparedStatement.executeQuery();
                    // while(rs.next()) {
                    //   Toast.makeText(Signup_Activity.this, "" + rs.getString("U_Name"), Toast.LENGTH_SHORT).show();
                    // }

                    //String query = "SELECT U_Email FROM User";
                    //ps = connection.prepareStatement(query);
                    //ResultSet rs = ps.executeQuery();

               while (rs.next()) {
                    String check = rs.getString("U_Email");
                    if (check.equals(Email)) {
                        Toast.makeText(Signup_Activity.this, "Email already exist", Toast.LENGTH_SHORT).show();
                        chk = 1;
                    }
                }

                if (chk == 0) {
                    String data = "INSERT INTO User (U_Name,U_Gender)" +
                            "VALUES( ' " + Name  + ","  +  Gender  + " )";

                    preparedStatement = connection.createStatement();
                    preparedStatement.(1, "" + Name);
                    preparedStatement.setString(2, "" + Gender);
                    preparedStatement.setString(3, "" + Age);
                    preparedStatement.setString(4, "" + phno);
                    preparedStatement.setString(5, "" + Email);
                    preparedStatement.setString(6, "" + Password);
                    preparedStatement.setString(7, "" + State);
                    preparedStatement.setString(8, "" + Acctype);
                    preparedStatement.executeUpdate(data);



                    //+ Gender  ,  Age  ,  phno  , Email  , Password , State , Acctype
                    //,U_Age,U_Mobile_No,U_Email,U_Password,State,Account_Type

                }

                connection.close();
                    //   pd.cancel();
                    Toast.makeText(Signup_Activity.this, "your Account has beeen created ", Toast.LENGTH_SHORT).show();
                    //}


            } catch (Exception e) {
              //  pd.cancel();
                Toast.makeText(Signup_Activity.this, "" + e, Toast.LENGTH_SHORT).show();
            }


        }

            }
        }

    }
} */




