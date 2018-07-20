package second;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by Octavian on 12/4/2016.
 */
public class Main {

    public static List<Long> numbers;
    public static Integer threadsNr = 100;
    public static ConcurrentLinkedQueue<Long> queue;
    public static Long result = 0l;

    public static void main(String[] args) {
        init();
        run();
        System.out.println(result);
    }

    private static void run() {
        ExecutorService ex = Executors.newWorkStealingPool();
        queue = new ConcurrentLinkedQueue<>(numbers);
        Future<Long>ft=null;
        double difference;
        long start_time = System.nanoTime();

        while(queue.size()!=1){
            try{
                ft= ex.submit(new AddThread(queue.poll(),queue.poll()));
                System.out.println(queue);
            }catch (NullPointerException npe){
                result = queue.poll();
            }
            while (!ft.isDone()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) { }
            }
            try {
                queue.add(ft.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        ex.shutdown();
        long end_time = System.nanoTime();
        difference=(end_time - start_time)/1e6;

        System.out.println("Computed "+queue.poll()+" in "+difference+" miliseconds");    }

    public static void init(){
        numbers = LongStream.rangeClosed(0,2700).boxed().collect(Collectors.toList());
    }

}
