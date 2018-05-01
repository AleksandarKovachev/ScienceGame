package com.tu.sofia.sciencegame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.adapter.MyQuestionsAdapter;
import com.tu.sofia.sciencegame.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class MyQuestions extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_questions, container, false);

        List<Question> questions = new ArrayList<>();
        Question question = new Question();
        question.setQuestion("Как е?");
        question.setApproved(true);
        question.setCorrectDescription("Въпросче");
        question.setTotalAnswers(10);
        question.setCorrectlyAnswered(7);
        questions.add(question);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_my_questions);
        MyQuestionsAdapter adapter = new MyQuestionsAdapter(getContext(), questions);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        return root;
    }

}
