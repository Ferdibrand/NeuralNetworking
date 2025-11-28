public class Main {
    public static void main(String[] args) {
        System.out.println("Running...");
        Network n1 = new Network(2, 3, 3, 4, "s", "s", 2);
        Matrix inputs = new Matrix(2, 2, new double[][] {
                {1, 3},
                {2, 3}
        });
        n1.randomiseWeightsBiases();
        System.out.println(n1.feedForward(inputs));
        System.out.println(n1);
    }

}
