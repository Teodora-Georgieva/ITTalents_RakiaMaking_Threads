package main.people;

import main.Fruit;
import main.rakiastills.RakiaMakingHouse;

public class FruitPicker extends Person implements Runnable{
    private Fruit fruit;
    private RakiaMakingHouse rakiaMakingHouse;

    public FruitPicker(int age, String name, Fruit fruit, RakiaMakingHouse rakiaMakingHouse) {
        super(age, name);
        this.fruit = fruit;
        this.rakiaMakingHouse = rakiaMakingHouse;
    }

    @Override
    public void run() {
        while(true){
            /*
            System.out.println(this.getName() + " is trying to fill " + this.rakiaMakingHouse.getRakiaStill().getName() +
                    " with " + fruit);
            */

            rakiaMakingHouse.fillStill(this.fruit, 1);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public Fruit getFruit() {
        return fruit;
    }
}