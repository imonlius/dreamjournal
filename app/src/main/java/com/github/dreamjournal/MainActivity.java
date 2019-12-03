package com.github.dreamjournal;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateText = findViewById(R.id.journalDate);
        EditText journalText = findViewById(R.id.journalText);
        ImageButton closeButton = findViewById(R.id.finishButton);

        populateDate(dateText);
        closeButton.setBackgroundResource(0);
        closeButton.setOnClickListener(unused -> System.out.println("the app has been closed. "));
    }

    protected void populateDate(TextView text) {
        Calendar today = new GregorianCalendar();
        String formattedDate = today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
                + " " + today.get(Calendar.DATE) + ", " + today.get(Calendar.YEAR);
        text.setText(formattedDate);
    }

}
