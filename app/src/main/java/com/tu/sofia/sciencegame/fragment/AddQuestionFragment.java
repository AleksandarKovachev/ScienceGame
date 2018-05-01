package com.tu.sofia.sciencegame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.adapter.AddAnswersAdapter;
import com.tu.sofia.sciencegame.adapter.SpinnerAdapter;
import com.tu.sofia.sciencegame.constant.QuestionType;
import com.tu.sofia.sciencegame.constant.RealmUtils;
import com.tu.sofia.sciencegame.constant.SharedPreferencesConstants;
import com.tu.sofia.sciencegame.entity.Answer;
import com.tu.sofia.sciencegame.entity.Question;
import com.tu.sofia.sciencegame.entity.User;
import com.tu.sofia.sciencegame.manager.DialogManager;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class AddQuestionFragment extends Fragment {

    private AppCompatSpinner spinner;
    private RecyclerView recyclerView;
    private AddAnswersAdapter adapter;

    private Button addQuestion;

    private EditText question, description;

    private List<String> spinnerData;

    private int count;

    private Realm realm;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_question, container, false);

        spinner = root.findViewById(R.id.answers_spinner);
        recyclerView = root.findViewById(R.id.recycler_view_answers);
        addQuestion = root.findViewById(R.id.add_question);
        question = root.findViewById(R.id.input_question);
        description = root.findViewById(R.id.input_layout_question_description);
        addQuestion.setOnClickListener(clickListener);

        realm = Realm.getDefaultInstance();
        sharedPreferencesManager = new SharedPreferencesManager(getContext());

        spinnerData = new ArrayList<>();
        spinnerData.add(getContext().getString(R.string.total_answers));
        for (QuestionType questionType : QuestionType.values()) {
            spinnerData.add(questionType.getName());
        }

        ArrayAdapter<String> dataAdapter = new SpinnerAdapter(getContext(),
                android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(spinnerClickListener);

        adapter = new AddAnswersAdapter(getContext(), count);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private AdapterView.OnItemSelectedListener spinnerClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            count = QuestionType.getAnswersByName(spinnerData.get(i));
            adapter.setCount(count);
            adapter.getEditTextList().clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    private View.OnClickListener clickListener = view -> {
        if (question.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(getContext(), "Неуспешно", "Полето Въпрос е задължително поле", "Добре");
            return;
        }
        if (description.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(getContext(), "Неуспешно", "Полето Описание е задължително поле", "Добре");
            return;
        }
        if (adapter.getEditTextList().size() == 0 || isEmptyAnswers()) {
            DialogManager.showAlertDialog(getContext(), "Неуспешно", "Добавянето на отговори е задължително", "Добре");
            return;
        }

        realm.beginTransaction();
        Question question = realm.createObject(Question.class, RealmUtils.getNextId(Question.class, realm));
        question.setQuestion(this.question.getText().toString());
        question.setCorrectDescription(description.getText().toString());

        int i = 0;
        RealmList<Answer> answerList = new RealmList<>();
        for (EditText answers : adapter.getEditTextList()) {
            Answer answer = realm.createObject(Answer.class, RealmUtils.getNextId(Answer.class, realm));
            answer.setName(answers.getText().toString());

            if (i == 0) {
                question.setCorrectAnswer(answer);
            } else {
                answerList.add(answer);
            }

            i++;
        }
        question.setWrongAnswers(answerList);
        question.setQuestionType(count);
        question.setApproved(false);
        User user = realm.where(User.class).equalTo(SharedPreferencesConstants.USERNAME, sharedPreferencesManager.getString(SharedPreferencesConstants.USERNAME, null)).findFirst();
        question.setFromUser(user);
        realm.commitTransaction();

        DialogManager.showAlertDialog(getContext(), "Успешно", "Успешно добавяне на нов въпрос. Чака одобрение от администратор!", "Добре");
    };

    private boolean isEmptyAnswers() {
        for (EditText editText : adapter.getEditTextList()) {
            if (editText.getText().toString().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
