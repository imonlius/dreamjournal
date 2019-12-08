package com.github.dreamjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);

        TextView dateText = findViewById(R.id.journalDate);
        EditText journalText = findViewById(R.id.journalText);
        ImageButton closeButton = findViewById(R.id.finishButton);
        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        dateText.setText(getIntent().getStringExtra("date"));
        Dream dream = Dream.getDream(dateText.getText().toString(), getApplicationContext());

        journalText.setText(dream.getDream());
        journalText.addTextChangedListener(new DreamWatcher(dream, getApplicationContext()));

        backgroundImage.setImageBitmap(dream.getImage(getApplicationContext()));

        closeButton.setBackgroundResource(0);
        closeButton.setOnClickListener(unused -> close(new Intent(this, StartActivity.class)));

    }

    protected void close(Intent nextIntent) {
        startActivity(nextIntent);
        finish();
    }

}
