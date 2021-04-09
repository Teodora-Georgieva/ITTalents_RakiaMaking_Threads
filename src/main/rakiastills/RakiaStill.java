package main.rakiastills;

import main.Fruit;
import main.rakias.Rakia;

public class RakiaStill {
    private String name;
    private int amountOfFruits;
    private Fruit fruitType;
    private RakiaMakingHouse rakiaMakingHouse;
    private volatile Rakia.RakiaType rakiaType;
    public static final int MAX_FRUIT_CAPACITY = 10;

    public RakiaStill(String name, RakiaMakingHouse rakiaMakingHouse){
        this.name = name;
        this.rakiaMakingHouse = rakiaMakingHouse;
    }

    public void emptyStill(){
        this.amountOfFruits = 0;
    }

    public int getAmountOfFruits() {
        return amountOfFruits;
    }

    public String getName() {
        return name;
    }

    public Rakia.RakiaType getRakiaType() {
        return this.rakiaType;
    }

    public Fruit getFruitType() {
        return fruitType;
    }

    public boolean isEmpty() {
        return this.amountOfFruits == 0;
    }

    public void setFruitType(Fruit fruit) {
        this.fruitType = fruit;
    }

    public void setRakiaType(Rakia.RakiaType rakiaType) {
        this.rakiaType = rakiaType;
    }

    public void increaseAmountOfFruits(int kilos) {
        this.amountOfFruits+=kilos;
    }

    public boolean isFull() {
        return this.amountOfFruits == MAX_FRUIT_CAPACITY;
    }
}