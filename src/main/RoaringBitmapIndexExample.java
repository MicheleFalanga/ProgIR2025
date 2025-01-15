package main;

import org.roaringbitmap.RoaringBitmap;

import java.io.*;
import java.util.*;

public class RoaringBitmapIndexExample {

    // Crea un Roaring Bitmap Index per la colonna specificata
    public static Map<String, RoaringBitmap> createRoaringBitmapIndex(String fileName) {
        Map<String, RoaringBitmap> bitmapIndex = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {

                    // Split della riga in base alla virgola
                    String[] columns = line.split(",");
                    for(String value : columns){
                        // Aggiungi o recupera un RoaringBitmap per ogni valore unico
                        bitmapIndex.putIfAbsent(value, new RoaringBitmap());
                        RoaringBitmap bitmap = bitmapIndex.get(value);

                        // Aggiungi il numero di riga (lineNumber) al RoaringBitmap
                        bitmap.add(lineNumber);
                    }

                    lineNumber++;
                }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmapIndex;
    }
}
