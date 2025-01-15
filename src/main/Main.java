import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import org.roaringbitmap.RoaringBitmap;
import main.RoaringBitmapIndexExample;

public class Main {

    public static void main(String[] args) throws Exception{

        String fileName = "src/datasets/census1881.csv";
        Map<String, RoaringBitmap> bitmapIndex = RoaringBitmapIndexExample.createRoaringBitmapIndex(fileName); // Colonna 'name' (indice 1)

        double sum = 0;
        double count = 0;

        // Stampa del Roaring Bitmap Index
        for (Map.Entry<String, RoaringBitmap> entry : bitmapIndex.entrySet()) {
            RoaringBitmap bitmap = entry.getValue();
            int max=0;
            for(int i : bitmap) {
                if(i > max) max=i;
            }
            sum += (double)bitmap.getCardinality()/(double) max;;
            count++;
        }
        System.out.println("Sum: " + sum);
        System.out.println("Count: " + count);
        System.out.println("Average: " + sum/count);

    }
}