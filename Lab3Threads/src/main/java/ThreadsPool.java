import Model.Matrix;
import Callables.MultiplThread;
import Callables.SumThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dogaro on 01/11/2016.
 */
public class ThreadsPool {

    private static final Integer threadsNr = 1000;
    private static final Integer mBounds = 5;

    public static void main(String [] args){

        operate(0);

    }

    private static void operate(int what) {
        Matrix a = new Matrix(mBounds,mBounds);
        a.populate();
        System.out.println(a);
        Matrix b = new Matrix(mBounds,mBounds);
        b.populate();
        System.out.println(b);
        Matrix rez = new Matrix(mBounds,mBounds);
        rez.populateNulls();

        ExecutorService ex = Executors.newFixedThreadPool(threadsNr);

        double difference;



        if(what==0){
            long start_time = System.nanoTime();
            for (int i=0; i<threadsNr; i++){
                ex.submit(new SumThread(a,b,rez));
            }
            ex.shutdown();

            long end_time = System.nanoTime();
            difference = (end_time - start_time)/1e6;

        }else{

            long start_time = System.nanoTime();
            for (int i=0; i<threadsNr; i++){
                ex.submit(new MultiplThread(a,b,rez));
            }
            ex.shutdown();
            long end_time = System.nanoTime();
            difference = (end_time - start_time)/1e6;
        }



        System.out.println(rez.toString()+'\n'+
                "Computed in "+difference+"ms with "+threadsNr+" threads");
    }

}
