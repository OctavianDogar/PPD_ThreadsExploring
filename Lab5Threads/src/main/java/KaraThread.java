import java.util.concurrent.Callable;

/**
 * Created by Octavian on 11/23/2016.
 */
public class KaraThread implements Callable {

    private Polynomial a;
    private Polynomial b;
    private Integer indexA;

    public KaraThread(Polynomial a,Polynomial b, Integer indexA) {
        this.a=a;
        this.b=b;
        this.indexA=indexA;
    }

    @Override
    public Polynomial call() {
        Polynomial dummy = a.computePolyAtIndex(indexA);
        return dummy.multiplyKara(b);
    }
}
