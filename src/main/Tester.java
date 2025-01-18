package main;

import org.roaringbitmap.RoaringBitmap;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class Tester {
    int n = 100;
    int numberOfTests=100;
    int type;

    public Tester(int n, int numberOfTests, int type) {
        this.n=n;
        this.numberOfTests=numberOfTests;
        this.type=type;
    }

    public void test(){
        double density=0;

        ArrayList<Double> dens= new ArrayList<>();
        ArrayList<Double> comp= new ArrayList<>();

        for (int i = 10; i > 0; i--) {
            density = Math.pow(2, -i);
            dens.add(density);

            double compSum=0;

            for (int j = 1; j <= numberOfTests; j++) {
                ArrayList<Integer> uniformSet = generateUniformSet(n, density);

                Bitmap set = new Bitmap(type);
                for (int elem : uniformSet) {
                    set.add(elem);
                }
                compSum+=(double) (set.size() * 8) / n;
            }
            comp.add(compSum/numberOfTests);
        }

        System.out.println(dens);
        System.out.println(comp);
    }

    public static ArrayList<Integer> generateUniformSet(int n, double density) {
        Random random = new Random();
        ArrayList<Integer> resultList = new ArrayList<>();
        double max=n/density;

        for (int i = 0; i < n; i++) {
            resultList.add((int)(random.nextDouble()*max));
        }

        return resultList;
    }

}
