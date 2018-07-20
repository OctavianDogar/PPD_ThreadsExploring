package Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by dogaro on 01/11/2016.
 */

@Getter
@Setter
@NoArgsConstructor
public class Pair<K,V>{
    private K key;
    private V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return   ""+'\n'+'\t' + key +
                '\t'+'\t'+'\t'+'\t' + value;
    }
}