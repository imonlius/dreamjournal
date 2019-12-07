package com.github.dreamjournal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public final static String RANDOM_PICTURE_URL = "https://picsum.photos/2160/3840";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        TextView dateText = findViewById(R.id.journalDate);
        EditText journalText = findViewById(R.id.journalText);
        ImageButton closeButton = findViewById(R.id.finishButton);
        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        populateDate(dateText);

        URL imageURL = populateDream(dateText.getText().toString(), getApplicationContext(), journalText);
        if (imageURL == null) {
            imageURL = getRandomImage();
        }

        closeButton.setBackgroundResource(0);
        closeButton.setOnClickListener(unused -> close(new Intent(this, StartActivity.class)));

        try {
            Bitmap bmp = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            backgroundImage.setImageBitmap(bmp);
            journalText.addTextChangedListener(new DreamWatcher(dateText.getText().toString(), getApplicationContext(), imageURL));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected URL getRandomImage() {
        URL pictureURL = null;
        try {
            URLConnection con = new URL(RANDOM_PICTURE_URL).openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            pictureURL = con.getURL();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pictureURL;
    }

    protected void populateDate(TextView text) {
        Calendar today = new GregorianCalendar();
        String formattedDate = today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
                + " " + today.get(Calendar.DATE) + ", " + today.get(Calendar.YEAR);
        text.setText(formattedDate);
    }

    protected void close(Intent nextIntent) {
        System.out.println("ok old man");
        startActivity(nextIntent);
        // System.out.println("");
        finish();
    }

    protected URL populateDream(String date, Context context, EditText dreamText) {
        try {
            FileInputStream fis = context.openFileInput(date + ".dream");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if (obj == null || ! (obj instanceof Dream)) {
                throw new ClassCastException();
            }
            dreamText.setText(((Dream) obj).getDream());
            return ((Dream) obj).getImageURL();
        } catch (Exception e) {
            System.out.println("problem fetching ya file boi");
        }
        return null;
    }

}
