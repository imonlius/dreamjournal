package com.github.dreamjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for the journal page of the Dreamjournal app.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Method that is called when MainActivity is created
     * @param savedInstanceState Bundle savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);

        TextView dateText = findViewById(R.id.journalDate);
        EditText journalText = findViewById(R.id.journalText);
        ImageButton closeButton = findViewById(R.id.finishButton);
        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        // Retrieve the Dream using the "date" extra from the launched Intent
        dateText.setText(getIntent().getStringExtra("date"));
        Dream dream = Dream.getDream(dateText.getText().toString(), getApplicationContext());

        // Set journalText to text from Dream and add DreamWatcher listener
        journalText.setText(dream.getDream());
        journalText.addTextChangedListener(new DreamWatcher(dream, getApplicationContext()));

        // Set the backgroundImage to the image from the Dream object
        backgroundImage.setImageBitmap(dream.getImage(getApplicationContext()));

        // Make background of closeButton transparent
        closeButton.setBackgroundResource(0);
        // Add an onClickListener to closeButton to return to StartActivity
        closeButton.setOnClickListener(unused -> close(new Intent(this, StartActivity.class)));

    }

    /**
     * Close the current activity and launch the next activity
     * @param nextIntent Intent for the next activity
     */
    private void close(Intent nextIntent) {
        startActivity(nextIntent);
        finish();
    }

}
