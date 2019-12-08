package com.github.dreamjournal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView quoteText = findViewById(R.id.quoteText);
        ImageButton closeButton = findViewById(R.id.closeButton);
        closeButton.setBackgroundResource(0);
        closeButton.setOnClickListener(unused -> close(new Intent(this, MainActivity.class)));

        ImageButton historyButton = findViewById(R.id.historyButton);
        historyButton.setBackgroundResource(0);
        historyButton.setOnClickListener(unused -> close(new Intent(this, HistoryActivity.class)));

        String[] quotes = getResources().getStringArray(R.array.quotes);
        quoteText.setText(quotes[(int) (Math.random() * quotes.length)]);
    }

    protected String getDate() {
        Calendar today = new GregorianCalendar();
        String formattedDate = today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
                + " " + today.get(Calendar.DATE) + ", " + today.get(Calendar.YEAR);
        return formattedDate;
    }

    protected void close(Intent nextIntent) {
        nextIntent.putExtra("date", getDate());
        startActivity(nextIntent);
        finish();
    }

}
