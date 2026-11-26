package com.appknox.mfva;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_analysis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textViewResult = (TextView) findViewById(R.id.textview_analysis);
        Button buttonStartAnalysis = (Button) findViewById(R.id.button_start_analysis);

        buttonStartAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject report = new JsonObject();
                report.addProperty("scanner", "sherlock");
                report.addProperty("target", "com.appknox.mfva");
                report.addProperty("status", "analyzed");

                Gson gson = new Gson();
                String serializedReport = gson.toJson(report);

                JsonObject parsedReport = gson.fromJson(serializedReport, JsonObject.class);
                textViewResult.setText(parsedReport.toString());

                Snackbar.make(v, "Analysis report serialized with Gson", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
