package Threads;

import Model.Matrix;
import Model.Pair;

import java.util.List;

/**
 * Created by dogaro on 01/11/2016.
 */
public class SumThread implements Runnable {

    private List<Pair> pairs;
    private Matrix a;
    private Matrix b;
    private Matrix rez;

    public SumThread(Matrix a,Matrix b,Matrix rez){
        this.a = a;
        this.b = b;
        this.rez = rez;
        pairs = a.pairUpForAdd(b);
    }

    @Override
    public void run() {
        a.startSumming(b,pairs,rez);
    }
}
