package com.github.dreamjournal;

import java.io.Serializable;
import java.net.URL;

public class Dream implements Serializable {

    private final static int FULL_OPACITY_COUNT = 5000;

    private String dream;
    private String date;

    private URL imageURL;

    protected Dream(String currentDate, String text, URL url) {
        dream = text;
        date = currentDate;
        imageURL = url;
    }

    protected String getDream() {
        return dream;
    }

    protected String getDate() {
        return date;
    }

    protected URL getImageURL() {
        return imageURL;
    }

    protected void setDream(String newDream) {
        dream = newDream;
    }

    protected double getTransparency() {
        return Math.min(1.0, (double) getDream().length() / FULL_OPACITY_COUNT);
    }

    protected String getPreview() {
        return getDream().substring(0, 150).replaceAll("(\\n)", "");
    }

}
