package com.github.dreamjournal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity for historical dream list for Dreamjournal app
 */
public class HistoryActivity extends AppCompatActivity {

    private static final int MARGIN_GAP = 15;

    /**
     * Method called when HistoryActivity is created
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_history);

        // Create a RecyclerView to hold all of the Dream cards
        RecyclerView recyclerView = findViewById(R.id.historyView);

        // Create a LinearLayoutManager to handle arrangement of the Dream cards
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // Create a DreamAdapter to handle population of the Dream cards
        DreamAdapter adapter = new DreamAdapter(Dream.getDreams(context));

        // Add DreamAdapter, LinearLayoutManager, ItemAnimator, and DreamDecoration to the RecyclerView
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DreamDecoration(MARGIN_GAP));

        // Set the orientation of the LinearLayoutManager for cards to stack vertically
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        // Create a homeButton with transparent background
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setBackgroundResource(0);
        homeButton.bringToFront();
        homeButton.setOnClickListener(unused -> close(new Intent(this, StartActivity.class)));

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
