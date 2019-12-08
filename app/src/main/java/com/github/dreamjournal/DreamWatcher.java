package com.github.dreamjournal;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * DreamWatcher is a TextWatcher that saves the Dream to a file when text is updated.
 */
public class DreamWatcher implements TextWatcher {

    private Dream dream;
    private Context context;

    /**
     * Constructs a new DreamWatcher with an attached Dream and Context
     * @param d Dream object to watch
     * @param c Context with which to save files
     */
    protected DreamWatcher(Dream d, Context c) {
        dream = d;
        context = c;
    }

    /**
     * Method called after text is changed. Currently does nothing.
     * @param editable Editable that has been changed
     */
    @Override
    public void afterTextChanged(Editable editable) {
    }

    /**
     * Method called before text is changed. Currently does nothing.
     * @param charSequence Sequence of characters in the EditText.
     * @param i Number of characters changed.
     * @param i1 Starting index of characters changed.
     * @param i2 Ending index of characters changed.
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    /**
     * Method called when text is changed. Serializes and saves the Dream to a File.
     * @param charSequence Sequence of characters in the EditText.
     * @param i Number of characters changed.
     * @param i1 Starting index of characters changed.
     * @param i2 Ending index of characters changed.
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Construct a new Dream object from the date, Dream text, and URL
        dream.setDream(charSequence.toString());
        // Create the file name using the dream's date
        String filename = dream.getDate() + ".dream";
        // Try to serialize the Dream and write it to a File in the Context
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dream);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("We've hit a snag: IO EXCEPTION (onTextChanged)");
        }

    }
}
