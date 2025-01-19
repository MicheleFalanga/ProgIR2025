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
                conciseSet = new ConciseSet();
                break;
            case 4:
                conciseSet = new ConciseSet(true);
                break;
            default:
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
            case 4:
                conciseSet.add(value);
                break;
        }
    }

    public double size() {
        switch (type) {
            case 1:
                return roaringBitmap.getSizeInBytes();
            case 2:
                return bitset.size();
            case 3:
            case 4:
                return conciseSet.size()*conciseSet.collectionCompressionRatio() * 4 * 8;
            default:
                return 0;
        }
    }

    public int last() {
        switch (type) {
            case 1:
                return roaringBitmap.last();
            case 2:
                return bitset.length()-1;
            case 3:
            case 4:
                return conciseSet.last();
            default:
                return 0;
        }
    }

    public void remove(int value) {
        switch (type) {
            case 1:
                roaringBitmap.remove(value);
                break;
            case 2:
                bitset.clear(value);
                break;
            case 3:
            case 4:
                conciseSet.remove(value);
                break;
        }
    }

    public Bitmap and(Bitmap bitmap) {
        switch (type) {
            case 1:
                RoaringBitmap tempMapR;
                tempMapR = roaringBitmap.clone();
                tempMapR.and(bitmap.roaringBitmap);
                Bitmap tempR=new Bitmap(type);
                tempR.roaringBitmap=tempMapR;
                return tempR;
            case 2:
                BitSet tempMapB;
                tempMapB = (BitSet)bitset.clone();
                tempMapB.and(bitmap.bitset);
                Bitmap tempB=new Bitmap(type);
                tempB.bitset=tempMapB;
                return tempB;
            case 3:
            case 4:
                Bitmap tempC=new Bitmap(type);
                tempC.conciseSet=conciseSet.intersection(bitmap.conciseSet);
                return tempC;
            default:
                return new Bitmap(0);
        }
    }

    public Bitmap or(Bitmap bitmap) {
        switch (type) {
            case 1:
                RoaringBitmap tempMapR;
                tempMapR = roaringBitmap.clone();
                tempMapR.or(bitmap.roaringBitmap);
                Bitmap tempR=new Bitmap(type);
                tempR.roaringBitmap=tempMapR;
                return tempR;
            case 2:
                BitSet tempMapB;
                tempMapB = (BitSet)bitset.clone();
                tempMapB.or(bitmap.bitset);
                Bitmap tempB=new Bitmap(type);
                tempB.bitset=tempMapB;
                return tempB;
            case 3:
            case 4:
                Bitmap tempC=new Bitmap(type);
                tempC.conciseSet=conciseSet.union(bitmap.conciseSet);
                return tempC;
            default:
                return new Bitmap(0);
        }
    }



}
