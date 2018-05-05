package com.tu.sofia.sciencegame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.entity.Answer;

import java.util.List;

import info.hoang8f.widget.FButton;

/**
 * Created by Aleksandar Kovachev on 02.05.2018 г..
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private Context context;

    private List<Answer> answers;
    private Answer correctAnswer;
    private View.OnClickListener correctClickListener;
    private View.OnClickListener wrongClickListener;

    public AnswerAdapter(Context context, List<Answer> answers, Answer correctAnswer, View.OnClickListener correctClickListener, View.OnClickListener wrongClickListener) {
        this.context = context;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.correctClickListener = correctClickListener;
        this.wrongClickListener = wrongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer, parent, false);
        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FButton answerButton;

        ViewHolder(View view) {
            super(view);
            answerButton = view.findViewById(R.id.answer);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Answer answer = answers.get(position);
        holder.answerButton.setText(answer.getName());

        if (answer.equals(correctAnswer)) {
            holder.answerButton.setOnClickListener(correctClickListener);
        } else {
            holder.answerButton.setOnClickListener(wrongClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

}