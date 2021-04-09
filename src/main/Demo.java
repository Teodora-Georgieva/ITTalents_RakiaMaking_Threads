package main;

import main.people.FruitPicker;
import main.people.RakiaMaker;
import main.rakias.Rakia;
import main.rakiastills.RakiaMakingHouse;
import main.rakiastills.RakiaStill;
import main.utils.Randomizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        RakiaMakingHouse rakiaMakingHouse = new RakiaMakingHouse();
        RakiaStill rakiaStill1 = new RakiaStill("Kazan1", rakiaMakingHouse);
        RakiaStill rakiaStill2 = new RakiaStill("Kazan2", rakiaMakingHouse);
        RakiaStill rakiaStill3 = new RakiaStill("Kazan3", rakiaMakingHouse);
        RakiaStill rakiaStill4 = new RakiaStill("Kazan4", rakiaMakingHouse);
        RakiaStill rakiaStill5 = new RakiaStill("Kazan5", rakiaMakingHouse);

        rakiaMakingHouse.addRakiaStill(rakiaStill1);
        rakiaMakingHouse.addRakiaStill(rakiaStill2);
        rakiaMakingHouse.addRakiaStill(rakiaStill3);
        rakiaMakingHouse.addRakiaStill(rakiaStill4);
        rakiaMakingHouse.addRakiaStill(rakiaStill5);

        ArrayList<FruitPicker> fruitPickers = new ArrayList<>();
        int age = 60;
        for (int i = 0; i < 7; i++) {
            String name = "FruitPicker" + (i+1);
            Fruit fruit = Randomizer.getRandomFruit();
            fruitPickers.add(new FruitPicker(age++, name, fruit, rakiaMakingHouse));
        }

        for(FruitPicker fruitPicker : fruitPickers){
            System.out.println(fruitPicker.getName() + " - " + fruitPicker.getFruit());
        }

        System.out.println();

        age = 65;
        ArrayList<RakiaMaker> rakiaMakers = new ArrayList<>();
        Rakia.RakiaType[] rakiaTypes = {Rakia.RakiaType.APRICOT_RAKIA, Rakia.RakiaType.GRAPE_RAKIA, Rakia.RakiaType.PLUM_RAKIA};
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            String name = "RakiaMaker" + (i+1);
            Rakia.RakiaType rakiaType = rakiaTypes[counter++];
            if(counter == 3){
                counter = 0;
            }
            rakiaMakers.add(new RakiaMaker(age, name, rakiaMakingHouse, rakiaType));
        }

        for(RakiaMaker rakiaMaker : rakiaMakers){
            System.out.println(rakiaMaker.getName() + " - " + rakiaMaker.getRakiaType());
        }

        System.out.println();

        for(FruitPicker fruitPicker : fruitPickers){
            new Thread(fruitPicker).start();
        }

        for(RakiaMaker rakiaMaker : rakiaMakers){
            new Thread(rakiaMaker).start();
        }

        Thread deamon = new Thread(() -> {
            int i = 1;
            while(true) {
                String statistics = rakiaMakingHouse.getStatistics();
                File directory = new File("statistics");
                if(!directory.exists()){
                    directory.mkdir();
                }

                File file = new File(directory, "statistics" + i++);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try(PrintStream ps = new PrintStream(file);) {
                    ps.println(statistics);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        deamon.setDaemon(true);
        deamon.start();

    }
}