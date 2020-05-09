package com.example.donation40.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import com.example.donation40.R;
import com.example.donation40.adapter.DonationAdapter;


public class ReportActivity extends BaseActivity {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        addControl();
        addEvent();
    }

    private void addControl() {
        listView = findViewById(R.id.reportList);
        DonationAdapter adapter = new DonationAdapter(this,  app.dbManager.getAll());
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
