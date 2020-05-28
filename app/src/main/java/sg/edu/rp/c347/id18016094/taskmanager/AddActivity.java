package sg.edu.rp.c347.id18016094.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    EditText etName, etDesc;
    Button btnAddT, btnCancel;
    int reqCode = 12345;

    ArrayList<Task> al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etDesc = findViewById(R.id.etDescription);
        etName = findViewById(R.id.etName);
        btnAddT = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAddT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                Intent intent = new Intent(AddActivity.this,
                        BroadBoardReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                String data1 = etName.getText().toString();
                String data2 = etDesc.getText().toString();

                DBHelper dbh = new DBHelper(AddActivity.this);
                long inserted_id = dbh.insertTask(data1, data2);
                dbh.close();

                if (inserted_id != -1) {
                    Toast.makeText(AddActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();

            }
        });
    }

}