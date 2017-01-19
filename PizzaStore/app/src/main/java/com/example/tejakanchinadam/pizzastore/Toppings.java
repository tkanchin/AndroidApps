package com.example.tejakanchinadam.pizzastore;

/**
 * Created by tejakanchinadam on 2/1/16.
 */
public class Toppings {

    int bacon = R.drawable.bacon;

    int cheese = R.drawable.cheese;

    int garlic = R.drawable.garlic;

    int greenPepper = R.drawable.green_pepper;

    int mushroom = R.drawable.mushroom;

    int olives = R.drawable.olives;

    int redpepper = R.drawable.red_pepper;

    int tomato = R.drawable.tomato;

    int count;

    int[] toppingsArray = {bacon, cheese, garlic, greenPepper, mushroom, olives, redpepper, tomato};

    String[] mytoppingsArray = {"Bacon", "Cheese", "Garlic", "GreenPepper", "Mushroom", "Olives", "Redpepper", "Tomato"};

    public int toppingImage (int toppingName) {

        return toppingsArray[toppingName];

    }


}
