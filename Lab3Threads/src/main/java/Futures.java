import Model.Matrix;
import Callables.MultiplThread;
import Callables.SumThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dogaro on 01/11/2016.
 */
public class Futures {

    private static final Integer threadsNr = 4;
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

        double difference;

        ExecutorService ex = Executors.newWorkStealingPool();
        List<Callable<Matrix>> callables = new ArrayList<>();

        if(what==0) {
            for (int i = 0; i < threadsNr; i++) {
                callables.add(() -> new SumThread(a, b, rez).call());
            }
        }else{
            for (int i = 0; i < threadsNr; i++) {
                callables.add(() -> new MultiplThread(a, b, rez).call());
            }
        }

        long start_time = System.nanoTime();
        try {
            ex.invokeAll(callables)
                    .stream()
                    .map(matrixFuture -> {
                        try{
                            return matrixFuture.get();
                        }catch (ExecutionException | InterruptedException e){
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .forEach(matrix -> matrix.coalesce(rez));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ex.shutdown();


        long end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;


        System.out.println(rez.toString()+'\n'+
                "Computed in "+difference+"ms with "+threadsNr+" threads");
    }

}
