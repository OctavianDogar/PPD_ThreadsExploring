import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by dogaro on 15/11/2016.
 */
@Getter
@Setter
public class Polynomial {

    private List<Double> queficients;
    private Character pName;
    private Integer degree;

    public Polynomial(int n,Character pName){
        queficients = new ArrayList<>(Collections.nCopies(n, (double)0));
        this.pName = pName;
        degree = n;
    }

    public Double getValueAtIndex(Number x,Number n){
        //(coef right) *  x^position(real )
        return queficients.get((int)n) *Math.pow(Double.parseDouble(x.toString()),Double.parseDouble(n.toString())  );
    }

    public Double getCoefAt(Integer n){
        return queficients.get(n);
    }

    public void setCoefAt(Integer index, Double elem) {
        queficients.set(index, elem);
    }

    public Polynomial multiply(Polynomial b){
        Polynomial rez = new Polynomial(queficients.size()+b.getQueficients().size()-1,'z');

        for (int i=0;i<getQueficients().size();i++){
            for (int j=0;j<b.getQueficients().size();j++) {
                rez.setCoefAt(i + j,rez.getCoefAt(i+j)+ getCoefAt(i) * b.getCoefAt(j));
            }
        }
        return rez;
    }

    public Polynomial multiplyKara(Polynomial b){
        Polynomial rez = new Polynomial(queficients.size()+b.getQueficients().size()-1,'z');

        for (int i=0;i<getQueficients().size();i++){
            for (int j=0;j<b.getQueficients().size();j++) {

                BigInteger aval = new BigDecimal(getCoefAt(i)).toBigInteger();
                BigInteger bval = new BigDecimal(b.getCoefAt(i)).toBigInteger();

                BigInteger endval = karatsuba(aval,bval);

                rez.setCoefAt(i + j,rez.getCoefAt(i+j)+ endval.doubleValue());
            }
        }
        return rez;
    }
    public Polynomial coalesce(Polynomial rez){

        queficients.stream().forEach(aDouble -> rez.setCoefAt(  queficients.indexOf(aDouble),
                rez.getCoefAt(queficients.indexOf(aDouble))+aDouble));
        return rez;

    }

    public Polynomial computePolyAtIndex(int index){

        Polynomial aux = new Polynomial(index+1,'c');

        aux.setCoefAt(index,getCoefAt(index));

        return aux;
    }


    public BigInteger karatsuba(BigInteger x, BigInteger y) {

        // cutoff to brute force
        int MaxBitLength = Math.max(x.bitLength(), y.bitLength());
        if (MaxBitLength <= 2000) return x.multiply(y);                // optimize this parameter

        // number of bits divided by 2, rounded up
        MaxBitLength = (MaxBitLength / 2) + (MaxBitLength % 2);

        // x = a + 2^MaxBitLength b,   y = c + 2^MaxBitLength d
        BigInteger shiftedX = x.shiftRight(MaxBitLength);
        BigInteger optimisedX = x.subtract(shiftedX.shiftLeft(MaxBitLength));
        BigInteger shiftedY = y.shiftRight(MaxBitLength);
        BigInteger optimisedY = y.subtract(shiftedY.shiftLeft(MaxBitLength));

        // compute sub-expressions
        BigInteger firstLvlMultilp    = karatsuba(shiftedX, shiftedY);
        BigInteger optimMultipl = karatsuba(optimisedX, optimisedY);
        BigInteger finalMultipl  = karatsuba(shiftedX.add(optimisedX), shiftedY.add(optimisedY));

        return firstLvlMultilp.add(finalMultipl.subtract(firstLvlMultilp).subtract(optimMultipl).shiftLeft(MaxBitLength)).add(optimMultipl.shiftLeft(2*MaxBitLength));
    }

    public void fill(int... quefs){
        degree = quefs.length;
        queficients = IntStream.range(0,quefs.length)
                .mapToObj(i -> (double)quefs[i])
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        final String[] rez = {pName + "(x) = "};
        queficients.stream().forEach(aDouble -> rez[0] +=aDouble.toString()+"x^"+queficients.indexOf(aDouble)+"+");
        return rez[0].substring(0,rez[0].length()-1);
    }


}
