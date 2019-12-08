package com.github.dreamjournal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * DreamAdapter class handles population of Dream cards
 */
public class DreamAdapter extends RecyclerView.Adapter {
    private List<Dream> dreams;

    /**
     * DreamViewHolder inner class holds a View for the Dream card
     */
    protected class DreamViewHolder extends RecyclerView.ViewHolder {
        private ImageView dreamImage;
        private TextView dateText;
        private TextView previewText;

        /**
         * Constructs a DreamViewHolder wrapped around the View view.
         * @param view View that DreamViewHolder will be wrapped around
         */
        protected DreamViewHolder(View view) {
            super(view);
            // Retrieve the image, date, and preview objects for the card
            RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);
            dreamImage = relativeLayout.findViewById(R.id.dreamImage);
            dateText = relativeLayout.findViewById(R.id.dateText);
            previewText = relativeLayout.findViewById(R.id.previewText);

            // Add an onClickListener to the entire card
            view.setOnClickListener(unused -> close(view.getContext(), dateText.getText().toString()));
        }
    }

    /**
     * Construct a DreamAdapter with a List<Dream> of all the Dreams
     * @param dreamsToo the List of all the Dreams
     */
    protected DreamAdapter(List<Dream> dreamsToo) {
        dreams = dreamsToo;
    }

    /**
     * Method to inflate the dream card chunk when the ViewHolder is created
     * @param parent The parent ViewGroup of the ViewHolder
     * @param viewType The viewType of the ViewHolder
     * @return A new DreamViewHolder object wrapped around the inflated dream card chunk
     */
    @Override
    public DreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the Dream card chunk
        View dreamView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.chunk_dream, parent, false);

        return new DreamViewHolder(dreamView);
    }

    /**
     * Method called when ViewHolder is bound and all attributes are populated.
     * @param holder The ViewHolder that contains the View to be populated
     * @param position The position of the ViewHolder in the RecyclerView
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Retrieve the corresponding dream from the List of Dreams
        Dream dream = dreams.get(position);
        // Populate the dateText, dreamImage, and previewText from the Dream object
        ((DreamViewHolder) holder).dateText.setText(dream.getDate());
        ((DreamViewHolder) holder).dreamImage.setImageBitmap(dream.getImage(holder.itemView.getContext()));
        ((DreamViewHolder) holder).previewText.setText(dream.getPreview());
    }

    /**
     * Gets the total number of Dreams in the DreamAdapter object
     * @return Total number of Dreams in the DreamAdapter object
     */
    @Override
    public int getItemCount() {
        return dreams.size();
    }

    /**
     * Closes the HistoryActivity activity and starts new MainActivity for the journal date
     * @param context Context object
     * @param date Date to feed MainActivity to load desired journal date
     */
    private void close(Context context, String date) {
        // Create Intent for next activity
        Intent nextIntent = new Intent(context, MainActivity.class);
        // Add "date" extra for MainActivity to load the desired journal date
        nextIntent.putExtra("date", date);
        context.startActivity(nextIntent);
    }
}
