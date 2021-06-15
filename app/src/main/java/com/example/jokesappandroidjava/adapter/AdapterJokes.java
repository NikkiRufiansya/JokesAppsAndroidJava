package com.example.jokesappandroidjava.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesappandroidjava.DetailJokes;
import com.example.jokesappandroidjava.R;
import com.example.jokesappandroidjava.model.JokesModel;

import java.util.List;

public class AdapterJokes extends RecyclerView.Adapter<AdapterJokes.MyView> {
    private Context context;
    private List<JokesModel> data;

    public AdapterJokes(Context context, List<JokesModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AdapterJokes.MyView onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jokes,parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterJokes.MyView holder, int position) {
        holder.setup.setText(data.get(position).getSetup());
        holder.btnSeeNow.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailJokes.class);
            intent.putExtra("id", data.get(position).getId());
            intent.putExtra("type", data.get(position).getType());
            intent.putExtra("setup", data.get(position).getSetup());
            intent.putExtra("punchline", data.get(position).getPunchline());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView setup, btnSeeNow;
        public MyView(@NonNull View itemView) {
            super(itemView);
            setup = itemView.findViewById(R.id.setup);
            btnSeeNow = itemView.findViewById(R.id.btnSeeNow);
        }
    }
}
