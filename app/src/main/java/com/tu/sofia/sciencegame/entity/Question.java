package com.tu.sofia.sciencegame.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class Question extends RealmObject {

    @PrimaryKey
    private Long id;

    private String question;

    private String correctDescription;

    private RealmList<Answer> wrongAnswers;

    private Answer correctAnswer;

    private int questionType;

    private boolean isApproved;

    private User fromUser;

    private long correctlyAnswered;

    private long totalAnswers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectDescription() {
        return correctDescription;
    }

    public void setCorrectDescription(String correctDescription) {
        this.correctDescription = correctDescription;
    }

    public RealmList<Answer> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(RealmList<Answer> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public long getCorrectlyAnswered() {
        return correctlyAnswered;
    }

    public void setCorrectlyAnswered(long correctlyAnswered) {
        this.correctlyAnswered = correctlyAnswered;
    }

    public long getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(long totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }
}
