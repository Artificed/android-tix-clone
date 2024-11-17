package com.example.onlymovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlymovie.R;
import com.example.onlymovie.models.Cast;

import java.util.ArrayList;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Cast> casts;
    private String baseImageUrl = "https://image.tmdb.org/t/p/w300/";

    public CreditAdapter(Context context, ArrayList<Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    // ViewHolder to hold references to the views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView actorName;
        ImageView actorImage;

        public ViewHolder(View itemView) {
            super(itemView);
            actorName = itemView.findViewById(R.id.movieActorText);  // TextView for actor name
            actorImage = itemView.findViewById(R.id.movieActorImage);  // ImageView for actor image
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_credit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the cast member for the current position
        Cast castMember = casts.get(position);

        // Set the actor name in the TextView
        holder.actorName.setText(castMember.getName());

        // Load the actor image using Glide
        String imageUrl = castMember.getProfile_path();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(baseImageUrl + imageUrl + "?api_key=d87f651a6b4efe803d9bb8e7b6cc5871")
                    .error(R.drawable.ic_launcher_background)  // Placeholder if the image fails to load
                    .into(holder.actorImage);
        } else {
            holder.actorImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return casts.size();  // Return the size of your data list
    }
}

