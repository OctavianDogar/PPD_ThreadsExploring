import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dogaro on 15/11/2016.
 */
public class Test {

    public static void main(String []args){

//        Polynomial p =  new Polynomial(5,'a');
//        Polynomial q =  new Polynomial(5,'b');
//
//        p.fill(1,2,3,4,5);
//        q.fill(10,20,30,40,50);
//
//        System.out.println(p);
//        System.out.println(q);
//
//        System.out.println(p.multiply(q));
        ArrayList<Integer> aux = new ArrayList<>();

        int as =5;
        int [] rez = new int []{10,20,30,40,50,20,40,60,80,100,30,60,90,120,150,40,80,120,160,200,50,100,150,200,250,0,0,0};
        int [] frez = new int[as*as+1];
        int x=0;
        for(int i=0; i<as;i++){
            for(int j = 0;j<as;j++){
                frez[i+j]= frez[i+j]+rez[x];
                x+=1;
            }
        }
        for(int i=0; i<as*2-1;i++){
            System.out.print(frez[i]+" ");
        }
    }
//10 40 100 200 350 440 460 400 250 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 26

}
