package com.tu.sofia.sciencegame.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.activity.GameActivity;
import com.tu.sofia.sciencegame.activity.StatisticActivity;

import info.hoang8f.widget.FButton;

/**
 * Created by Aleksandar Kovachev on 02.05.2018 г..
 */
public class HomeScreenFragment extends Fragment {

    private FButton startGameButton;
    private FButton statisticButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        startGameButton = root.findViewById(R.id.play_game_button);
        startGameButton.setOnClickListener(startGameListener);

        statisticButton = root.findViewById(R.id.statistic_button);
        statisticButton.setOnClickListener(statisticButtonListener);

        return root;
    }

    private View.OnClickListener startGameListener = view -> {
        Intent intent = new Intent(getContext(), GameActivity.class);
        startActivity(intent);
    };

    private View.OnClickListener statisticButtonListener = view -> {
        Intent intent = new Intent(getContext(), StatisticActivity.class);
        startActivity(intent);
    };

}
