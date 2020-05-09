package com.example.donation30.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.donation30.R;
import com.example.donation30.models.Donation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class DonateActivity extends BaseActivity {

    FloatingActionButton fab;
    Button donateButton;
    RadioGroup paymentMethod;
    ProgressBar progressBar;
    NumberPicker amountPicker;
    EditText amountText;
    TextView amountTotal;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControl();
        addEvent();
    }

    private void addControl() {
        fab = findViewById(R.id.fab);
        donateButton = findViewById(R.id.donateButton);
        paymentMethod = findViewById(R.id.paymentMethod);
        progressBar = findViewById(R.id.progressBar);
        amountPicker = findViewById(R.id.amountPicker);
        amountText = findViewById(R.id.paymentAmount);
        amountTotal = findViewById(R.id.totalSoFar);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);

        progressBar.setMax(10000);
    }

    private void addEvent() {

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method = paymentMethod.getCheckedRadioButtonId() == R.id.PayPal ? "PayPal" : "Direct";
                int donatedAmount =  amountPicker.getValue();
                if (donatedAmount == 0)
                {
                    String text = amountText.getText().toString();
                    if (!text.equals(""))
                        donatedAmount = Integer.parseInt(text);
                }
                if (donatedAmount > 0)
                {
                    newDonation(new Donation(donatedAmount, method));
                    progressBar.setProgress(totalDonated);
                    String totalDonatedStr = "$" + totalDonated;
                    amountTotal.setText(totalDonatedStr);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
