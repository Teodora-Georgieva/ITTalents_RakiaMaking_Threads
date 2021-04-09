package main.people;

import main.rakias.Rakia;
import main.rakiastills.RakiaMakingHouse;

public class RakiaMaker extends Person implements Runnable{
    private RakiaMakingHouse rakiaMakingHouse;
    private Rakia.RakiaType rakiaType;

    public RakiaMaker(int age, String name, RakiaMakingHouse rakiaMakingHouse, Rakia.RakiaType rakiaType) {
        super(age, name);
        this.rakiaMakingHouse = rakiaMakingHouse;
        this.rakiaType = rakiaType;
    }

    @Override
    public void run() {
        while (true) {
            //System.out.println(this.getName() + " is trying to make rakia of type " + rakiaMakingHouse.getRakiaStill().getRakiaType());
            rakiaMakingHouse.makeRakia( this);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public Rakia.RakiaType getRakiaType() {
        return rakiaType;
    }
}