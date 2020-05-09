package com.example.donation50.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.donation50.R;
import com.example.donation50.api.DonationApi;
import com.example.donation50.models.Donation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class DonateActivity extends BaseActivity {

    FloatingActionButton fab;
    Button donateButton;
    RadioGroup paymentMethod;
    ProgressBar progressBar;
    NumberPicker amountPicker;
    EditText amountText;
    TextView amountTotal;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        amountTotal.setText("$" + app.totalDonated);
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
                    Donation donation = new Donation(donatedAmount, method,0, 0);
                    new InsertTask(DonateActivity.this).execute("/donations", donation);
                    app.newDonation(donation);
                    progressBar.setProgress(app.totalDonated);
                    String totalDonatedStr = "$" + app.totalDonated;
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

    @Override
    public void reset() {
        super.reset();
        new ResetTask(this).execute("/donations");
        app.totalDonated = 0;
        amountTotal.setText("$" + app.totalDonated);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetAllTask(this).execute("/donations");
    }

    private class GetAllTask extends AsyncTask<String, Void, List<Donation>> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetAllTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations List");
            this.dialog.show();
        }

        @Override
        protected List<Donation> doInBackground(String... params) {
            try {
                Log.v("donate", "Donation App Getting All Donations");
                return (List<Donation>) DonationApi.getAll((String) params[0]);
            }
            catch (Exception e) {
                Log.v("donate", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);

            //use result to calculate the totalDonated amount here

            app.donations.clear();
            for (Donation i : result) {
                app.donations.add(i);
            }

            app.totalDonated = 0;
            for (Donation i : app.donations)
                app.totalDonated += i.amount;
            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private class InsertTask extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public InsertTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Saving Donation....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("donate", "Donation App Inserting");
                DonationApi.insert((String) params[0], (Donation) params[1]);
            }

            catch(Exception e)
            {
                Log.v("donate","ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private class ResetTask extends AsyncTask<Object, Void, String> {

        protected ProgressDialog         dialog;
        protected Context                 context;

        public ResetTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Donations....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                for (Donation i : app.donations)
                    res = DonationApi.delete((String)params[0], i._id);
                app.donations.clear();
            }

            catch(Exception e)
            {
                Log.v("donate"," RESET ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            app.totalDonated = 0;
            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }
}