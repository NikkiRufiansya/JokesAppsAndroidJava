package com.example.jokesappandroidjava.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
    public AdapterJokes.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jokes, parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJokes.MyView holder, int position) {
        holder.setup.setText(data.get(position).getSetup());
        holder.punchline.setText("*"+data.get(position).getPunchline()+"*");
        boolean isExpanded = data.get(position).isExpanded();
        holder.layoutPunchline.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.cardLayout.setOnClickListener(v -> {
            JokesModel jokesModel = data.get(position);
            jokesModel.setExpanded(!jokesModel.isExpanded());
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView setup, punchline;
        public RelativeLayout layoutPunchline;
        public CardView cardLayout;

        public MyView(@NonNull View itemView) {
            super(itemView);
            setup = itemView.findViewById(R.id.setup);
            punchline = itemView.findViewById(R.id.punchline);
            layoutPunchline = itemView.findViewById(R.id.layoutPunchline);
            cardLayout = itemView.findViewById(R.id.cardLayout);
        }
    }
}
