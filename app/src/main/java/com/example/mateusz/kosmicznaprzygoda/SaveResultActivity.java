package com.example.mateusz.kosmicznaprzygoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveResultActivity extends AppCompatActivity {
    Button button5;
    Button button6;
    Button button7;
    EditText editText5;
    TextView textView5;
    DatabaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_result);
        connection = new  DatabaseConnection();
        connection.Connect();

        textView5=(TextView) findViewById(R.id.textView5);
        textView5.setText("Punkty: "+Integer.toString(Constants.SCORCES));


        button5=(Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGame();
            }
        });

        button6=(Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                openMainActivity();
            }
        });

        button7=(Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMainActivity();
            }
        });


    }

    public void openNewGame(){
        Intent intent = new Intent(this,GameScene.class);
        startActivity(intent);
        finish();
    }

    public void sendData(){


         editText5= (EditText) findViewById(R.id.editText5);
         String nick = editText5.getText().toString();


        Statement st = null;
        try {
            st = DatabaseConnection.getConnection().createStatement();

            String sql = "INSERT INTO roznosci.wyniki VALUES('"+nick+"',"+Integer.toString(Constants.SCORCES)+") ";

            st.executeUpdate(sql);




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
