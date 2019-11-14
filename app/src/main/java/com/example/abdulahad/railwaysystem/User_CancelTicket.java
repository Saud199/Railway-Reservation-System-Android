package com.example.abdulahad.railwaysystem;

        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.TintContextWrapper;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

public class User_CancelTicket extends AppCompatActivity {

    EditText ticketNo;
    Button cancelBtn;
    TextView cancelres;

    String gsTicketNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__cancel_ticket);

        ticketNo = (EditText)findViewById(R.id.delete_ticketbyuser);
        cancelBtn = (Button) findViewById(R.id.deletebtn_ticketbyusers);
        cancelres=(TextView)findViewById(R.id.cancelticketresponce);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gsTicketNo = ticketNo.getText().toString();

                if (gsTicketNo.length() == 0) {
                    Toast.makeText(User_CancelTicket.this, "Please enter a ticket no", Toast.LENGTH_SHORT).show();

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(User_CancelTicket.this);
                    builder.setCancelable(false);
                    builder.setTitle("Cancel Ticket");
                    builder.setMessage("Are you sure you want to cancel this ticket ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            cancelTicket ct = new cancelTicket();
                            ct.execute("");

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.create().show();

                }
            }
        });

    }



    class cancelTicket extends AsyncTask<String , String , String> {

        String Data;


        ProgressDialog p = new ProgressDialog(User_CancelTicket.this);

        @Override
        public void onPreExecute() {

            cancelres.setText("Connecting to Server");

            p.setTitle("Please Wait");
            p.setCancelable(false);
            p.setMessage("Canceling Ticket...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {


                try {
                    DB_Connection obj_DB_Connection = new DB_Connection();
                    Connection connection = obj_DB_Connection.getConnection();
                    PreparedStatement ps = null;

                    String Ptrainsticket="";
                    String query="select * from BookedTickets";
                    PreparedStatement p=connection.prepareStatement(query);
                    ResultSet rs=p.executeQuery();
                    while(rs.next()){

                        Ptrainsticket="" + rs.getString("Ticket_No");

                        if(Ptrainsticket.equals(gsTicketNo)){
                            Ptrainsticket="-1";
                            break;
                        }

                    }


                    if(Ptrainsticket.equals("-1")){

                    String sql = "Delete from BookedTickets WHERE Ticket_No=" + gsTicketNo   + " AND User_id=" + getUserName.u_id;

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();

                    Data="Ticket has been cancled";
                }else {

                        Data="You have not Booked Ticket with Ticket ID : " + gsTicketNo;
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
           cancelres.setText(""+Dat);
        }


    }


}
