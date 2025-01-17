import java.util.*;

import main.BitSetIndex;
import org.roaringbitmap.RoaringBitmap;
import main.RoaringBitmapIndex;

public class Main {

    public static void main(String[] args) throws Exception{

        String path="src/datasets/";
        String[] fileNames ={"census-income.data","wikileaks.csv","weather_sept_85.csv"};
        /*
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
        */

        int n = 1000000;
        double density = 0.001;

        List<Integer> uniformSet = generateUniformSet(n, density);
        System.out.println("Densità effettiva: " + (double) uniformSet.size() / n);
        System.out.println(uniformSet.size() + " " + n);
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        for (int elem : uniformSet) {
            roaringBitmap.add(elem);
        }
        System.out.println("Densità effettiva: " + (double) roaringBitmap.getCardinality() / roaringBitmap.last());
        System.out.println(roaringBitmap.getCardinality() + " " + roaringBitmap.last());
        BitSet bitset = new BitSet();
        for (int elem : uniformSet) {
            bitset.set(elem);
        }
        System.out.println("Densità effettiva: " + (double) bitset.cardinality() / bitset.length());
        System.out.println(bitset.cardinality() + " " + bitset.length());
    }

    private static String getRandomKey(Map<String, RoaringBitmap> map) {
        Random random = new Random();

        String[] groups = map.keySet().toArray(new String[0]);

        return groups[random.nextInt(groups.length)];
    }

    public static List<Integer> generateUniformSet(int n, double density) {
        Random random = new Random();
        List<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (random.nextDouble() < density) {
                resultList.add(i); // Aggiunge l'indice con probabilità data dalla densità
            }
        }

        return resultList;
    }

}