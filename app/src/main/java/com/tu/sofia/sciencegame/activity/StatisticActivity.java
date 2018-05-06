package com.tu.sofia.sciencegame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.adapter.StatisticAdapter;
import com.tu.sofia.sciencegame.entity.Statistic;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Aleksandar Kovachev on 06.05.2018 г..
 */
public class StatisticActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StatisticAdapter adapter;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        realm = Realm.getDefaultInstance();

        List<Statistic> data;

        realm.beginTransaction();
        RealmResults<Statistic> statistics = realm.where(Statistic.class).findAll();
        data = realm.copyFromRealm(statistics);
        realm.commitTransaction();

        Collections.sort(data, (statistic1, statistic2) -> {
            if (statistic1.getTotalAnswers() - statistic1.getCorrectedAnswers() > statistic2.getTotalAnswers() - statistic2.getCorrectedAnswers()) {
                return 1;
            } else if (statistic1.getTotalAnswers() - statistic1.getCorrectedAnswers() < statistic2.getTotalAnswers() - statistic2.getCorrectedAnswers()) {
                return -1;
            } else {
                return 0;
            }
        });

        recyclerView = findViewById(R.id.recycler_view_statistic);
        adapter = new StatisticAdapter(this, data);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
