package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dogaro on 01/11/2016.
 */
@Getter
@Setter
public class Matrix {


    private Integer[][] matrix;
    private Random random;

    public Matrix(Integer cols, Integer rows){
        matrix = new Integer[cols][rows];
        random = new Random();
    }

    public Integer getColNr(){
        return matrix.length;
    }

    public Integer getLinesNr(){
        return matrix[0].length;
    }

    public Integer getAt(int col,int row){
        return matrix[col][row];
    }

    public void setAt(int col, int row, int val){
        matrix[col][row] = val;
    }

    public List<Pair> pairUpForAdd(Matrix b){
        List<Pair> rez = new ArrayList<>();
        for(int i=0;i<getColNr();i++){
            for(int j=0; j<getLinesNr();j++){
                rez.add(new Pair(i,j));
            }
        }
        return rez;
    }

    public void startSumming(Matrix b,List<Pair> list, Matrix rez){
        while(list.size()>0){
            int index = random.nextInt(list.size());
            syncSumRemove(b, list, rez, index);
        }
    }

    public Matrix startSummingC(Matrix b,List<Pair> list, Matrix rez){
        while(list.size()>0){
            int index = random.nextInt(list.size());
            syncSumRemove(b, list, rez, index);
        }
        return rez;
    }

    public void startMultiplSecond(Matrix b, List<Pair> list, Matrix rez){
        while(list.size()>0){
            int index = random.nextInt(list.size());
            while(getAt((int)list.get(index).getKey(),(int)list.get(index).getValue()).equals(0)){
                try {
                    list.get(index).wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (list.get(index)) {
                int partialSum = 0;
                int rezLine = (int) list.get(index).getValue();
                int rezCol = (int) list.get(index).getKey();

                for (int i = 0; i < getLinesNr(); i++) { //assuming its quadratic
                    partialSum += getAt(i, rezLine) * b.getAt(rezCol, i);
                }
                rez.setAt(rezCol, rezLine, partialSum);

                list.remove(index);

            }
        }
    }


    private void syncSumRemove(Matrix b, List<Pair> list, Matrix rez, int index) {
        synchronized (list.get(index)){
            int ownVal = getAt((int)list.get(index).getKey(),(int)list.get(index).getValue());
            int otherVal = b.getAt((int)list.get(index).getKey(),(int)list.get(index).getValue());
            rez.setAt((int)list.get(index).getKey(),(int)list.get(index).getValue(),ownVal+otherVal);
            list.remove(index);
        }
    }


    public void coalesce(Matrix b){
        for(int i=0; i<getColNr();i++){
            for(int j=0; j<getLinesNr();j++){
                if(!getAt(i,j).equals(0) && b.getAt(i,j).equals(0)){
                    b.setAt(i,j,getAt(i,j));
                }
            }
        }
    }

    public void startMultiplying(Matrix b, List<Pair> list, Matrix rez){
        while(list.size()>0){
            int index = random.nextInt(list.size());
            synchronized (list.get(index)) {
                int partialSum = 0;
                int rezLine = (int) list.get(index).getValue();
                int rezCol = (int) list.get(index).getKey();

                for (int i = 0; i < getLinesNr(); i++) { //assuming its quadratic
                    partialSum += getAt(i, rezLine) * b.getAt(rezCol, i);
                }
                rez.setAt(rezCol, rezLine, partialSum);

                list.remove(index);
            }
        }
    }

    public Matrix startMultiplyingC(Matrix b, List<Pair> list, Matrix rez){
        while(list.size()>0){
            int index = random.nextInt(list.size());
            synchronized (list.get(index)) {
                int partialSum = 0;
                int rezLine = (int) list.get(index).getValue();
                int rezCol = (int) list.get(index).getKey();

                for (int i = 0; i < getLinesNr(); i++) { //assuming its quadratic
                    partialSum += getAt(i, rezLine) * b.getAt(rezCol, i);
                }
                rez.setAt(rezCol, rezLine, partialSum);

                list.remove(index);
            }
        }
        return rez;
    }

    public void populate(){

        for(int i=0;i<getColNr();i++){
            for(int j=0; j<getLinesNr();j++){
                setAt(i,j,random.nextInt(30));
            }
        }
    }
    public void populateNulls(){

        for(int i=0;i<getColNr();i++){
            for(int j=0; j<getLinesNr();j++){
                setAt(i,j,0);
            }
        }
    }


    @Override
    public String toString(){
        String rez="";
        for(int i=0;i<getColNr();i++){
            for(int j=0; j<getLinesNr();j++){
                if(getAt(j,i).toString().length()==1){
                    rez+=getAt(j,i)+"  ";
                }
                else{
                    rez+=getAt(j,i)+" ";
                }
            }
            rez+='\n';
        }
        return rez;
    }

}
