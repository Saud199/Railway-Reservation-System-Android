package com.example.abdulahad.railwaysystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin_ManageTrain extends AppCompatActivity {


    EditText TrainE;
    Button   TrainB;
    TextView TrainT;

    String gsTrainNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__manage_train);


        TrainE = (EditText) findViewById(R.id.deleting_Train);
        TrainB = (Button) findViewById(R.id.delete_Trainbtn);
        TrainT = (TextView) findViewById(R.id.deltrainresponce);


        TrainB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               gsTrainNo = TrainE.getText().toString();

                if (gsTrainNo.length()==0) {
                    Toast.makeText(Admin_ManageTrain.this,"Please enter a Train ID",Toast.LENGTH_SHORT).show();
                    TrainT.setText("Please enter Train ID");
                }

                else{
                    TrainT.setText("");
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_ManageTrain.this);
                builder.setCancelable(false);
                builder.setTitle("Cancel Train");
                builder.setMessage("Are you sure you want to cancel this Train ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        cancelTrain ct = new cancelTrain();
                        ct.execute("");
                    }

                });

                builder.setNegativeButton("No" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();

            }
            }
        });

    }

        class cancelTrain extends AsyncTask<String , String , String> {

            String Data;


            ProgressDialog p = new ProgressDialog(Admin_ManageTrain.this);

            @Override
            public void onPreExecute() {

                TrainT.setText("Connecting to Server");

                p.setTitle("Please Wait");
                p.setCancelable(false);
                p.setMessage("Canceling Train...");
                p.show();
            }

            @Override
            protected String doInBackground(String... strings) {


                    try {
                        DB_Connection obj_DB_Connection = new DB_Connection();
                        Connection connection = obj_DB_Connection.getConnection();
                        PreparedStatement ps = null;

                        String Ptrains="";

                        String query="select * from Train";
                        PreparedStatement p=connection.prepareStatement(query);
                        ResultSet rs=p.executeQuery();
                        while(rs.next()){

                            Ptrains="" + rs.getString("Train_id");

                            if(Ptrains.equals(gsTrainNo)){
                                Ptrains="-1";
                                break;
                            }

                        }


                        if(Ptrains.equals("-1")){

                        String sql = "Delete from Train WHERE Train_id=" + gsTrainNo;
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.executeUpdate();

                        String sql1="Delete from BookedTickets WHERE Train_id="+gsTrainNo;
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                        preparedStatement1.executeUpdate();


                        Data="Train has been cancled";

                        }

                        else{

                            Data="Train not present with this ID";
                        }



                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Data="Connection Error";
                    }

                return Data;
            }



            @Override
            public void onPostExecute(String Dat){
                p.dismiss();
                TrainT.setText(""+Dat);

                if(Dat.equalsIgnoreCase("Train has been cancled")){

                    TrainE.setText("");
                }



            }

        }





    }

