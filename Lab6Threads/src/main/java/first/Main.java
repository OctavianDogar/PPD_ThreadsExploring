package first;

import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Octavian on 12/3/2016.
 */
public class Main {

    public static final Integer threadsNr = 17;
    public static Vector initial = new Vector();
    public static List<Integer> utilList=new ArrayList<>();
    private static Integer indexBiggestLower = 0;
    private static List<Object> intervals  = new ArrayList<>();
    private static List<Pair> intervalBounds = new ArrayList<>();

    public static void main(String[] args) {
        init();

        runNormal();

        System.out.println(initial.getRez());
    }


    public static void runNormal(){
        List<Thread> threads = new ArrayList<>();
        double difference;
        long start_time = System.nanoTime();

        for (int i=0;i<intervalBounds.size();i++){
            Integer from = (Integer) intervalBounds.get(i).getLeft();
            Integer to = (Integer) intervalBounds.get(i).getRight();
            Thread t = new Thread(new VectThread(from,to,initial.getList(),initial.getRez()));

            threads.add(t);
            t.start();
        }

        threads.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t = new Thread(new VectThread(0,initial.getList().size(),initial.getList(),initial.getRez()));
        t.start();
        long end_time = System.nanoTime();
        difference=(end_time - start_time)/1e6;

        System.out.println("computedNormal in "+difference+" miliseconds");

    }


    public static void runByExecutor(){

        ExecutorService ex = Executors.newFixedThreadPool(threadsNr);
        double difference;
        long start_time = System.nanoTime();



        for (int i=0;i<intervalBounds.size();i++){
            Integer from = (Integer) intervalBounds.get(i).getLeft();
            Integer to = (Integer) intervalBounds.get(i).getRight();
            ex.submit(new VectThread(from,to,initial.getList(),initial.getRez()));
        }

        ex.shutdown();
        long end_time = System.nanoTime();
        difference=(end_time - start_time)/1e6;

        System.out.println("computedByExecutor in "+difference+" miliseconds");

    }


    public static void init(){

        /*
        filling the initial vector array and initialising the
         */
        initial.fill(1,2,5,5,5,5,4,5,2,5,8,4,2,6,3,2,5,7,4,2,6,9,6,9,4);
        for(int i=1;i<=threadsNr;i++){
            utilList.add(new Integer((i*(i+1))/2));
        }

        if(!utilList.contains(threadsNr)){
            for (Integer i:utilList){
                if(utilList.get(utilList.indexOf(i)+1)>threadsNr &&
                        i<threadsNr){
                    indexBiggestLower  = utilList.indexOf(i)+1;
                    break;
                }
            }
        }else{
            indexBiggestLower = utilList.indexOf(threadsNr)+1;
        }
        System.out.println("IndexBiggestLower: "+indexBiggestLower);
        
        int intv = initial.getList().size()/indexBiggestLower;
        List<Integer> auxIntervalList = new ArrayList<>();

        System.out.println("Intv: "+intv);
        
        Cloner cloner = new Cloner();
        /*
        trimming the list to a divisible length
        List<Integer> parseList = initial.getList().subList(0,intv*indexBiggestLower);
         */

        /*
        because I forgot that each newInterval on the nextLine also contains the j-1 elem,
        the bound of indexBiggestLower*intv was no longer required
         */
        List<Integer> parseList = initial.getList();

        Pair auxPair = new Pair();

        int offset=0;
        for(int i=0;i<indexBiggestLower;i++){
            /*
            at start, the parsed list is the initial list
             */

            /*
            initialising the leftSide interval pair taking into consideration
            the offset of the trimmed ParseList w.r.t. the original list
             */
            offset = intv*i;
            auxPair.setLeft(offset);

            for(int j=0;j<parseList.size();j++){

                if(auxIntervalList.size()<intv){
                    /*
                    if the end of the partial list is reached, the accumulated elems are
                    dumped, and a new list is created for further adding
                     */
                    if(j==parseList.size()-1){
                        auxIntervalList.add(parseList.get(j));
                        /*
                        if the set has ended, the rightside of the pair is completed
                        and he pair is added to the pair list
                         */
                        auxPair.setRight(j+offset);
                        intervalBounds.add(cloner.deepClone(auxPair));

                        intervals.add(cloner.deepClone(auxIntervalList));
                        auxIntervalList.clear();
                    }else{
                        auxIntervalList.add(parseList.get(j));
                    }
                }else{
                    /*
                    also, if the interval is completed, the rightside of
                    the pair is set, and the pair added to the list
                     */
                    auxPair.setRight(j+offset);
                    intervalBounds.add(cloner.deepClone(auxPair));
                    auxPair.clear();
                    /*
                    when the interval is closed, I set the next left side of the pair
                     */
                    if(j!=parseList.size()-1) {
                        auxPair.setLeft(j+offset + 1);
                    }

                    /*
                    add the last and current omitted element, then add the list
                     */
                    auxIntervalList.add(parseList.get(j));
                    intervals.add(cloner.deepClone(auxIntervalList));
                    auxIntervalList.clear();
                }
            }
            /*
            printing the pyramid
             */
            for(int k=0;k<i*3;k++){
                System.out.print("    ");
            }
            System.out.println(parseList.toString());

            /*
            after the first batch of splits, the list is thinned by the first intv items
             */
            if(intv+1<parseList.size()){
                parseList = parseList.subList(intv,parseList.size());
            }
        }
    }

}
