package com.github.dreamjournal;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;

public class DreamWatcher implements TextWatcher {

    private String date;
    private Context context;
    private URL url;

    public DreamWatcher(String d, Context c, URL u) {
        date = d;
        context = c;
        url = u;
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Dream dream = new Dream(date, charSequence.toString(), url);
        String filename = date + ".dream";
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dream);
            oos.close();
        } catch (IOException e) {
            System.out.println("Help please. <3");
        }

    }
}