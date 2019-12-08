package com.github.dreamjournal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Dream implements Serializable {

    private final static int FULL_OPACITY_COUNT = 5000;
    public final static String RANDOM_PICTURE_URL = "https://picsum.photos/2160/3840";

    private String dream;
    private String date;

    private URL imageURL;


    protected Dream(String currentDate, String text, URL url) {
        date = currentDate;
        dream = text;
        imageURL = url;
    }

    protected Dream(String currentDate) {
        this(currentDate, "", null);
        System.out.println("I'm creating a new dream with the date: " + currentDate);
    }

    protected String getDream() {
        return dream;
    }

    protected String getDate() {
        return date;
    }

    protected void setDream(String newDream) {
        dream = newDream;
    }

    protected double getTransparency() {
        return Math.min(1.0, (double) getDream().length() / FULL_OPACITY_COUNT);
    }

    protected URL getImageURL() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (imageURL != null) {
            System.out.println("I HAVE: " + imageURL);
            return imageURL;
        }
        try {
            URLConnection con = new URL(RANDOM_PICTURE_URL).openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            imageURL = con.getURL();
            System.out.println("RENEW: " + imageURL);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageURL;

    }

    protected String getPreview() {
        String dreamPreview = getDream().replaceAll("(\\n)", "");
        if (dreamPreview.length() < 150) {
            return dreamPreview;
        }
        return dreamPreview.substring(0, 150);
    }

    protected String getImageFileName(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger no = new BigInteger(1, md.digest(getImageURL().toString().getBytes()));
            return no.toString(16) + ".bmp";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Bitmap getImage(Context context) {

        Bitmap bmp = getStoredImage(context);
        if (bmp != null) {
            System.out.println("Retrieved it from cache!");
            return bmp;
        }
        System.out.println("Pulling from web.");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            bmp = BitmapFactory.decodeStream(getImageURL().openConnection().getInputStream());
            storeBitmapImage(context, bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public void storeBitmapImage(Context context, Bitmap bmp) {
        System.out.println("HELLLLOOOOO");
        try {
            System.out.println("I'm going to try to write to " + getImageFileName());
            FileOutputStream fos = context.openFileOutput(getImageFileName(), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new SerializableBitmap(bmp));

            System.out.println("I've successfully written to " + getImageFileName());

            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Help please. <3");
            e.printStackTrace();
        }
    }

    public Bitmap getStoredImage(Context context) {
        try {
            FileInputStream fis = context.openFileInput(getImageFileName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();

            System.out.println("I'm pulling from: " + getImageFileName());

            ois.close();
            fis.close();

            if (obj == null || ! (obj instanceof SerializableBitmap)) {
                throw new ClassCastException();
            }
            return ((SerializableBitmap) obj).getBitmap();
        } catch (Exception e) {
            System.out.println("problem fetching ya file boi");
        }
        return null;
    }

    public static Dream getDream(String date, Context context) {
        try {
            FileInputStream fis = context.openFileInput(date + ".dream");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();

            ois.close();
            fis.close();

            if (obj == null || ! (obj instanceof Dream)) {
                throw new ClassCastException();
            }
            return (Dream) obj;
        } catch (Exception e) {
            System.out.println("problem fetching ya file boi");
        }
        return new Dream(date);
    }

    protected static List<Dream> getDreams(Context context) {
        String[] dreamFileNames  = context.fileList();
        List<Dream> dreams = new ArrayList<Dream>();
        for (String dreamFileName : dreamFileNames) {
            System.out.println(dreamFileName);
            try {
                FileInputStream fis = context.openFileInput(dreamFileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();

                ois.close();
                fis.close();

                if (obj == null || ! (obj instanceof Dream)) {
                    throw new Exception();
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
