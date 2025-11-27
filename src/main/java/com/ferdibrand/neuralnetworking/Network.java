public class Network {
    private final int  NUM_INPUT_NEURONS;
    private final int NUM_OUTPUT_NEURONS;
    private final int NUM_HIDDEN_LAYERS;
    private final int NUM_HIDDEN_NEURONS;
    private final int INPUT_LAYER_INDEX;
    private final int OUTPUT_LAYER_INDEX;
    private Matrix[] layers;
    private Matrix[] weights;
    private Matrix[] biases;
    private String dataFileName;
    private String answerFileName;

    public Network(int numInputNeurons, int numOutputNeurons, int numHiddenLayers, int numHiddenNeurons,
                   String dataFileName, String answerFileName) {
        this.NUM_INPUT_NEURONS = numInputNeurons;
        this.NUM_OUTPUT_NEURONS = numOutputNeurons;
        this.NUM_HIDDEN_LAYERS = numHiddenLayers;
        this.NUM_HIDDEN_NEURONS = numHiddenNeurons;
        this.INPUT_LAYER_INDEX = 0;
        this.OUTPUT_LAYER_INDEX = NUM_HIDDEN_LAYERS + 1;

        layers = new Matrix[NUM_HIDDEN_LAYERS + 2];
        layers[INPUT_LAYER_INDEX] = new Matrix(NUM_INPUT_NEURONS, 1);
        for (int i = 1; i <= numHiddenLayers; i++) {
            layers[i] = new Matrix(numHiddenNeurons, 1);
        }
        layers[OUTPUT_LAYER_INDEX] = new Matrix(NUM_OUTPUT_NEURONS, 1);

          // layer[i] = weight[i-1].multiply(layer[i-1]).add(bias[i-1]);
        weights = new Matrix[NUM_HIDDEN_LAYERS + 1];
        for (int i = 0; i < OUTPUT_LAYER_INDEX; i++) {
            weights[i] = new Matrix(layers[i+1].getNumRow(),layers[i].getNumRow());
        }

        biases = new Matrix[NUM_HIDDEN_LAYERS + 1];
        for (int i = 0; i < OUTPUT_LAYER_INDEX; i++) {
            biases[i] = new Matrix(layers[i+1].getNumRow(), 1);
        }
    }

    public String toString() {
        String str = "";
        str += "Inputs: " + NUM_INPUT_NEURONS + ", Outputs: " + NUM_OUTPUT_NEURONS
                           + ", Hidden layers: " + NUM_HIDDEN_LAYERS + ", Neurons per hidden layer: "
                           + NUM_HIDDEN_NEURONS;
        str += "\nLayers:\n";
        for (Matrix m : layers) {
            str += m;
            str += "\n";
        }
        str += "\nWeights:\n";
        for (Matrix m : weights) {
            str += m;
            str += "\n";
        }
        str += "\nBiases:\n";
        for (Matrix m : biases) {
            str += m;
            str += "\n";
        }
        return str;
    }
}
