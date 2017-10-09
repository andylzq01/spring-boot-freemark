package com.wooyoo.learning.dao.domain;

import javax.persistence.*;

/**
 * Created by hdwang on 2017/6/15.
 */
public class User {

    private Integer id;

    private String number;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
