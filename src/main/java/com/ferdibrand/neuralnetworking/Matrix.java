import java.util.Arrays;

public class Matrix {
    private int numRow;
    private int numCol;
    private double[][] contents;

    // Constructors
    public Matrix() {
        numRow = 1;
        numCol = 1;
        contents = new double[1][1];
    }

    public Matrix(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;
        contents = new double[numRow][numCol];
    }

    public Matrix(int numRow, int numCol, double[][] contents) {
        this.numRow = numRow;
        this.numCol = numCol;
        this.contents = contents;
    }

    // Getters
    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public double getValue(int row, int col) {
        return contents[row][col];
    }

    public double[] getRow(int row) {
        return contents[row];
    }

    public double[] getCol(int col) {
        double[] returnCol = new double[numRow];
        for (int i = 0; i < numRow; i++) {
            returnCol[i] = this.getValue(i, col);
        }
        return returnCol;
    }

    public double[][] getContents() {
        return contents;
    }

    // Setters
    public void setOrder(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;
        contents = new double[numRow][numCol];
    }

    public void setValue(int row, int col, double value) {
        contents[row][col] = value;
    }

    public void setRow(int row, double[] values) {
        contents[row] = values;
    }

    public void setCol(int col, double[] values) {
        for (int i = 0; i < numRow; i++) {
            contents[i][col] = values[i];
        }
    }

    public void setContents(double[][] contents) {
        this.contents = contents;
    }

    public String toString() {

        String contentsNewLine  = "";
        for (double[] l : contents) {
            contentsNewLine += Arrays.toString(l);
            contentsNewLine += "\n";
        }
        return "Order: (" + numRow + ", " + numCol + ")\nContents:\n" + contentsNewLine;
    }

    // Operations
    public Matrix add(Matrix b) {
        if (numRow == b.numRow && numCol == b.numCol) {
            Matrix sum = new Matrix(numRow, numCol);
            for (int row = 0; row < sum.numRow; row++) {
                for (int col = 0; col < sum.numCol; col++) {
                    sum.contents[row][col] = this.getValue(row, col) + b.getValue(row, col);
                }
            }
            return sum;
        } else {
            System.out.println("Cannot add matrices, not same order. Return Matrix a");
            return this;
        }
    }

    public Matrix subtract (Matrix b) {
        b = b.multiply(-1);
        Matrix difference = this.add(b);
        return difference;
    }

    public Matrix multiply(double scalar) {
        Matrix product = new Matrix(numRow, numCol);
        for (int row = 0; row < product.numRow; row++) {
            for (int col = 0; col < product.numCol; col++) {
                product.setValue(row, col, getValue(row, col) * scalar);
            }
        }
        return product;
    }

    public Matrix multiply(Matrix b) {
        if (numCol == b.numRow) {
            Matrix product = new Matrix(numRow, b.numCol);
            for (int row = 0; row < product.numRow; row++) {
                for (int col = 0; col < product.numCol; col++) {
                    double value = 0;
                    for (int i = 0; i < numCol; i++) {
                        value += getValue(row, i) * b.getValue(i, col);
                    }
                    product.setValue(row, col, value);
                }
            }
            return product;
        } else {
            System.out.println("Cannot multiply matrices, not compatible orders. Return Matrix a");
            return this;
        }
    }

    public Matrix transpose() {
        Matrix transposed = new Matrix(numCol, numRow);
        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {
                transposed.setValue(col, row, getValue(row, col));
            }
        }
        return transposed;
    }
}
