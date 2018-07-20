package second;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;

/**
 * Created by Octavian on 12/4/2016.
 */
@Getter
@Setter
@AllArgsConstructor
public class AddThread implements Callable {

    private Long a;
    private Long b;

    @Override
    public Long call() {
        return a+b;
    }
}
