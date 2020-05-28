package sg.edu.rp.c347.id18016094.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView listlist;
    ArrayList<Task> al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAddTask);
        listlist = findViewById(R.id.list);

        //Put inside list view
        DBHelper  dbh = new DBHelper(MainActivity.this);
        al = dbh.getTasks();
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        listlist.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, 9);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper dbh = new DBHelper(MainActivity.this);
            //Line 52-55 is to refresh list view
            al.clear();
            al.addAll(dbh.getTasks());
            dbh.close();
            aa.notifyDataSetChanged();
        }
    }
}
