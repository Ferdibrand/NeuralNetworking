import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running...");
        Matrix a = new Matrix(2, 2, new ArrayList<ArrayList<Double>>(
                Arrays.asList(
                        new ArrayList<Double>(Arrays.asList(3.0, 2.0)),
                        new ArrayList<Double>(Arrays.asList(1.0, 4.0))
                )));
        Matrix b = new Matrix(2, 2, new ArrayList<ArrayList<Double>>(
                Arrays.asList(
                        new ArrayList<Double>(Arrays.asList(5.0, 0.0)),
                        new ArrayList<Double>(Arrays.asList(6.0, 7.0))
                )));

        Matrix c = a.transpose();
        Matrix d = a.subtract(b);
        d = d.mMultiply(a);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }

}
