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

        layers[INPUT_LAYER_INDEX] = new Matrix(NUM_INPUT_NEURONS, 1);
        for (int i = 1; i <= numHiddenLayers; i++) {
            layers[i] = new Matrix(numHiddenNeurons, 1);
        }
        layers[OUTPUT_LAYER_INDEX] = new Matrix(NUM_OUTPUT_NEURONS, 1);

        /*
           [x] [x]
           [x] [x]
               [x]
               [x]

           xx  x   x
           xx  x   x
           xx      x
           xx      x

           layer[i] = weight[i-1].multiply(layer[i-1]).add(bias[i-1]);
         */


    }
}
