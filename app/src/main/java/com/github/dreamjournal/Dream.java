package com.github.dreamjournal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.File;
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

/**
 * Dream object contains all relevant details to each Dream
 */
public class Dream implements Serializable {

    /* URL from which to retrieve random pictures for the Dream */
    private final static String RANDOM_PICTURE_URL = "https://picsum.photos/2160/3840";

    private String dream;
    private String date;

    private URL imageURL;
    private String imageFileName;

    /**
     * Constructs a new Dream object from the currentDate, dream text, and image URL
     * @param d Date for the Dream object
     * @param text Text of the Dream
     * @param url URL for the Dream image
     */
    private Dream(String d, String text, URL url) {
        date = d;
        dream = text;
        imageURL = url;
    }

    /**
     * Constructs a new Dream object from only the date
     * @param d Date for the Dream object
     */
    private Dream(String d) {
        this(d, "", null);
    }

    /**
     * Returns the text of the Dream
     * @return Dream text
     */
    protected String getDream() {
        return dream;
    }

    /**
     * Returns the date of the Dream
     * @return Dream date as a formatted String (MONTH DATE, YEAR)
     */
    protected String getDate() {
        return date;
    }

    /**
     * Sets the text of the Dream
     * @param newDream New text of the Dream
     */
    protected void setDream(String newDream) {
        dream = newDream;
    }

    /**
     * Returns the image URL of the Dream
     * @return URL of the Dream image
     */
    private URL getImageURL() {

        // Configure proper policies to connect to the Internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // If object already has imageURL, return it
        if (imageURL != null) {
            System.out.println("I HAVE: " + imageURL);
            return imageURL;
        }
        // If object does not have an imageURL, try to generate a new one
        try {
            // Connect to RANDOM_PICTURE_URL and obtain redirected static URL
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

    /**
     * Returns a preview of the Dream string with the first 150 non-newline characters
     * @return The first 150 non-newline characters of the Dream string
     */
    protected String getPreview() {
        String dreamPreview = getDream().replaceAll("(\\n)", "");
        // Handle if the dream itself is shorter than 150 characters
        if (dreamPreview.length() < 150) {
            return dreamPreview;
        }
        return dreamPreview.substring(0, 150);
    }

    /**
     * Get the file name of the Dream image
     * @return File name of the Dream image
     */
    private String getImageFileName(){
        if (imageFileName != null) {
            return imageFileName;
        }
        try {
            // Get an md5 hash of the Image URL as the file name
            // Using a hash enables a "unique" identifier token
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Convert md5 hash to a BigInteger to convert hash to an alphanumeric String
            BigInteger no = new BigInteger(1, md.digest(getImageURL().toString().getBytes()));
            // Append a ".bmp" extension
            imageFileName = no.toString(16) + ".bmp";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return imageFileName;
    }

    /**
     * Retrieve the image of the Dream as a Bitmap
     * @param context Context from which to retrieve image as a File
     * @return Dream image as a Bitmap
     */
    protected Bitmap getImage(Context context) {

        // Attempt to get the Bitmap image from internal storage
        Bitmap bmp = getStoredImage(context);

        // If Bitmap image is not in internal storage, continue
        if (bmp != null) {
            System.out.println("Retrieved it from cache!");
            return bmp;
        }
        System.out.println("Pulling from web.");

        // Security policies to allow Internet connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Connect to the image URL to download the image as a Bitmap
        try {
            bmp = BitmapFactory.decodeStream(getImageURL().openConnection().getInputStream());
            // Store Bitmap image in internal storage for later retrieval
            storeBitmapImage(context, bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    /**
     * Store Dream image as a Bitmap image for quick retrieval
     * @param context Context object with which to store files
     * @param bmp Bitmap image to be stored
     */
    private void storeBitmapImage(Context context, Bitmap bmp) {
        // Write Bitmap image to a File
        try {
            FileOutputStream fos = context.openFileOutput(getImageFileName(), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // Wrap Bitmap in SerializableBitmap to enable serialization to a file
            oos.writeObject(new SerializableBitmap(bmp));

            System.out.println("I've successfully written to " + getImageFileName());

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve Dream image as a Bitmap image from device storage
     * @param context Context object with which to retrieve files
     * @return Bitmap image of Dream
     */
    private Bitmap getStoredImage(Context context) {

        // If file does not exist, return null
        File f = context.getFileStreamPath(getImageFileName());
        if (!f.exists()) {
            return null;
        }

        // Try to open the file from the image file name
        try {
            FileInputStream fis = context.openFileInput(getImageFileName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();

            System.out.println("I'm pulling from: " + getImageFileName());

            ois.close();
            fis.close();

            if (!(obj instanceof SerializableBitmap)) {
                throw new Exception();
            }
            // Unwrap the SerializableBitmap to return Bitmap object
            return ((SerializableBitmap) obj).getBitmap();
        } catch (Exception e) {
            System.out.println("problem fetching ya file boi");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the Dream object from file
     * @param date Date of Dream object to retrieve
     * @param context Context object with which to retrieve files
     * @return Dream object from file
     */
    public static Dream getDream(String date, Context context) {

        String dreamPath = date + ".dream";

        File f = context.getFileStreamPath(dreamPath);
        if (f.exists()) {
            // If file does exist, attempt to read it from the file
            try {
                FileInputStream fis = context.openFileInput(dreamPath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();

                System.out.println("I got: " + dreamPath);

                ois.close();
                fis.close();

                if (!(obj instanceof Dream)) {
                    throw new Exception();
                }
                return (Dream) obj;
            } catch (Exception e) {
                System.out.println("problem fetching ya file boi");
                e.printStackTrace();
            }
        }

        // If file doesn't exist, return an empty Dream with the date
        return new Dream(date);
    }

    /**
     * Get a List of all the Dreams in the Context file system
     * @param context Context object from which to retrieve files
     * @return List of all Dreams
     */
    protected static List<Dream> getDreams(Context context) {
        // Obtain a list of all the files present in the Context
        String[] dreamFileNames = context.fileList();
        List<Dream> dreams = new ArrayList<>();

        // Iterate through all file names and add Dreams to dreams list
        for (int j = dreamFileNames.length - 1; j >= 0; j--) {
            System.out.println(dreamFileNames[j]);

            // If file is an image, skip it.
            if (dreamFileNames[j].contains(".bmp")) {
                continue;
            }

            // If file is not an image, try reading it
            try {
                FileInputStream fis = context.openFileInput(dreamFileNames[j]);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();

                ois.close();
                fis.close();

                if (!(obj instanceof Dream)) {
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
