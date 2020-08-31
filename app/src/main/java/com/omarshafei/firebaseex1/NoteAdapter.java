package com.omarshafei.firebaseex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private Context context;
    private ArrayList<Note> data;
    private LayoutInflater layoutInflater;

    public NoteAdapter(Context context, ArrayList<Note> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentElement = data.get(position);
        String text = "Title: "+ currentElement.getTitle() + "\n"+ "Description: "+ currentElement.getDescription() + "\n\n"; 
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_layout);
        }
    }
}
