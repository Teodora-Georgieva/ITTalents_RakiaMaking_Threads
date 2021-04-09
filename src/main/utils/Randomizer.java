package main.utils;

import main.Fruit;
import main.rakias.Rakia;

import java.util.Random;

public abstract class Randomizer {
    public static int getRandomNumber(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public static Fruit getRandomFruit(){
        int num = getRandomNumber(1, 3);
        switch (num){
            case 1: return Fruit.GRAPES;
            case 2: return Fruit.APRICOTS;
            default: return Fruit.PLUMS;
        }
    }
}