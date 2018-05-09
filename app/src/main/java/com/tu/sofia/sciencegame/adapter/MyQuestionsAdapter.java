package com.tu.sofia.sciencegame.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.entity.Question;

import java.util.List;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class MyQuestionsAdapter extends RecyclerView.Adapter<MyQuestionsAdapter.ViewHolder> {

    private Context context;

    private static List<Question> data;

    public MyQuestionsAdapter(Context context, List<Question> objects) {
        this.context = context;
        this.data = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_questions, parent, false);
        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, correctlyAnswered, totalAnswers, description;
        ImageView questionState;

        ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.my_question_title);
            description = view.findViewById(R.id.my_description_description);
            correctlyAnswered = view.findViewById(R.id.my_question_correctly_answered);
            totalAnswers = view.findViewById(R.id.my_question_total_answers);
            questionState = view.findViewById(R.id.my_question_state);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Question question = data.get(position);

        holder.title.setText(question.getQuestion());
        holder.description.setText(question.getCorrectDescription());
        holder.correctlyAnswered.setText(String.valueOf(question.getCorrectlyAnswered()));
        holder.totalAnswers.setText(String.valueOf(question.getTotalAnswers()));

        if (question.isApproved()) {
            holder.questionState.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_approved));
        } else {
            holder.questionState.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_not_approved));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
