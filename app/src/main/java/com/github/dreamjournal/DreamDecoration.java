package com.github.dreamjournal;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * DreamDecoration class enables padding margins around the Dream cards in the RecyclerView
 */
public class DreamDecoration extends RecyclerView.ItemDecoration {

    private int space;

    /**
     * Construct a DreamDecoration with a set padding margin
     * @param s Padding margin size
     */
    public DreamDecoration(int s) {
        space = s;
    }

    /**
     * Set the item offsets for the Rect object of the Dream cards
     * @param outRect the Rect object of the Dream card
     * @param view the View of the Dream card
     * @param parent the RecyclerView parent of the Dream card
     * @param state the RecyclerView State
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Only add a top margin if the card is the first in the RecyclerView
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
