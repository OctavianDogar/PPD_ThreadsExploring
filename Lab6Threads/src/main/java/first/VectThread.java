package first;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Octavian on 12/2/2016.
 */
@Getter
@Setter
public class VectThread implements Runnable {

    private Vector vector;

    public VectThread(Integer from, Integer to, List<Integer> list, List<Integer> rez){
        vector = new Vector(from,to,list,rez);
    }

    @Override
    public void run(){
        vector.compute();
    }
}
