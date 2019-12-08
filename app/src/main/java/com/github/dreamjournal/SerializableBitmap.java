package com.github.dreamjournal;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SerializableBitmap implements Serializable {

    private int[] pixels;
    private int height;
    private int width;

    public SerializableBitmap(Bitmap bitmap) {
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
    }

    public Bitmap getBitmap() {
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }

}
