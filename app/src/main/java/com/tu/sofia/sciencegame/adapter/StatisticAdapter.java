package com.tu.sofia.sciencegame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.entity.Statistic;

import java.util.List;

/**
 * Created by Aleksandar Kovachev on 06.05.2018 г..
 */
public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {

    private Context context;

    private static List<Statistic> data;

    public StatisticAdapter(Context context, List<Statistic> objects) {
        this.context = context;
        this.data = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statistic, parent, false);
        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView statisticRow, statisticUsername, statisticPoints;

        ViewHolder(View view) {
            super(view);

            statisticRow = view.findViewById(R.id.statistic_row);
            statisticUsername = view.findViewById(R.id.statistic_username);
            statisticPoints = view.findViewById(R.id.statistic_points);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Statistic statistic = data.get(position);
        holder.statisticRow.setText(String.valueOf(position + 1) + ".");
        holder.statisticUsername.setText(statistic.getUser().getUsername());
        holder.statisticPoints.setText(String.format(context.getString(R.string.questionNumber), String.valueOf(statistic.getCorrectedAnswers()), String.valueOf(statistic.getTotalAnswers())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}