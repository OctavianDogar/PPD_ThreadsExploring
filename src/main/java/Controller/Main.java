package Controller;

import Model.Matrix;
import Threads.MultiplThread;
import Threads.SumThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dogaro on 01/11/2016.
 */
public class Main {

    private static final Integer threadsNr = 20;
    private static final Integer mBounds = 600;

    public static void main(String [] args){

        operate(0);

    }

    private static void operate(int what) {
        Matrix a = new Matrix(mBounds,mBounds);
        a.populate();
//        System.out.println(a);
        Matrix b = new Matrix(mBounds,mBounds);
        b.populate();
//        System.out.println(b);
        Matrix rez = new Matrix(mBounds,mBounds);
        rez.populateNulls();

        List<Thread> threads = new ArrayList<>();


        long start_time;
        long end_time;
        double difference;

        for(int k=1; k<=threadsNr;k++){
            start_time = System.nanoTime();
            if(what==0){
                for (int i=0; i<k;i++){
                    Thread t = new Thread(new SumThread(a,b,rez));
                    threads.add(t);
                    t.start();
                }
            }else {
                for (int i = 0; i < k; i++) {
                    Thread t = new Thread(new MultiplThread(a, b, rez));
                    threads.add(t);
                    t.start();

                }
            }
            threads.stream().forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            end_time = System.nanoTime();
            difference = (end_time - start_time)/1e6;
            System.out.println("Computed in "+difference+"ms with "+k+" threads");

        }





    }

}
