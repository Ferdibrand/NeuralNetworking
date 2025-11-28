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
    private final int BATCH_SIZE;

    public Network(int numInputNeurons, int numOutputNeurons, int numHiddenLayers, int numHiddenNeurons,
                   String dataFileName, String answerFileName, int BATCH_SIZE) {
        this.NUM_INPUT_NEURONS = numInputNeurons;
        this.NUM_OUTPUT_NEURONS = numOutputNeurons;
        this.NUM_HIDDEN_LAYERS = numHiddenLayers;
        this.NUM_HIDDEN_NEURONS = numHiddenNeurons;
        this.INPUT_LAYER_INDEX = 0;
        this.OUTPUT_LAYER_INDEX = NUM_HIDDEN_LAYERS + 1;
        this.dataFileName = dataFileName;
        this.answerFileName = answerFileName;
        this.BATCH_SIZE = BATCH_SIZE;

        layers = new Matrix[NUM_HIDDEN_LAYERS + 2];
        layers[INPUT_LAYER_INDEX] = new Matrix(NUM_INPUT_NEURONS, BATCH_SIZE);
        for (int i = 1; i <= numHiddenLayers; i++) {
            layers[i] = new Matrix(numHiddenNeurons, BATCH_SIZE);
        }
        layers[OUTPUT_LAYER_INDEX] = new Matrix(NUM_OUTPUT_NEURONS, BATCH_SIZE);

          // layer[i] = weight[i-1].multiply(layer[i-1]).add(bias[i-1]);
        weights = new Matrix[NUM_HIDDEN_LAYERS + 1];
        for (int i = 0; i < OUTPUT_LAYER_INDEX; i++) {
            weights[i] = new Matrix(layers[i+1].getNumRow(),layers[i].getNumRow());
        }

        biases = new Matrix[NUM_HIDDEN_LAYERS + 1];
        for (int i = 0; i < OUTPUT_LAYER_INDEX; i++) {
            biases[i] = new Matrix(layers[i+1].getNumRow(), BATCH_SIZE);
        }
    }

    public Matrix[] getLayers() {
        return layers;
    }

    public Matrix[] getWeights() {
        return weights;
    }

    public Matrix[] getBiases() {
        return biases;
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

    public void randomiseWeightsBiases() {
        for (Matrix m : weights) {
            for (int row = 0; row < m.getNumRow(); row++) {
                for (int col = 0; col < m.getNumCol(); col++) {
                    m.setValue(row, col, Math.random());
                }
            }
        }
        for (Matrix m : biases) {
            for (int row = 0; row < m.getNumRow(); row++) {
                double random = Math.random();
                for (int col = 0; col < m.getNumCol(); col++) {
                    m.setValue(row, col, random);
                }
            }
        }
    }

    //each column of input is a set of training data; order: NUM_INPUT_NEURONSxBATCH_SIZE
    public Matrix feedForward(Matrix input) {
        layers[INPUT_LAYER_INDEX] = input;
        for (int i = 1; i <= OUTPUT_LAYER_INDEX; i++) {
            layers[i] = weights[i - 1].multiply(layers[i - 1]).add(biases[i - 1]).sigmoid();
        }
        Matrix output = layers[OUTPUT_LAYER_INDEX];
        return output;
    }

    public double cost(Matrix outputPredicted, Matrix outputTrue) {
        Matrix losses = new Matrix(NUM_OUTPUT_NEURONS, BATCH_SIZE);
        double sum = 0;
        for (int row = 0; row < NUM_OUTPUT_NEURONS; row++) {
            for (int col = 0; col < BATCH_SIZE; col++) {
                sum += loss(outputPredicted.getValue(row, col), outputTrue.getValue(row, col));
            }
        }
        return sum / (NUM_OUTPUT_NEURONS * BATCH_SIZE);
    }

    //For binary output
    public double loss(double outputPredicted, double outputTrue) {
        return -(outputTrue * Math.log(outputPredicted) + (1 - outputTrue) * Math.log(1 - outputPredicted));
    }
}
