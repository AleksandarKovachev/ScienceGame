package com.tu.sofia.sciencegame.entity;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Aleksandar Kovachev on 01.05.2018 г..
 */
public class Answer extends RealmObject {

    @PrimaryKey
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getId(), answer.getId()) &&
                Objects.equals(getName(), answer.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
