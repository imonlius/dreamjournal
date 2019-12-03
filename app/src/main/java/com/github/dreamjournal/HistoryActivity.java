package com.github.dreamjournal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System.out.println("hello");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.historyView);

        System.out.println(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        // manager.setOrientation(LinearLayoutManager.VERTICAL);

        DreamAdapter adapter = new DreamAdapter(getDreams());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    protected List<Dream> getDreams() {
        Dream myDream = new Dream("April 7, 2001", getResources().getStringArray(R.array.quotes)[3], null);
        Dream myDreamToo = new Dream("April 10th, 2001", getResources().getStringArray(R.array.quotes)[2], null);
        return new ArrayList<>(Arrays.asList(myDream, myDreamToo));
    }

}
