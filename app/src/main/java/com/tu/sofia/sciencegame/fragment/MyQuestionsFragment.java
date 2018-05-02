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
import com.tu.sofia.sciencegame.constant.SharedPreferencesConstants;
import com.tu.sofia.sciencegame.entity.Question;
import com.tu.sofia.sciencegame.entity.User;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class MyQuestionsFragment extends Fragment {

    private Realm realm;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_questions, container, false);

        List<Question> questions;

        realm = Realm.getDefaultInstance();
        sharedPreferencesManager = new SharedPreferencesManager(getContext());

        realm.beginTransaction();
        User user = realm.where(User.class).equalTo(SharedPreferencesConstants.USERNAME, sharedPreferencesManager.getString(SharedPreferencesConstants.USERNAME, null)).findFirst();
        RealmResults<Question> questionsResults = realm.where(Question.class).equalTo("userId", user.getId()).findAll();
        questions = realm.copyFromRealm(questionsResults);
        realm.commitTransaction();


        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_my_questions);
        MyQuestionsAdapter adapter = new MyQuestionsAdapter(getContext(), questions);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return root;
    }

}
