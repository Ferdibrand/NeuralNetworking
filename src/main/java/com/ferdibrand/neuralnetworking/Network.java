import java.util.*;

public class Network {
    private int numInputs;
    private int numOutputs;
    private int numHiddenLayers;
    private int numNeuronsPerLayer;
    private Matrix inputLayer;
    private Matrix outputLayer;
    private ArrayList<Matrix> hiddenLayers;
    private ArrayList<Matrix> weights;
    private ArrayList<Matrix> biases;
    private String dataFileName;
    private String answerFileName;

    public Network(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerLayer,
                   String dataFileName, String answerFileName) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numNeuronsPerLayer = numNeuronsPerLayer;

        inputLayer.setOrder(numInputs, 1);
        outputLayer.setOrder(numOutputs, 1);

        for (int i = 0; i < numHiddenLayers; i++) {
            hiddenLayers.add(new Matrix(numNeuronsPerLayer, 1));
        }

    }
}
