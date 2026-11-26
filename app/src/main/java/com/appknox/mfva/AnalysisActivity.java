package com.appknox.mfva;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thirdpartysdk.cache.RedisCacheClient;

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
                RedisCacheClient.connect();
                textViewResult.setText("Bundled cache client connected via com.thirdpartysdk.cache");
                Snackbar.make(v, "Analysis complete", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
