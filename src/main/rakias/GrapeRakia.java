package main.rakias;

import main.Fruit;
import main.people.RakiaMaker;

import java.time.LocalDateTime;

public class GrapeRakia extends Rakia{
    public GrapeRakia(LocalDateTime dateOfManufacturing, Fruit fruit, RakiaMaker rakiaMaker, int liters) {
        super(dateOfManufacturing, fruit, rakiaMaker, liters);
    }
}