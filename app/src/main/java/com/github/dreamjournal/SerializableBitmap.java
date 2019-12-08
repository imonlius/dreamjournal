package com.github.dreamjournal;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * SerializableBitmap enables serialization of a Bitmap object
 */
public class SerializableBitmap implements Serializable {

    private int[] pixels;
    private int height;
    private int width;

    /**
     * Constructor that wraps a SerializableBitmap around a Bitmap object
     * @param bitmap Bitmap image to wrap around
     */
    protected SerializableBitmap(Bitmap bitmap) {
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
    }

    /**
     * Returns the Bitmap object wrapped by the SerializableBitmap
     * @return Bitmap object wrapped by SerializableBitmap
     */
    protected Bitmap getBitmap() {
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }

}
