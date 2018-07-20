import Model.Matrix;
import Threads.MultiplThread;
import Threads.SumThread;
import Waiters.MultiplSecondThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dogaro on 01/11/2016.
 */
public class MultiplSecond {

    private static final Integer threadsFirstNr = 4;
    private static final Integer threadsSecondNr = 80;
    private static final Integer mBounds = 3;

    public static void main(String [] args){

        operate();

    }
    private static void operate() {
        Matrix a = new Matrix(mBounds,mBounds);
        a.populate();
        System.out.println("Matrix a: "+'\n'+a);
        Matrix b = new Matrix(mBounds,mBounds);
        b.populate();
        System.out.println("Matrix b: "+'\n'+b);
        Matrix firstRez = new Matrix(mBounds,mBounds);
        firstRez.populateNulls();
        Matrix c = new Matrix(mBounds,mBounds);
        c.populate();
        Matrix lastRez = new Matrix(mBounds,mBounds);
        lastRez.populateNulls();

        List<Thread> threads = new ArrayList<>();


        long start_time = System.nanoTime();

            for (int i = 0,j=0; i < threadsFirstNr; i++, j++) {
                Thread t = new Thread(new MultiplThread(a, b, firstRez));
                threads.add(t);
                t.start();
                if(j<threadsSecondNr){
                    Thread w = new Thread(new MultiplSecondThread(firstRez, c, lastRez));
                    threads.add(w);
                    w.start();
                }

            }
        threads.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;

        System.out.println("First resulted matrix: "+'\n'+firstRez.toString());

        System.out.println("Matrix c: "+'\n'+c);

        System.out.println("Second resulted matrix: "+'\n'+lastRez.toString()+'\n'+
                "Computed in "+difference+"ms with "+threadsSecondNr+" threads");
    }

}
