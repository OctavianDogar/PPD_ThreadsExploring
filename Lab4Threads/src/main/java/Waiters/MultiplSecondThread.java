package Waiters;

import Model.Matrix;
import Model.Pair;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by dogaro on 01/11/2016.
 */
public class MultiplSecondThread implements Runnable {

    private List<Pair> pairs;
    private Matrix a;
    private Matrix b;
    private Matrix rez;

    public MultiplSecondThread(Matrix a,Matrix b,Matrix rez){
        this.a = a;
        this.b = b;
        this.rez = rez;
        pairs = a.pairUpForAdd(b);
    }

    @Override
    public void run() {
        a.startMultiplSecond(b,pairs,rez);
    }

}
