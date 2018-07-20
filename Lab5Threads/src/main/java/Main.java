import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by dogaro on 16/11/2016.
 */
public class Main {

    private static final Integer threadsNr = 4;
    private static Polynomial a = new Polynomial(0,'a');
    private static Polynomial b = new Polynomial(0,'b');
    private static Polynomial c;


    public static void main(String[] args) {

        a.fill(1,2,3,4,5);
        b.fill(10,20,30,40,50);

        computeThreadic(a,b);
        computeNonthreadic(a,b);
        computeKaraThreadic(a,b);
        computeKaraNonthreadic(a,b);

    }

    private static void computeNonthreadic(Polynomial a, Polynomial b){
        double difference;
        long start_time = System.nanoTime();
        System.out.println(a.multiply(b));
        long end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;
        System.out.println("computed nonthreadic in "+difference+" miliseconds");
    }

    private static void computeKaraNonthreadic(Polynomial a,Polynomial b){
        double difference;
        long start_time = System.nanoTime();
        a.multiply(b);
        long end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;
        System.out.println("computed kara nonthreadic in "+difference+" miliseconds");
    }

    private static void computeThreadic(Polynomial a, Polynomial b) {

        c = new Polynomial(b.getDegree()+a.getDegree()-1,'c');

        double difference;

        ExecutorService ex = Executors.newWorkStealingPool();
        List<Callable<Polynomial>> callables = new ArrayList<>();

        IntStream.range(0,a.getDegree())//-1?
                .mapToObj(value -> new Pair(value,a.computePolyAtIndex(value)))
                .forEach(dummy -> callables.add(new PolyThread((Polynomial) dummy.getRight(),b,(int)dummy.getLeft())));

        long start_time = System.nanoTime();

        try {
            ex.invokeAll(callables)
                    .stream()
                    .map(polyFuture -> {
                        try{
                            return polyFuture.get();
                        }catch (ExecutionException | InterruptedException e){
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .forEach(incompletePoly -> incompletePoly.coalesce(c));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;

        System.out.println("computedFinal in "+difference+" miliseconds");

        ex.shutdown();
    }

    private static void computeKaraThreadic(Polynomial a, Polynomial b) {

        c = new Polynomial(b.getDegree()+a.getDegree()-1,'c');

        double difference;

        ExecutorService ex = Executors.newWorkStealingPool();
        List<Callable<Polynomial>> callables = new ArrayList<>();

        IntStream.range(0,a.getDegree())//-1?
                .mapToObj(value -> new Pair(value,a.computePolyAtIndex(value)))
                .forEach(dummy -> callables.add(new KaraThread((Polynomial) dummy.getRight(),b,(int)dummy.getLeft())));

        long start_time = System.nanoTime();

        try {
            ex.invokeAll(callables)
                    .stream()
                    .map(polyFuture -> {
                        try{
                            return polyFuture.get();
                        }catch (ExecutionException | InterruptedException e){
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .forEach(incompletePoly -> incompletePoly.coalesce(c));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;

        System.out.println("computedKaraThreadic in "+difference+" miliseconds");

        ex.shutdown();
    }

}
