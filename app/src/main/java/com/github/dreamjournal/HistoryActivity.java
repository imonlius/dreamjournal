package com.github.dreamjournal;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    public static final int MARGIN_GAP = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System.out.println("hello");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.historyView);

        System.out.println(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        DreamAdapter adapter = new DreamAdapter(Dream.getDreams(context));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DreamDecoration(MARGIN_GAP));
        manager.setOrientation(LinearLayoutManager.VERTICAL);


    }

}
