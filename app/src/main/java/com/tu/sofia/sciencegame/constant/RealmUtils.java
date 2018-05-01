package com.tu.sofia.sciencegame.constant;

import io.realm.Realm;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class RealmUtils {

    public static Number getNextId(Class classType, Realm realm) {
        Number currentIdNum = realm.where(classType).max("id");
        long nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

}
