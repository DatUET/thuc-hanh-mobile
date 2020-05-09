package com.example.donation20.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.donation20.R;

public class ReportActivity extends AppCompatActivity {

    ListView listView;
    static final String[] numbers = new String[] {
            "Amount, Pay method",
            "10,     Direct",
            "100,    PayPal",
            "1000,   Direct",
            "10,     PayPal",
            "5000,   PayPal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        addControl();
        addEvent();
    }

    private void addControl() {
        listView = findViewById(R.id.reportList);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,  android.R.layout.simple_list_item_1, numbers);
        listView.setAdapter(adapter);
    }

    private void addEvent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
