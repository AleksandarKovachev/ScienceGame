package com.tu.sofia.sciencegame.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.adapter.AnswerAdapter;
import com.tu.sofia.sciencegame.constant.RealmUtils;
import com.tu.sofia.sciencegame.constant.SharedPreferencesConstants;
import com.tu.sofia.sciencegame.entity.Answer;
import com.tu.sofia.sciencegame.entity.Question;
import com.tu.sofia.sciencegame.entity.Statistic;
import com.tu.sofia.sciencegame.entity.User;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.hoang8f.widget.FButton;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aleksandar Kovachev on 02.05.2018 г..
 */
public class GameActivity extends AppCompatActivity {

    private Realm realm;

    private TextView question;
    private TextView questionNumberTextView;
    private TextView correctedAnswersTextView;

    private List<Question> questions;

    private RecyclerView recyclerView;
    private AnswerAdapter adapter;

    private int questionNumber;
    private int correctedAnswers;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        realm = Realm.getDefaultInstance();

        question = findViewById(R.id.question);
        questionNumberTextView = findViewById(R.id.questionNumber);
        correctedAnswersTextView = findViewById(R.id.correctedAnswers);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        realm.beginTransaction();
        RealmResults<Question> questionsResults = realm.where(Question.class).equalTo("isApproved", true).findAll();
        questions = realm.copyFromRealm(questionsResults);
        realm.commitTransaction();

        Collections.shuffle(questions);

        if(questions.size() == 0) {
            Toast.makeText(this, "Няма налични въпроси за ирата!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        question.setText(questions.get(questionNumber).getQuestion());
        updateTexts();

        recyclerView = findViewById(R.id.recycler_view_answers);

        List<Answer> answers = getAnswers();
        adapter = getAnswerAdapter(answers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private AnswerAdapter getAnswerAdapter(List<Answer> answers) {
        return new AnswerAdapter(this, answers, questions.get(questionNumber).getCorrectAnswer(), correctOnClickListener, wrongOnClickListener);
    }

    private void updateTexts() {
        questionNumberTextView.setText(
                String.format(getString(R.string.questionNumber), String.valueOf(questionNumber != questions.size() ? questionNumber + 1 : questionNumber), String.valueOf(questions.size())));
        correctedAnswersTextView.setText(String.valueOf(correctedAnswers));
    }

    private List<Answer> getAnswers() {
        List<Answer> answers = new ArrayList<>(questions.get(questionNumber).getWrongAnswers());
        answers.add(questions.get(questionNumber).getCorrectAnswer());
        Collections.shuffle(answers);
        return answers;
    }

    private View.OnClickListener correctOnClickListener = view -> {
        correctedAnswers++;
        realm.beginTransaction();
        Question updateQuestion = questions.get(questionNumber);
        updateQuestion.setCorrectlyAnswered(updateQuestion.getCorrectlyAnswered() + 1);
        updateQuestion.setTotalAnswers(updateQuestion.getTotalAnswers() + 1);
        realm.insertOrUpdate(updateQuestion);
        realm.commitTransaction();
        answerDialog(getString(R.string.correctAnswerDialog), questions.get(questionNumber));
    };

    private View.OnClickListener wrongOnClickListener = view -> {
        realm.beginTransaction();
        Question updateQuestion = questions.get(questionNumber);
        updateQuestion.setTotalAnswers(updateQuestion.getTotalAnswers() + 1);
        realm.insertOrUpdate(updateQuestion);
        realm.commitTransaction();
        answerDialog(getString(R.string.wrongAnswerDialog), questions.get(questionNumber));
    };

    public void answerDialog(String status, Question question) {
        final Dialog dialogCorrect = new Dialog(GameActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_answer);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        TextView answerStatus = dialogCorrect.findViewById(R.id.answerStatus);
        TextView answerDescription = dialogCorrect.findViewById(R.id.answerDescription);
        FButton buttonNext = dialogCorrect.findViewById(R.id.dialogNext);

        answerStatus.setText(status);
        answerDescription.setText(question.getCorrectDescription());

        questionNumber++;
        updateTexts();

        if (questionNumber == questions.size()) {
            buttonNext.setText(getText(R.string.finishGame));
        }

        buttonNext.setOnClickListener(view -> {
            dialogCorrect.dismiss();
            if (questionNumber == questions.size()) {
                realm.beginTransaction();
                User user = realm.where(User.class).equalTo(SharedPreferencesConstants.USERNAME, sharedPreferencesManager.getString(SharedPreferencesConstants.USERNAME, null)).findFirst();
                Statistic statistic = realm.createObject(Statistic.class, RealmUtils.getNextId(Statistic.class, realm));
                statistic.setCorrectedAnswers(correctedAnswers);
                statistic.setTotalAnswers(questions.size());
                statistic.setUser(user);
                realm.commitTransaction();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                this.question.setText(questions.get(questionNumber).getQuestion());
                List<Answer> answers = getAnswers();
                adapter = getAnswerAdapter(answers);
                recyclerView.swapAdapter(adapter, true);
            }
        });

    }

}
