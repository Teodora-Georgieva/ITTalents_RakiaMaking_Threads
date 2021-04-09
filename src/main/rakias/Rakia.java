package main.rakias;

import main.Fruit;
import main.people.RakiaMaker;

import java.time.LocalDateTime;

public abstract class Rakia {
    public enum RakiaType{
        GRAPE_RAKIA, APRICOT_RAKIA, PLUM_RAKIA
    }

    private LocalDateTime dateOfManufacturing;
    private Fruit fruit;
    private RakiaMaker rakiaMaker;
    private int liters;

    public Rakia(LocalDateTime dateOfManufacturing, Fruit fruit, RakiaMaker rakiaMaker, int liters) {
        this.dateOfManufacturing = dateOfManufacturing;
        this.fruit = fruit;
        this.rakiaMaker = rakiaMaker;
        this.liters = liters;
    }
}