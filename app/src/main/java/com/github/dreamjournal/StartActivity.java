package com.github.dreamjournal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Activity for the starting home screen of the Dreamjournal app.
 */
public class StartActivity extends AppCompatActivity {

    /**
     * Method that is called when StartActivity is created.
     * @param savedInstanceState savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageButton closeButton = findViewById(R.id.closeButton);
        // Make background transparent for closeButton
        closeButton.setBackgroundResource(0);
        // Add onClickListener to go to the MainActivity class
        closeButton.setOnClickListener(unused -> close(new Intent(this, MainActivity.class)));

        ImageButton historyButton = findViewById(R.id.historyButton);
        // Make background transparent for historyButton
        historyButton.setBackgroundResource(0);
        // Add onClickListener to go to the HistoryActivity class
        historyButton.setOnClickListener(unused -> close(new Intent(this, HistoryActivity.class)));

        // Retrieve random quote from R.array.quotes to display
        String[] quotes = getResources().getStringArray(R.array.quotes);
        TextView quoteText = findViewById(R.id.quoteText);
        quoteText.setText(quotes[(int) (Math.random() * quotes.length)]);
    }

    /**
     * Returns the formatted date of today.
     * @return String with today's date formatted as MONTH, DATE, YEAR
     */
    private String getDate() {
        // Get today's date formatted as MONTH, DATE, YEAR
        Calendar today = new GregorianCalendar();
        return today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
                + " " + today.get(Calendar.DATE) + ", " + today.get(Calendar.YEAR);
    }

    /**
     * Closes the current activity and launches a new one
     * @param nextIntent Intent for the next Activity
     */
    private void close(Intent nextIntent) {
        // Put in the "date" extra for MainActivity to load the proper journal date
        nextIntent.putExtra("date", getDate());
        startActivity(nextIntent);
        finish();
    }

}
