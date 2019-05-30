package com.myapplicationdev.android.ps06_notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnAdd, btnCancel;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnAdd = findViewById(R.id.buttonAdd);
        btnCancel = findViewById(R.id.buttonCancel);

        etName = findViewById(R.id.editTextName);
        etDesc = findViewById(R.id.editTextDescription);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();

                DBHelper db = new DBHelper(AddTask.this);
                long row_affected = db.insertTask(name, desc);
                db.close();

                if (row_affected != -1) {
                    Toast.makeText(AddTask.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }

                DBHelper dbHelper = new DBHelper(AddTask.this);
                db.insertTask(name, desc);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);


                Intent intent = new Intent(AddTask.this,
                        TaskBroadcastReceiver.class);
                intent.putExtra("name", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddTask.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);


                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
                Toast.makeText(getBaseContext(), "Task Inserted", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
