import java.util.*;
import java.io.*;

public class Matrix {
    private int numRow;
    private int numColumn;
    private ArrayList<ArrayList<Double>> contents = new ArrayList<>();

    // Constructors
    public Matrix() {
        numRow = 1;
        numColumn = 1;
        contents.add(new ArrayList<Double>(Arrays.asList(0.0)));
    }

    public Matrix(int numRow, int numColumn) {
        this.numRow = numRow;
        this.numColumn = numColumn;
        contents.clear();
        for (int row = 0; row < numRow; row++) {
            ArrayList<Double> newRow = new ArrayList<>();
            for (int col = 0; col < numColumn; col++) {
                newRow.add(0.0);
            }
            contents.add(newRow);
        }
    }

    public Matrix(int numRow, int numColumn, ArrayList<ArrayList<Double>> contents) {
        this.numRow = numRow;
        this.numColumn = numColumn;
        this.contents = contents;
    }

    // Getters
    public int getNumRow() {
        return numRow;
    }

    public int getNumColumn() {
        return numColumn;
    }

    public double getValue(int row, int col) {
        return contents.get(row).get(col);
    }

    public ArrayList<Double> getRow(int row) {
        return contents.get(row);
    }

    public ArrayList<Double> getCol(int col) {
        ArrayList<Double> tempArray = new ArrayList<>();
        for (ArrayList<Double> l : contents) {
            tempArray.add(l.get(col));
        }
        return tempArray;
    }

    public ArrayList<ArrayList<Double>> getContents() {
        return contents;
    }

    // Setters
    public void setOrder(int numRow, int numColumn) {
        this.numRow = numRow;
        this.numColumn = numColumn;
        contents.clear();
        for (int row = 0; row < numRow; row++) {
            ArrayList<Double> newRow = new ArrayList<>();
            for (int col = 0; col < numColumn; col++) {
                newRow.add(0.0);
            }
            contents.add(newRow);
        }
    }

    public void setValue(int row, int col, double value) {
        contents.get(row).set(col, value);
    }

    public void setRow(int row, ArrayList<Double> values) {
        contents.set(row, values);
    }

    public void setColumn(int col, ArrayList<Double> values) {
        for (int i = 0; i < numRow; i++) {
            contents.get(i).set(col, values.get(i));
        }
    }

    public void setContents(ArrayList<ArrayList<Double>> contents) {
        this.contents = contents;
    }

    public String toString() {

        String contentsNewLine  = "";
        for (ArrayList<Double> l : this.contents) {
            contentsNewLine += l.toString();
            contentsNewLine += "\n";
        }
        return "Order: (" + numRow + ", " + numColumn + ")\nContents:\n" + contentsNewLine;
    }

    // Operations
    public static Matrix add(Matrix a, Matrix b) {
        if (a.numRow == b.numRow && a.numColumn == b.numColumn) {
            Matrix sum = new Matrix(a.numRow, a.numColumn);
            for (int row = 0; row < sum.numRow; row++) {
                for (int col = 0; col < sum.numColumn; col++) {
                    sum.setValue(row, col, a.getValue(row, col) + b.getValue(row, col));
                }
            }
            return sum;
        } else {
            System.out.println("Cannot add matrices, not same order. Return Matrix a");
            return a;
        }
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        if (a.numRow == b.numRow && a.numColumn == b.numColumn) {
            Matrix difference = new Matrix(a.numRow, a.numColumn);
            for (int row = 0; row < difference.numRow; row++) {
                for (int col = 0; col < difference.numColumn; col++) {
                    difference.setValue(row, col, a.getValue(row, col) + b.getValue(row, col));
                }
            }
            return difference;
        } else {
            System.out.println("Cannot subtract matrices, not same order. Return Matrix a");
            return a;
        }
    }

    public static Matrix sMultiply(Matrix a, double scalar) {
        Matrix product = new Matrix(a.numRow, a.numColumn);
        for (int row = 0; row < product.numRow; row++) {
            for (int col = 0; col < product.numColumn; col++) {
                product.setValue(row, col, a.getValue(row, col) * scalar);
            }
        }
        return product;
    }

    public static Matrix mMultiply(Matrix a, Matrix b) {
        if (a.numColumn == b.numRow) {
            Matrix product = new Matrix(a.numRow, b.numColumn);
            for (int row = 0; row < product.numRow; row++) {
                for (int col = 0; col < product.numColumn; col++) {
                    double value = 0;
                    for (int i = 0; i < a.numColumn; i++) {
                        value += a.getValue(row, i) * b.getValue(i, col);
                    }
                    product.setValue(row, col, value);
                }
            }
            return product;
        } else {
            System.out.println("Cannot multiply matrices, not compatible orders. Return Matrix a");
            return a;
        }
    }

    public static Matrix transpose(Matrix a) {
        Matrix transposed = new Matrix(a.numColumn, a.numRow);
        for (int row = 0; row < a.numRow; row++) {
            for (int col = 0; col < a.numColumn; col++) {
                transposed.setValue(col, row, a.getValue(row, col));
            }
        }
        return transposed;
    }
}
