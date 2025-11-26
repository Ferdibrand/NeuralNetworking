import java.util.*;
import java.io.*;

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

        Matrix c = Matrix.transpose(a);
        Matrix d = Matrix.subtract(a, b);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }

}
