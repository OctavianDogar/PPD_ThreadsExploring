import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;

/**
 * Created by dogaro on 15/11/2016.
 */
@Getter
@Setter
public class PolyThread implements Callable{

    private Polynomial a;
    private Polynomial b;
    private Integer indexA;

    public PolyThread(Polynomial a,Polynomial b, Integer indexA) {
        this.a=a;
        this.b=b;
        this.indexA=indexA;
    }

    @Override
    public Polynomial call() {
        Polynomial dummy = a.computePolyAtIndex(indexA);
        return dummy.multiply(b);
    }
}
