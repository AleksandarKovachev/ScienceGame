package com.tu.sofia.sciencegame.constant;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public enum QuestionType {

    TWO_ANSWERS(2, "Два отговора"), THREE_ANSWERS(3, "Три отговора"), FOUR_ANSWERS(4, "Четири отговора");

    private int answers;
    private String name;

    QuestionType(int answers, String name) {
        this.answers = answers;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int getAnswersByName(String name) {
        for(QuestionType questionType : QuestionType.values()) {
            if(questionType.getName().equals(name)) {
                return questionType.answers;
            }
        }
        return 0;
    }

}
