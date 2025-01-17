package main;

import org.roaringbitmap.RoaringBitmap;

import java.io.*;
import java.util.*;

public class RoaringBitmapIndex {
    public HashMap<String, RoaringBitmap> bitmap;
    public long totalRows;

    public RoaringBitmapIndex(String fileName) {
        bitmap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {

                    String[] columns = line.split(",");
                    for(String value : columns){
                        bitmap.putIfAbsent(value, new RoaringBitmap());
                        RoaringBitmap val = bitmap.get(value);
                        val.add(lineNumber);
                    }

                    lineNumber++;
                }
                totalRows=lineNumber;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calculateDensity() {
        long totalCardinality = 0;
        long totalUniqueValues = 0;

        long sum=0;
        long count=0;

        for (RoaringBitmap bitmap : bitmap.values()) {
            totalCardinality += bitmap.getLongCardinality();
            totalUniqueValues += bitmap.lastSigned();
            sum += bitmap.getLongCardinality()/(long)bitmap.last();
            //System.out.println("Cardinality: " + bitmap.getLongCardinality() + " Last: "+ bitmap.lastSigned());
        }

        // Usa `long` per calcolare il denominatore
        long denominator = totalRows * totalCardinality;

        // Calcola la densità
        return (double) totalUniqueValues / denominator;
    }

    public double calculateSpacePerItem() {
        double totalSpaceInBits = 0;
        long totalItems = 0;

        for (Map.Entry<String, RoaringBitmap> entry : bitmap.entrySet()) {
            RoaringBitmap bitmap = entry.getValue();

            // Calcola lo spazio occupato dalla bitmap in byte, poi convertilo in bit
            long bitmapSizeInBits = bitmap.getSizeInBytes()*8;

            // Somma la dimensione in bit e il numero di elementi
            totalSpaceInBits += bitmapSizeInBits;
            totalItems += bitmap.getLongCardinality();
        }

        // Calcola lo spazio per item in bit
        return totalItems > 0 ? totalSpaceInBits / totalItems : 0;
    }
}
