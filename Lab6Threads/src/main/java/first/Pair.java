package first;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Octavian on 10/23/2016.
 */

@Getter
@Setter
@NoArgsConstructor
public class Pair<K,V>{
    private K left;
    private V right;

    public Pair(K left, V right){
        this.left = left;
        this.right = right;
    }

    public void clear(){
        this.left=null;
        this.right=null;
    }

    @Override
    public String toString() {
        return "( "+left+","+right+" )";
    }
}