package main;

import it.uniroma3.mat.extendedset.intset.ConciseSet;
import org.roaringbitmap.RoaringBitmap;
import java.util.BitSet;

public class Bitmap {
    int type;
    RoaringBitmap roaringBitmap;
    BitSet bitset;
    ConciseSet conciseSet;


    public Bitmap(int type) {
        this.type=type;
        switch (type) {
            case 1:
                roaringBitmap = new RoaringBitmap();
                break;
            case 2:
                bitset = new BitSet();
                break;
            case 3:
                //simulate WAH=true
                conciseSet = new ConciseSet();
                break;
        }
    }

    public void add(int value) {
        switch (type) {
            case 1:
                roaringBitmap.add(value);
                break;
            case 2:
                bitset.set(value);
                break;
            case 3:
                conciseSet.add(value);
                break;
        }
    }

    public int size() {
        switch (type) {
            case 1:
                return roaringBitmap.getSizeInBytes();
            case 2:
                return bitset.size();
            case 3:
                return conciseSet.toByteBuffer().remaining();
            default:
                return 0;
        }
    }


}
