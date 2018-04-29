package com.tu.sofia.sciencegame.constant;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public enum UserTypes {

    USER(1), ADMIN(2);

    private int userType;

    private UserTypes(int userType) {
        this.userType = userType;
    }

    public int getUserType(){
        return userType;
    }

}
