package com.github.dreamjournal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DreamAdapter extends RecyclerView.Adapter {
    private List<Dream> dreams;

    protected class DreamViewHolder extends RecyclerView.ViewHolder {
        protected ImageView dreamImage;
        protected ImageView darkImage;
        protected TextView dateText;
        protected Button shareButton;
        protected TextView previewText;

        protected DreamViewHolder(View view) {
            super(view);
            RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);
            dreamImage = relativeLayout.findViewById(R.id.dreamImage);
            darkImage = relativeLayout.findViewById(R.id.darkImage);
            dateText = relativeLayout.findViewById(R.id.dateText);
            shareButton = relativeLayout.findViewById(R.id.shareButton);
            previewText = relativeLayout.findViewById(R.id.previewText);

            shareButton.setOnClickListener(unused -> System.out.println("Shared! :) not really. "));
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
        ((DreamViewHolder) holder).previewText.setText(dream.getPreview());
        // holder.dreamImage.setImage

    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }
}
