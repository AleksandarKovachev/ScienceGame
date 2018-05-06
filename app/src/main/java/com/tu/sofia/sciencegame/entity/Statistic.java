package com.tu.sofia.sciencegame.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Aleksandar Kovachev on 06.05.2018 г..
 */
public class Statistic extends RealmObject {

    @PrimaryKey
    private Long id;

    private User user;

    private int correctedAnswers;

    private int totalAnswers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCorrectedAnswers() {
        return correctedAnswers;
    }

    public void setCorrectedAnswers(int correctedAnswers) {
        this.correctedAnswers = correctedAnswers;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }
}
