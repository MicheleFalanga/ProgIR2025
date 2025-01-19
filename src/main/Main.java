package main;

import java.util.*;

import it.uniroma3.mat.extendedset.intset.ConciseSet;
import main.BitSetIndex;
import org.roaringbitmap.RoaringBitmap;
import main.RoaringBitmapIndex;

public class Main {

    public static void main(String[] args) throws Exception{
        Main.syntheticTest();
        return;
    }

    public static void syntheticTest() {
        int n = 100000;
        int numberOfTests=1;

        new Tester(n,numberOfTests,1,1).test();
        new Tester(n,numberOfTests,2,1).test();
        new Tester(n,numberOfTests,3,1).test();
        new Tester(n,numberOfTests,4,1).test();

    }

    public static void dataSetTest() {
        String path="src/datasets/";
        String[] fileNames ={"census-income.data","wikileaks.csv","weather_sept_85.csv"};

        for(int i=0; i< fileNames.length;i++) {
            RoaringBitmapIndex roaringIndex =new RoaringBitmapIndex(path+fileNames[i]);

            double density = roaringIndex.calculateDensity();

            System.out.println("Density: " + density);

            double size = roaringIndex.calculateSpacePerItem();
            System.out.println("Size: " + size);
        }

        System.out.println("---------------");
        for(int i=0; i< fileNames.length;i++) {
            BitSetIndex roaringIndex =new BitSetIndex(path+fileNames[i]);

            double density = roaringIndex.calculateDensity();

            System.out.println("Density: " + density);

            double size = roaringIndex.calculateSpacePerItem();
            System.out.println("Size: " + size);
        }
    }


    public static String getRandomKey(Map<String, RoaringBitmap> map) {
        Random random = new Random();

        String[] groups = map.keySet().toArray(new String[0]);

        return groups[random.nextInt(groups.length)];
    }

}