package first;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Octavian on 12/2/2016.
 */
@Getter
@Setter
@NoArgsConstructor
public class Vector {

    private List<Integer> list;
    private List<Integer> rez;
    private Integer from;
    private Integer to;

    public Vector(Integer from, Integer to, List<Integer> list,List<Integer> rez) {
        this.from = from;
        this.to = to;
        this.list = list;
        this.rez = rez;
    }

    public void compute(){
        rez.set(from,list.get(from));
        for(int i=from+1;i<to;i++){
            rez.set(i,rez.get(i-1)+list.get(i));
        }
    }

    public void fill(int... quefs){

        list = IntStream.range(0,quefs.length)
                .mapToObj(i -> quefs[i])
                .collect(Collectors.toList());


        rez = new ArrayList<>(Collections.nCopies(list.size(),0));
    }
}
