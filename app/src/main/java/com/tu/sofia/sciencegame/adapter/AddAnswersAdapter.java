package com.tu.sofia.sciencegame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tu.sofia.sciencegame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class AddAnswersAdapter extends RecyclerView.Adapter<AddAnswersAdapter.ViewHolder> {

    private Context context;

    private int count;

    private List<EditText> editTextList;

    public AddAnswersAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
        editTextList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_answer, parent, false);
        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText answerEditText;
        TextView answerNumber;

        ViewHolder(View view) {
            super(view);

            answerEditText = view.findViewById(R.id.answer_edit_text);
            answerNumber = view.findViewById(R.id.answer_number);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == 0) {
            holder.answerNumber.setText(context.getString(R.string.correct_answer));
        } else {
            holder.answerNumber.setText(String.format(context.getString(R.string.answer).toString(), position));
        }

        editTextList.add(holder.answerEditText);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EditText> getEditTextList() {
        return editTextList;
    }

}
