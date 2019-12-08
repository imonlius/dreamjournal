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

public class DreamAdapter extends RecyclerView.Adapter {
    private List<Dream> dreams;

    protected class DreamViewHolder extends RecyclerView.ViewHolder {
        protected ImageView dreamImage;
        protected TextView dateText;
        protected TextView previewText;

        protected DreamViewHolder(View view) {
            super(view);
            RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);
            dreamImage = relativeLayout.findViewById(R.id.dreamImage);
            dateText = relativeLayout.findViewById(R.id.dateText);
            previewText = relativeLayout.findViewById(R.id.previewText);

            view.setOnClickListener(unused -> close(view.getContext(), dateText.getText().toString()));
        }
    }

    protected DreamAdapter(List<Dream> dreamsToo) {
        dreams = dreamsToo;
    }

    @Override
    public DreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dreamView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.chunk_dream, parent, false);

        return new DreamViewHolder(dreamView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Dream dream = dreams.get(position);
        ((DreamViewHolder) holder).dateText.setText(dream.getDate());
        ((DreamViewHolder) holder).dreamImage.setImageBitmap(dream.getImage(holder.itemView.getContext()));
        ((DreamViewHolder) holder).previewText.setText(dream.getPreview());
        // holder.dreamImage.setImage

    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }

    protected void close(Context context, String date) {
        Intent nextIntent = new Intent(context, MainActivity.class);
        nextIntent.putExtra("date", date);
        context.startActivity(nextIntent);
    }
}
