package com.ferdibrand.neuralnetworking;

import java.util.Scanner;
import java.io.File;

public class Network {
    private final int  NUM_INPUT_NEURONS;
    private final int NUM_OUTPUT_NEURONS;
    private final int NUM_HIDDEN_LAYERS;
    private final int NUM_HIDDEN_NEURONS;
    private final int INPUT_LAYER_INDEX;
    private final int OUTPUT_LAYER_INDEX;
    private final int BATCH_SIZE;
    private final double LEARNING_RATE;
    private double[] costs;
    private Matrix[] layers;
    private Matrix[] weights;
    private Matrix[] biases;
    private Matrix[] dWeights;
    private Matrix[] dBiases;
    private Matrix[] dPreActivation;
    private Matrix[] dActivation;
    private String dataFileName;
    private String answerFileName;
    private int epoch = 0;
    private int NUM_EPOCHS;
    private Matrix input;
    private Matrix outputTrue;
    private double[][] trainInput;
    private double[][] trainOutput;


    public Network(int numInputNeurons, int numOutputNeurons, int numHiddenLayers, int numHiddenNeurons,
                   String dataFileName, String answerFileName, int BATCH_SIZE, double learningRate, int numEpochs) {
        this.NUM_INPUT_NEURONS = numInputNeurons;
        this.NUM_OUTPUT_NEURONS = numOutputNeurons;
        this.NUM_HIDDEN_LAYERS = numHiddenLayers;
        this.NUM_HIDDEN_NEURONS = numHiddenNeurons;
        this.INPUT_LAYER_INDEX = 0;
        this.OUTPUT_LAYER_INDEX = NUM_HIDDEN_LAYERS + 1;
        this.BATCH_SIZE = BATCH_SIZE;
        this.LEARNING_RATE = learningRate;
        this.dataFileName = dataFileName;
        this.answerFileName = answerFileName;
        this.NUM_EPOCHS = numEpochs;
        input = new Matrix(NUM_INPUT_NEURONS, BATCH_SIZE);
        outputTrue = new Matrix(NUM_OUTPUT_NEURONS, BATCH_SIZE);
        costs = new double[NUM_EPOCHS];


        //filling arrays with arrays of correct order
        layers = new Matrix[NUM_HIDDEN_LAYERS + 2];
        layers[INPUT_LAYER_INDEX] = new Matrix(NUM_INPUT_NEURONS, BATCH_SIZE);
        for (int i = 1; i <= numHiddenLayers; i++) {
            layers[i] = new Matrix(numHiddenNeurons, BATCH_SIZE);
        }
        layers[OUTPUT_LAYER_INDEX] = new Matrix(NUM_OUTPUT_NEURONS, BATCH_SIZE);

        weights = new Matrix[NUM_HIDDEN_LAYERS + 1];
        for (int i = 0; i < OUTPUT_LAYER_INDEX; i++) {
            weights[i] = new Matrix(layers[i+1].getNumRow(),layers[i].getNumRow());
        }

        biases = new Matrix[NUM_HIDDEN_LAYERS + 1];
        for (int i = 0; i < OUTPUT_LAYER_INDEX; i++) {
            biases[i] = new Matrix(layers[i+1].getNumRow(), 1);
        }

        dWeights = new Matrix[NUM_HIDDEN_LAYERS + 1];
        dBiases = new Matrix[NUM_HIDDEN_LAYERS + 1];
        dPreActivation = new Matrix[NUM_HIDDEN_LAYERS + 2];
        dActivation = new Matrix[NUM_HIDDEN_LAYERS + 2];

        //Randomise weights and biases, ready for training
        for (Matrix m : weights) {
            for (int row = 0; row < m.getNumRow(); row++) {
                for (int col = 0; col < m.getNumCol(); col++) {
                    m.setValue(row, col, (Math.random() * 2 - 1) * Math.sqrt(2.0 / (m.getNumCol() + m.getNumRow())));

                }
            }
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

    public double[][] parse(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            int numLines = 0;
            String[] temp = new String[] {""};
            while (scan.hasNextLine()) {
                numLines++;
                temp = scan.nextLine().split(",");
            }
            scan.close();

            Scanner read = new Scanner(new File(filename));
            String[][] inputString = new String[numLines][temp.length];
            for (int i = 0; i < numLines; i++) {
                inputString[i] = read.nextLine().split(",");
            }
            read.close();

            double[][] input = new double[numLines][temp.length];
            for (int i = 0; i < numLines; i++) {
                for (int j = 0; j < temp.length; j++) {
                    input[i][j] = Double.parseDouble(inputString[i][j]);
                }
            }

            return input;
        } catch (Exception e) {
            System.out.println("Could not find file");
            return new double[][] {{0.0}};
        }
    }

    //each column of input is a set of training data; order: NUM_INPUT_NEURONSxBATCH_SIZE
    public void feedForward() {
        layers[INPUT_LAYER_INDEX] = input;
        for (int i = 1; i <= OUTPUT_LAYER_INDEX; i++) {
            layers[i] = weights[i - 1].multiply(layers[i - 1])
                    .add(expandBias(biases[i - 1], layers[i - 1].getNumCol()))
                    .sigmoid();

        }
    }


    //input is a n x 1 matrix, output is n x numCol matrix
    public Matrix expandBias(Matrix input, int numCol) {
        Matrix output = new Matrix(input.getNumRow(), numCol);
        for (int i = 0; i < numCol; i++) {
            output.setCol(i, input.getCol(0));
        }
        return output;
    }

    public void backPropagate() {
        costs[epoch] = cost(layers[OUTPUT_LAYER_INDEX], outputTrue);
        dPreActivation[OUTPUT_LAYER_INDEX] = layers[OUTPUT_LAYER_INDEX].subtract(outputTrue);
        for (int layer = OUTPUT_LAYER_INDEX - 1; layer >= 0; layer--) {
            dActivation[layer] = calcDActivation(layer);
            dWeights[layer] = calcDWeight(layer);
            dBiases[layer] = calcDBias(layer);
            dPreActivation[layer] = calcDPreActivation(layer);
        }
        adjustWeightsBiases();
    }

    public void adjustWeightsBiases() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i].subtract(dWeights[i].multiply(LEARNING_RATE));
            biases[i] = biases[i].subtract(dBiases[i].multiply(LEARNING_RATE));
        }
    }

    public Matrix calcDWeight(int layer) {
        return dPreActivation[layer + 1].multiply(layers[layer].transpose()).multiply(1.0 / BATCH_SIZE);
    }

    public Matrix calcDBias(int layer) {
        Matrix output = new Matrix(layers[layer + 1].getNumRow(), 1);
        for (int i = 0; i < output.getNumRow(); i++) {
            double sum = 0;
            for (int j = 0; j < dPreActivation[layer + 1].getNumCol(); j++) {
                sum += dPreActivation[layer + 1].getValue(i, j);
            }
            output.setValue(i, 0, sum);
        }
        return output.multiply(1.0 / BATCH_SIZE);
    }

    public Matrix calcDPreActivation(int layer) {
        return dActivation[layer].multiplyElement(layers[layer].multiplyElement(
                Matrix.setOne(layers[layer].getNumRow(), layers[layer].getNumCol()).subtract(layers[layer])));
    }

    public Matrix calcDActivation(int layer) {
        return weights[layer].transpose().multiply(dPreActivation[layer + 1]);
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
//        double epsilon = 1e-10;
//        outputPredicted = Math.max(epsilon, Math.min(1 - epsilon, outputPredicted));
        return -(outputTrue * Math.log(outputPredicted) + (1 - outputTrue) * Math.log(1 - outputPredicted));
    }

    public void train() {
        trainInput = parse(dataFileName);
        trainOutput = parse(answerFileName);

        for (int i = 0; i < NUM_EPOCHS; i++) {


            // FIX THIS, USE CORRESPONDING ROWS IN OUTPUT, NOT RANDOM
            for (int j = 0; j < BATCH_SIZE; j++) {
                int random = (int)(Math.random() * trainInput.length);
                for (int k = 0; k < NUM_INPUT_NEURONS; k++) {
                    input.setValue(k, j, trainInput[random][k]);
                }
                for (int k = 0; k < NUM_OUTPUT_NEURONS; k++) {
                    outputTrue.setValue(k, j, trainOutput[random][k]);
                }

            }

            feedForward();
            backPropagate();

            epoch++;
        }
    }

    public Matrix predict(Matrix data) {
        input = data;
        feedForward();
        return layers[OUTPUT_LAYER_INDEX];
    }
}
