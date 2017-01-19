package com.example.tejakanchinadam.inclass12;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by tejakanchinadam on 4/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

    String amount, category, date, name, user;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Expense() {

    }
}
