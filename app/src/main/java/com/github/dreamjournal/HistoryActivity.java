package com.github.dreamjournal;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System.out.println("hello");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.historyView);

        System.out.println(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        DreamAdapter adapter = new DreamAdapter(getDreams(context));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        manager.setOrientation(LinearLayoutManager.VERTICAL);


    }

    protected List<Dream> getDreams(Context context) {
        String[] dreamFileNames  = context.fileList();
        List<Dream> dreams = new ArrayList<Dream>();
        for (String dreamFileName : dreamFileNames) {
            try {
                FileInputStream fis = context.openFileInput(dreamFileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj == null || ! (obj instanceof Dream)) {
                    throw new ClassCastException();
                }
                dreams.add((Dream) obj);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("problem fetching ya file boi");
            }
        }
        return dreams;
    }

}
