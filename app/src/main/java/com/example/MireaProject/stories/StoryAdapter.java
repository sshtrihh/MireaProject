package com.example.MireaProject.stories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MireaProject.R;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{

    private final LayoutInflater inflater;

    private final List<Story> stories;

    StoryAdapter(Context context, List<Story> stories) {
        this.stories = stories;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.countView.setText(Integer.toString(story.getCount()));
        holder.storyView.setText(story.getStory());
    }


    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView countView, storyView;
        ViewHolder(View view){
            super(view);
            countView = view.findViewById(R.id.countView);
            storyView = view.findViewById(R.id.storyView);
        }
    }
}