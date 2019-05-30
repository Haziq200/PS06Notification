package com.myapplicationdev.android.ps06_notification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aa;
    ArrayList<Task> taskal;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btnAddTask);

        taskal = new ArrayList<Task>();
        aa = new TaskAdapter(this,R.layout.custom_layout,taskal);

        DBHelper db = new DBHelper(MainActivity.this);
        ArrayList<Task> data = db.getAllTask();
        aa = new TaskAdapter(this, R.layout.custom_layout, data);
        aa.notifyDataSetChanged();

        lv.setAdapter(aa);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        AddTask.class);
                startActivity(i);
            }
        });

    }


}
