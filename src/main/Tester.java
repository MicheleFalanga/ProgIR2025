package main;

import java.util.ArrayList;
import java.util.Random;

public class Tester {
    int n = 100;
    int numberOfTests=100;
    int bitmapType;
    int distType;

    Random random = new Random();

    public Tester(int n, int numberOfTests, int bitmapType, int distType) {
        this.n=n;
        this.numberOfTests=numberOfTests;
        this.bitmapType = bitmapType;
    }

    public void test(){
        double density=0;
        long startTime;
        long endTime;

        ArrayList<Double> dens= new ArrayList<>();
        ArrayList<Double> comp= new ArrayList<>();
        ArrayList<Double> and= new ArrayList<>();
        ArrayList<Double> or= new ArrayList<>();
        ArrayList<Double> rem= new ArrayList<>();
        ArrayList<Double> add= new ArrayList<>();

        for (int i = 10; i > 0; i--) {
            density = Math.pow(2, -i);
            dens.add(density);

            double compSum=0;
            double andSum=0;
            double orSum=0;
            double remSum=0;
            double addSum=0;

            Bitmap temp;


            for (int j = 1; j <= numberOfTests; j++) {
                ArrayList<Integer> numbers = generateList(n, density);

                Bitmap set = new Bitmap(bitmapType);
                for (int elem : numbers) {
                    set.add(elem);
                }
                compSum+=(double) (set.size() * 8) / n;

                ArrayList<Integer> numbersForTest = generateList(n, density);

                Bitmap Test = new Bitmap(bitmapType);
                for (int elem : numbers) {
                    Test.add(elem);
                }

                startTime = System.nanoTime();
                temp=set.and(Test);
                endTime = System.nanoTime();

                andSum=((double)(endTime-startTime));


                startTime = System.nanoTime();
                temp=set.or(Test);
                endTime = System.nanoTime();

                orSum=((double)(endTime-startTime));

                int numberToRemove =numbers.get(random.nextInt(numbers.size()));
                startTime = System.nanoTime();
                set.remove(numberToRemove);
                endTime = System.nanoTime();

                remSum=((double)(endTime-startTime));

                int numberToAdd =generateNumber(set.last()) ;
                startTime = System.nanoTime();
                set.add(numberToAdd);
                endTime = System.nanoTime();

                addSum=((double)(endTime-startTime));

            }
            comp.add(compSum/numberOfTests);
            and.add(andSum/numberOfTests);
            or.add(orSum/numberOfTests);
            rem.add(remSum/numberOfTests);
            add.add(addSum/numberOfTests);
        }
        System.out.println("----------"+ bitmapType +"----------");
        //System.out.println(dens);
        System.out.println("Compression:  "+comp);
        System.out.println("Union:        "+and);
        System.out.println("Intersection: "+or);
        System.out.println("Removal:      "+rem);
        System.out.println("Insertion:    "+add);

    }


    private ArrayList<Integer> generateList(int n, double density) {
        if (distType == 1) {
            return generateUniformSet(n, density);
        }else{
            return generateBetaSet(n, density);
        }

    }

    private ArrayList<Integer> generateUniformSet(int n, double density) {

        ArrayList<Integer> resultList = new ArrayList<>();
        double max=n/density;

        for (int i = 0; i < n; i++) {
            resultList.add((int)(random.nextDouble()*max));
        }

        return resultList;
    }

    private ArrayList<Integer> generateBetaSet(int n, double density) {

        ArrayList<Integer> resultList = new ArrayList<>();
        double max=n/density;

        for (int i = 0; i < n; i++) {
            resultList.add((int)(Math.pow(random.nextDouble(),2)*max));
        }

        return resultList;
    }

    private int generateNumber(int min) {
        return random.nextInt((min*10) - min) + min;
    }


}
