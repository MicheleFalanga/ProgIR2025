package main;

import org.roaringbitmap.RoaringBitmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class BitSetIndex {
    public Map<String, BitSet> bitmap;
    public long totalRows;

    public BitSetIndex(String fileName) {
        bitmap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {

                String[] columns = line.split(",");
                for(String value : columns){
                    bitmap.putIfAbsent(value, new BitSet());
                    BitSet val = bitmap.get(value);
                    val.set(lineNumber);
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

        for (BitSet bitmap : bitmap.values()) {
            totalCardinality += bitmap.cardinality();
            totalUniqueValues += bitmap.size()-1;
            sum += bitmap.cardinality()/(long)bitmap.size()-1;
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

        for (Map.Entry<String, BitSet> entry : bitmap.entrySet()) {
            BitSet bitmap = entry.getValue();

            // Calcola lo spazio occupato dalla bitmap in byte, poi convertilo in bit
            long bitmapSizeInBits = bitmap.size();

            // Somma la dimensione in bit e il numero di elementi
            totalSpaceInBits += bitmapSizeInBits;
            totalItems += bitmap.cardinality();
        }

        // Calcola lo spazio per item in bit
        return totalItems > 0 ? totalSpaceInBits / totalItems : 0;
    }

}
