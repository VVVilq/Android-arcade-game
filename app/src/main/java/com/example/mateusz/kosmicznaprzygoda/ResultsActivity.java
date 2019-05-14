package com.example.mateusz.kosmicznaprzygoda;


import android.content.Intent;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultsActivity extends AppCompatActivity {
    private TableLayout mTableLayout;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mTableLayout = (TableLayout) findViewById(R.id.tableInvoices);

        mTableLayout.setStretchAllColumns(true);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.Connect();
        //new LoadDataTask().execute(0);

         fetchData();
         button = new Button(this);
            button.setText(R.string.button_back);
            mTableLayout.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    void fetchData() {
        try {



            int index=0;
            int leftRowMargin=0;
            int topRowMargin=0;
            int rightRowMargin=0;
            int bottomRowMargin = 0;
            int textSize = 0, smallTextSize =0, mediumTextSize = 0;

            textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall) *2;
            smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small) * 2;
            mediumTextSize = (int) getResources().getDimension(R.dimen.font_size_medium);

            // Database Part

            Statement st = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM roznosci.wyniki ORDER BY punkty DESC");


            TextView textSpacer = null;

            mTableLayout.removeAllViews();

            // jednorazowe
            textSpacer = new TextView(this);
            textSpacer.setText("");

             TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tv.setGravity(Gravity.LEFT);

            tv.setPadding(5, 15, 0, 15);

            tv.setText("Nick");
            tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);

             TextView tv2 = new TextView(this);

            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);

            tv2.setGravity(Gravity.LEFT);

            tv2.setPadding(5, 15, 0, 15);

            tv2.setText("Sorce");
            tv2.setBackgroundColor(Color.parseColor("#f7f7f7"));

            TableRow tr = new TableRow(this);

            tr.setId(index + 1);

            TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,

                    TableLayout.LayoutParams.WRAP_CONTENT);

            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

            tr.setPadding(0,0,0,0);

            tr.setLayoutParams(trParams);


            tr.addView(tv);

            tr.addView(tv2);


            mTableLayout.addView(tr, trParams);

            while (rs.next()) {

                tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                tv.setGravity(Gravity.LEFT);

                tv.setPadding(5, 15, 0, 15);

                tv.setBackgroundColor(Color.parseColor("#f8f8f8"));

                tv2 = new TextView(this);
                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

                tv2.setGravity(Gravity.LEFT);

                tv2.setPadding(5, 15, 0, 15);

                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

                tv2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv2.setTextColor(Color.parseColor("#000000"));


                   // row.add(rs.getString(i));
                    tv.setText(String.valueOf(rs.getString(1)));
                    tv2.setText(String.valueOf(rs.getString(2)));


                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                index++;
                tr = new TableRow(this);
                tr.setId(index + 1);
                 trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
                tr.setPadding(0,0,0,0);
                tr.setLayoutParams(trParams);

                tr.setId(index + 1);

                tr.addView(tv);

                tr.addView(tv2);

                tr.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TableRow tr = (TableRow) v;
                        //do whatever action is needed
                        reopen();

                    }
                });

                mTableLayout.addView(tr, trParams);
            }






        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void reopen(){
        Intent intent = new Intent(this,ResultsActivity.class);
        startActivity(intent);
        finish();
    }

}