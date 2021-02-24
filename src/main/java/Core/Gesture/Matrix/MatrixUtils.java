package Core.Gesture.Matrix;

import org.ejml.simple.SimpleMatrix;

public class MatrixUtils {

    public static SimpleMatrix toSimpleMatrix(double[] val){
        SimpleMatrix matrix = new SimpleMatrix(4,1);
        for (int i = 0; i < val.length; i++){
            matrix.set(i,0, val[i]);
        }
        return matrix;
    }

    public static double[] fromSimpleMatrix(SimpleMatrix val){
        double[] returnVal = new double[4];
        for (int i = 0; i < 4; i++){
            returnVal[i] = val.get(i,0);
        }
        return returnVal;
    }

    public static SimpleMatrix toSimpleMatrix(double[][] val){
        SimpleMatrix matrix = new SimpleMatrix(4,4);
        for (int i = 0; i < val.length; i++){
            for (int j = 0; j < val[i].length; j++){
                matrix.set(i,j, val[i][j]);
            }
        }
        return matrix;
    }

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
