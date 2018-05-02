package com.tu.sofia.sciencegame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.adapter.AnswerAdapter;
import com.tu.sofia.sciencegame.entity.Answer;
import com.tu.sofia.sciencegame.entity.Question;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aleksandar Kovachev on 02.05.2018 г..
 */
public class GameActivity extends AppCompatActivity {

    private Realm realm;

    private SharedPreferencesManager sharedPreferencesManager;

    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        realm = Realm.getDefaultInstance();
        sharedPreferencesManager = new SharedPreferencesManager(this);

        question = findViewById(R.id.question);

        List<Question> questions;

        realm.beginTransaction();
        RealmResults<Question> questionsResults = realm.where(Question.class).findAll();
        questions = realm.copyFromRealm(questionsResults);
        realm.commitTransaction();

        question.setText(questions.get(0).getQuestion());

        List<Answer> answers = questions.get(0).getWrongAnswers();
        answers.add(questions.get(0).getCorrectAnswer());

        Collections.shuffle(answers);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_answers);
        AnswerAdapter adapter = new AnswerAdapter(this, answers, questions.get(0).getCorrectAnswer());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

}
