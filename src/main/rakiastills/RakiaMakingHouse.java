package main.rakiastills;

import main.Fruit;
import main.people.RakiaMaker;
import main.rakias.ApricotRakia;
import main.rakias.GrapeRakia;
import main.rakias.PlumRakia;
import main.rakias.Rakia;
import main.utils.Randomizer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RakiaMakingHouse{
    private ArrayList<RakiaStill> rakiaStills;
    private int litersOfMadeGrapeRakia;
    private int litersOfMadeApricotRakia;
    private int litersOfMadePlumRakia;
    private ArrayList<Rakia> madeRakias;

    public RakiaMakingHouse(){
        this.rakiaStills = new ArrayList<>();
        this.madeRakias = new ArrayList<>();
    }

    public void addRakiaStill(RakiaStill rakiaStill){
        this.rakiaStills.add(rakiaStill);
    }

    private RakiaStill getAppropriateStillForFruitPicker(Fruit fruit){
        for(RakiaStill rakiaStill : this.rakiaStills){
            if(rakiaStill.getAmountOfFruits() < RakiaStill.MAX_FRUIT_CAPACITY && rakiaStill.getFruitType() == fruit){
                return rakiaStill;
            }
        }

        for(RakiaStill rakiaStill : this.rakiaStills){
            if(rakiaStill.isEmpty()){
                return rakiaStill;
            }
        }

        return null;
    }

    private RakiaStill getAppropriateStillForRakiaMaker(RakiaMaker rakiaMaker){
        for(RakiaStill rakiaStill : this.rakiaStills){
            if(rakiaStill.isFull() && rakiaStill.getRakiaType() == rakiaMaker.getRakiaType()) {
                return rakiaStill;
            }
        }

        return null;
    }

    public synchronized void fillStill(Fruit fruit, int kilos){
        if(this.getLitersOfMadeRakia() >= 10){
            Thread.currentThread().interrupt();
            return;
        }

        RakiaStill appropriateRakiaStill;

        while (this.getAppropriateStillForFruitPicker(fruit) == null) {
            try {
                System.out.println("Fruit picker is waited because there is no appropriate still");
                wait();

                if(this.getLitersOfMadeRakia() >= 10){
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            catch (InterruptedException e) {
            }
        }

        appropriateRakiaStill = this.getAppropriateStillForFruitPicker(fruit);
        if (appropriateRakiaStill.getFruitType() == null) {
            appropriateRakiaStill.setFruitType(fruit);
            System.out.println("FRUIT TYPE OF " + appropriateRakiaStill.getName() +
                    " IS SET TO " + appropriateRakiaStill.getFruitType());

            Fruit fruitType = appropriateRakiaStill.getFruitType();
            switch (fruitType) {
                case PLUMS:
                    appropriateRakiaStill.setRakiaType(Rakia.RakiaType.PLUM_RAKIA);
                    break;
                case GRAPES:
                    appropriateRakiaStill.setRakiaType(Rakia.RakiaType.GRAPE_RAKIA);
                    break;
                case APRICOTS:
                    appropriateRakiaStill.setRakiaType(Rakia.RakiaType.APRICOT_RAKIA);
                    break;
            }
            System.out.println("RAKIA TYPE OF " + appropriateRakiaStill.getName() +
                    " IS SET TO: " + appropriateRakiaStill.getRakiaType());
        }

        appropriateRakiaStill.increaseAmountOfFruits(kilos);
        System.out.println("Put " + fruit + " in " + appropriateRakiaStill.getName() +
                ", now there are " + appropriateRakiaStill.getAmountOfFruits() + " kilos");

        if (appropriateRakiaStill.getAmountOfFruits() == 10) {
            notifyAll();
        }
    }

    public synchronized void makeRakia(RakiaMaker rakiaMaker){
        if(this.getLitersOfMadeRakia() >= 10){
            Thread.currentThread().interrupt();
            return;
        }

        while (this.getAppropriateStillForRakiaMaker(rakiaMaker) == null) {
            try {
                System.out.println("Rakia maker is waited because there is no appropriate still");
                wait();

                if(this.getLitersOfMadeRakia() >= 10){
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            catch (InterruptedException e) {
            }
        }

        RakiaStill rakiaStill = this.getAppropriateStillForRakiaMaker(rakiaMaker);
        rakiaStill.emptyStill();
        System.out.println("Removed 10 kilos of " + rakiaMaker.getRakiaType() +
                ", now there are " + rakiaStill.getAmountOfFruits());

        int litersOfMadeRakia = Randomizer.getRandomNumber(1, 5);
        this.increaseLitersOfMadeRakia(litersOfMadeRakia, rakiaStill.getRakiaType());

        Rakia madeRakia = null;
        switch (rakiaStill.getRakiaType()){
            case APRICOT_RAKIA: madeRakia = new ApricotRakia(LocalDateTime.now(), rakiaStill.getFruitType(),
                    rakiaMaker, litersOfMadeRakia); break;
            case GRAPE_RAKIA: madeRakia = new GrapeRakia(LocalDateTime.now(), rakiaStill.getFruitType(),
                    rakiaMaker, litersOfMadeRakia); break;
            case PLUM_RAKIA: madeRakia = new PlumRakia(LocalDateTime.now(), rakiaStill.getFruitType(),
                    rakiaMaker, litersOfMadeRakia); break;
        }

        this.madeRakias.add(madeRakia);

        System.out.println("RAKIA TYPE: " + rakiaStill.getRakiaType() + ", " + litersOfMadeRakia + " LITERS ARE MADE");
        System.out.println("MAX LITERS OF PRODUCED RAKIA SO FAR: " + this.getLitersOfMadeRakia());
        rakiaStill.setRakiaType(null);
        rakiaStill.setFruitType(null);
        notifyAll();

    }

    private Fruit getMostPickedFruit(){
        int kilosOfApricots = 0;
        int kilosOfPlums = 0;
        int kilosOfGrapes = 0;

        for(RakiaStill rakiaStill : this.rakiaStills){
            if(rakiaStill.getFruitType() == null){
                continue;
            }

            switch (rakiaStill.getFruitType()){
                case APRICOTS: kilosOfApricots += rakiaStill.getAmountOfFruits(); break;
                case GRAPES: kilosOfGrapes += rakiaStill.getAmountOfFruits(); break;
                case PLUMS: kilosOfPlums += rakiaStill.getAmountOfFruits(); break;
            }
        }

        Fruit maxFruit = Fruit.APRICOTS;
        int maxKilos = kilosOfApricots;

        if(kilosOfPlums > maxKilos){
            maxKilos = kilosOfPlums;
            maxFruit = Fruit.PLUMS;
        }
        if(kilosOfGrapes > maxKilos){
            maxKilos = kilosOfGrapes;
            maxFruit = Fruit.GRAPES;
        }

        if(maxKilos == 0){
            return null;
        }

        return maxFruit;
    }

    private Rakia.RakiaType getTypeOfMostProducedRakia() {
        /*
        System.out.println("Appricot rakia : " + this.litersOfMadeApricotRakia);
        System.out.println("Plum rakia: " + this.litersOfMadePlumRakia);
        System.out.println("Grapes rakia: " + this.litersOfMadeGrapeRakia);
         */

        int maxRakia = this.litersOfMadeApricotRakia;
        if(this.litersOfMadeGrapeRakia > maxRakia){
             maxRakia = this.litersOfMadeGrapeRakia;
        }
        if(this.litersOfMadePlumRakia > maxRakia){
            maxRakia = this.litersOfMadePlumRakia;
        }

        if(maxRakia == 0){
            return null;
        }

        if(maxRakia == this.litersOfMadeApricotRakia){
            return Rakia.RakiaType.APRICOT_RAKIA;
        }
        if(maxRakia == this.litersOfMadePlumRakia){
            return Rakia.RakiaType.PLUM_RAKIA;
        }
        return Rakia.RakiaType.GRAPE_RAKIA;
    }

    private double getRatioGrapeApricotRakia(){
        double grapeRakia = this.litersOfMadeGrapeRakia*1.0;
        int apricotRakia = this.litersOfMadeApricotRakia;
        double ratio;
        if(grapeRakia == 0 && apricotRakia == 0){
            //System.out.println("No grape rakia produced, no apricot rakia produced");
            return 0;
        }
        try{
            ratio = grapeRakia/apricotRakia;
        }
        catch (ArithmeticException e){
            ratio = 0;
        }
        return ratio;
    }

    public String getStatistics(){
        String mostPickedFruit = "Most picked fruit: " + this.getMostPickedFruit();
        String mostProducedTypeOfRakia = "Most produced type of rakia: " + this.getTypeOfMostProducedRakia();
        String ratio = "grape rakia : apricot rakia = " + this.getRatioGrapeApricotRakia();

        String statistics = "--------------------Statistics----------------------" + '\n';
        statistics = statistics + mostPickedFruit + '\n' + mostProducedTypeOfRakia + '\n' + ratio + '\n';
        return statistics;
    }

    private int getLitersOfMadeRakia() {
        int maxLitersOfMadeRakia = litersOfMadeApricotRakia;
        if(litersOfMadePlumRakia > maxLitersOfMadeRakia){
            maxLitersOfMadeRakia = litersOfMadePlumRakia;
        }

        if(litersOfMadeGrapeRakia > maxLitersOfMadeRakia){
            maxLitersOfMadeRakia = litersOfMadeGrapeRakia;
        }
        return maxLitersOfMadeRakia;
    }

    private void increaseLitersOfMadeRakia(int liters, Rakia.RakiaType rakiaType){
        switch (rakiaType){
            case PLUM_RAKIA: this.litersOfMadePlumRakia+=liters; break;
            case GRAPE_RAKIA: this.litersOfMadeGrapeRakia+=liters; break;
            case APRICOT_RAKIA: this.litersOfMadeApricotRakia+=liters; break;
        }
    }
}