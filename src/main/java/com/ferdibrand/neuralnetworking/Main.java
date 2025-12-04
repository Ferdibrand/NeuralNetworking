package com.ferdibrand.neuralnetworking;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running..");
        Network n1 = new Network(16, 1, 3, 10, "src/main/resources/com/ferdibrand/neuralnetworking/inputs.csv",
                "src/main/resources/com/ferdibrand/neuralnetworking/outputs.csv", 100, 0.01, 100000);
        n1.train();
        Matrix test = new Matrix(16, 1, new double[][] {{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {0}, {0}, {0}, {1}});
        System.out.println(n1.predict(test));
    }

}
