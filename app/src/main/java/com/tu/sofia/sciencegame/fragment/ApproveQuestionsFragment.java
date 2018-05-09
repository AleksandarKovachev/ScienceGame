package com.tu.sofia.sciencegame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.adapter.ApproveQuestionAdapter;
import com.tu.sofia.sciencegame.entity.Question;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aleksandar Kovachev on 06.05.2018 г..
 */
public class ApproveQuestionsFragment extends Fragment {

    private RecyclerView recyclerView;

    private Realm realm;

    private List<Question> questions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_approve_questions, container, false);

        realm = Realm.getDefaultInstance();

        recyclerView = root.findViewById(R.id.recycler_approve_questions);

        realm.beginTransaction();
        RealmResults<Question> questionsResults = realm.where(Question.class).equalTo("isApproved", false).findAll();
        questions = realm.copyFromRealm(questionsResults);
        realm.commitTransaction();

        ApproveQuestionAdapter adapter = new ApproveQuestionAdapter(getContext(), questions, realm);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return root;
    }

}
