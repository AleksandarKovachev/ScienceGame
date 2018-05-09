package com.tu.sofia.sciencegame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.entity.Answer;
import com.tu.sofia.sciencegame.entity.Question;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Aleksandar Kovachev on 10.05.2018 г..
 */
public class ApproveQuestionAdapter extends RecyclerView.Adapter<ApproveQuestionAdapter.ViewHolder> {

    private Context context;

    private static List<Question> data;

    private Realm realm;

    public ApproveQuestionAdapter(Context context, List<Question> objects, Realm realm) {
        this.context = context;
        this.data = objects;
        this.realm = realm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_approve_question, parent, false);
        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, correctAnswer, wrongAnswers;
        ImageButton approveQuestion, discardQuestion;

        ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.question_title);
            description = view.findViewById(R.id.question_description);
            correctAnswer = view.findViewById(R.id.correct_answer);
            wrongAnswers = view.findViewById(R.id.wrong_answers);
            approveQuestion = view.findViewById(R.id.approve_question);
            discardQuestion = view.findViewById(R.id.discard_question);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Question question = data.get(position);

        holder.title.setText(question.getQuestion());
        holder.description.setText(question.getCorrectDescription());
        holder.correctAnswer.setText("Верен отговор: " + String.valueOf(question.getCorrectAnswer().getName()));

        StringBuilder wrongAnswers = new StringBuilder("Грешни отговори: ");
        for (Answer answer : question.getWrongAnswers()) {
            wrongAnswers.append(answer.getName()).append(", ");
        }

        wrongAnswers.setLength(wrongAnswers.length() - 2);

        holder.wrongAnswers.setText(wrongAnswers.toString());

        holder.approveQuestion.setOnClickListener(v -> {
            data.remove(question);
            question.setApproved(true);
            realm.beginTransaction();
            realm.insertOrUpdate(question);
            realm.commitTransaction();
            this.notifyDataSetChanged();
        });
        holder.discardQuestion.setOnClickListener(v -> {
            data.remove(question);
            question.setApproved(false);
            realm.beginTransaction();
            realm.insertOrUpdate(question);
            realm.commitTransaction();
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}