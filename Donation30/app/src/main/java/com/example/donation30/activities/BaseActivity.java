package com.example.donation30.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.donation30.R;
import com.example.donation30.models.Donation;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public final int target = 10000;
    public int totalDonated = 0;
    public static List<Donation> donations = new ArrayList<>();

    public boolean newDonation(Donation donation)
    {
        boolean targetAchieved = totalDonated > target;
        if (!targetAchieved)
        {
            donations.add(donation);
            totalDonated += donation.amount;
        }
        else
        {
            Toast toast = Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT);
            toast.show();
        }
        return targetAchieved;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);
        MenuItem report = menu.findItem(R.id.menuReport);
        MenuItem donate = menu.findItem(R.id.menuDonate);

        if(donations.isEmpty())
            report.setEnabled(false);
        else
            report.setEnabled(true);

        if(this instanceof DonateActivity){
            donate.setVisible(false);
            if(!donations.isEmpty())
                report.setVisible(true);
        }
        else {
            report.setVisible(false);
            donate.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                settings();
                break;

            case R.id.menuReport:
                report();
                break;

            case R.id.menuDonate:
                donate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void settings()
    {
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }

    public void report()
    {
        startActivity (new Intent(this, ReportActivity.class));
    }

    public void donate()
    {
        startActivity (new Intent(this, DonateActivity.class));
    }
}
