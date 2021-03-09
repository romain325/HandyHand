package Core.Gesture.Matrix;

import org.ejml.simple.SimpleMatrix;

/**
 * A class to manage some matrix
 */
public class MatrixUtils {
    /**
     * A method to get a SimpleMatrix from a simple array
     * @param val The simple Array from which we want the SimpleMatrix
     * @return The SimpleMatrix that have been create
     */
    public static SimpleMatrix toSimpleMatrix(double[] val){
        SimpleMatrix matrix = new SimpleMatrix(4,1);
        for (int i = 0; i < val.length; i++){
            matrix.set(i,0, val[i]);
        }
        return matrix;
    }

    /**
     * A method to get a simple array from a SimpleMatrix
     * @param val The SimpleMatrix from which we want the simple Array
     * @return The simple Array that have been create
     */
    public static double[] fromSimpleMatrix(SimpleMatrix val){
        double[] returnVal = new double[4];
        for (int i = 0; i < 4; i++){
            returnVal[i] = val.get(i,0);
        }
        return returnVal;
    }

    /**
     * A method to get a SimpleMatrix from a 2D array
     * @param val The 2D Array from which we want the SimpleMatrix
     * @return The SimpleMatrix that have been create
     */
    public static SimpleMatrix toSimpleMatrix(double[][] val){
        SimpleMatrix matrix = new SimpleMatrix(4,4);
        for (int i = 0; i < val.length; i++){
            for (int j = 0; j < val[i].length; j++){
                matrix.set(i,j, val[i][j]);
            }
        }
        return matrix;
    }

    /**
     * A method to get a 2D array from a SimpleMatrix
     * @param val The SimpleMatrix from which we want the 2D Array
     * @return The 2D Array that have been create
     */
    public static double[][] squareFromSimpleMatrix(SimpleMatrix val){
        double[][] returnVal = new double[4][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < returnVal[i].length; j++){
                returnVal[i][j] = val.get(i,j);
            }
        }
        return returnVal;
    }
}
