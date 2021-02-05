package Core.Gesture.Matrix.Normalization;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;

/**
 * A class to get a normalizer matrix. All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
 */
public class MatrixNormalizer {
    /**
     * The matrix normalizer
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     */
    private SimpleMatrix normalizer;

    /**
     * A method to get the matrix normalizer
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @return The matrix normalizer that is contained in the class
     */
    public SimpleMatrix getNormalizer() {
        return normalizer.copy();
    }

    /**
     * A method to set the matrix normalizer in the class.
     * The new matrix have to be of the same size of the old one.
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param normalizer The new matrix normalizer
     * @throws BadAttributeValueExpException An exception is thrown if the normalizer is null, or not of the same size of the oldest
     */
    private void setNormalizer(SimpleMatrix normalizer) throws BadAttributeValueExpException {
        if(this.normalizer != null) //If they are already one normalizer
        {
            if(normalizer == null) throw new BadAttributeValueExpException("New normalizer have to be not null");
            if(normalizer.numCols() != this.normalizer.numCols() || normalizer.numRows() != this.normalizer.numRows()) {
                throw new BadAttributeValueExpException("Size of new normalizer have to be equal to the last");
            }
        }
        else //If normalizer wasn't set before
        {
            if(normalizer == null) {
                throw new BadAttributeValueExpException("New normalizer have to be not null");
            }
        }
        this.normalizer = normalizer;
    }

    /**
     * A constructor of the class MatrixNormalizer
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param normalizer The matrix normalizer that we want in
     * @throws BadAttributeValueExpException An exception is thrown if the normalizer is null
     */
    public MatrixNormalizer(SimpleMatrix normalizer) throws BadAttributeValueExpException {
        setNormalizer(normalizer);
    }

    /**
     * A constructor of the class MatrixNormalizer
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param translation The matrix translation that well be in the matrix normalizer
     * @param rotations Some matrix translations that well be in the matrix normalizer
     * @throws BadAttributeValueExpException An exception is thrown if the normalizer couldn't be initialized
     */
    public MatrixNormalizer(SimpleMatrix translation, SimpleMatrix... rotations) throws BadAttributeValueExpException {
        SimpleMatrix normalizer = getNewNormalisationMatrix(translation, rotations);
        setNormalizer(normalizer);
    }

    /**
     * A constructor of the class MatrixNormalizer for normalize vectors of a hand
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param hand The hand on which we want to normalizer based
     * @throws BadAttributeValueExpException An exception is thrown if the hand is null or not valid
     */
    public MatrixNormalizer(Hand hand) throws BadAttributeValueExpException {
        setNormalizer(getNoramlisationMatrixFromHand(hand, 75));
    }

    /**
     * A method to get a normalization matrix from a hand
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param hand The hand on which we want to normalizer based
     * @return The new matrix normalizer
     * @throws BadAttributeValueExpException An exception is thrown if the hand is null or not valid
     */
    public SimpleMatrix getNoramlisationMatrixFromHand(Hand hand) throws BadAttributeValueExpException {
        float metacarpalMiddleFingerLength = 75;
        for(Finger finger: hand.fingers()) {
            if(finger.type() == Finger.Type.TYPE_MIDDLE) {
                metacarpalMiddleFingerLength = finger.bone(Bone.Type.TYPE_METACARPAL).length();
            }
        }
        return getNoramlisationMatrixFromHand(hand, metacarpalMiddleFingerLength);
    }

    /**
     * A method to get a normalization matrix from a hand
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param hand The hand on which we want to normalizer based
     * @param metacarpalMiddleFingerLength The length of the metacarpal bone which we want to have after scaling
     * @return The new matrix normalizer
     * @throws BadAttributeValueExpException An exception is thrown if the hand is null or not valid
     */
    public SimpleMatrix getNoramlisationMatrixFromHand(Hand hand, float metacarpalMiddleFingerLength) throws BadAttributeValueExpException {
        if(hand == null || !hand.isValid()) {
            throw new BadAttributeValueExpException("The hand have to be not null and valid");
        }

        // The translation
        SimpleMatrix translator = getTranslationMatrixFromHand(hand);

        // The scaling
        float scale = 1;
        for(Finger finger: hand.fingers()) {
            if(finger.type() == Finger.Type.TYPE_MIDDLE) {
                scale = metacarpalMiddleFingerLength / finger.bone(Bone.Type.TYPE_METACARPAL).length();
            }
        }

        SimpleMatrix scalingMatrix = scalingMatrix(scale, scale, scale);
        translator = scalingMatrix.mult(translator);

        // The rotation
        //The vector of the direction of the hand
        SimpleMatrix vectorDirection = new SimpleMatrix(4, 1);
        vectorDirection.set(0,0, hand.direction().getX());
        vectorDirection.set(1,0, hand.direction().getY());
        vectorDirection.set(2,0, hand.direction().getZ());
        vectorDirection.set(3,0, 1);

        //The vector normalized of the palm of the hand
        SimpleMatrix vectorPalmNormal = new SimpleMatrix(4, 1);
        vectorPalmNormal.set(0,0, hand.palmNormal().getX());
        vectorPalmNormal.set(1,0, hand.palmNormal().getY());
        vectorPalmNormal.set(2,0, hand.palmNormal().getZ());
        vectorPalmNormal.set(3,0, 1);

        //Vectors of the LeapMotion, to get access to methods yaw, pitch and roll
        Vector handDirectionLeap = new Vector((float) vectorDirection.get(0,0), (float) vectorDirection.get(1,0), (float) vectorDirection.get(2,0));
        Vector handPalmNormalLeap = new Vector((float) vectorPalmNormal.get(0,0), (float) vectorPalmNormal.get(1,0), (float) vectorPalmNormal.get(2,0));

        //When the hand is up side down, the rotation have to be inverse
        boolean isHandupSideDown = Math.abs(handPalmNormalLeap.roll()) > Math.PI/2;

        //Get the rotation of the hand around the vector of the palm, to re-axe the hand
        SimpleMatrix firstRotation = rotationMatrixByAnyAxis2d(handDirectionLeap.yaw() * (isHandupSideDown ? 0 : -1), vectorPalmNormal);

        //We change different vectors with the new rotation
        vectorDirection = firstRotation.mult(vectorDirection);
        vectorPalmNormal = firstRotation.mult(vectorPalmNormal);
        handDirectionLeap = new Vector((float) vectorDirection.get(0,0), (float) vectorDirection.get(1,0), (float) vectorDirection.get(2,0));
        handPalmNormalLeap = new Vector((float) vectorPalmNormal.get(0,0), (float) vectorPalmNormal.get(1,0), (float) vectorPalmNormal.get(2,0));

        //We get the second rotation
        SimpleMatrix secondRotation = xAxisRotationMatrix3d(handDirectionLeap.pitch() * -1);

        //We change different vectors with the new rotation
        vectorDirection = secondRotation.mult(vectorDirection);
        vectorPalmNormal = secondRotation.mult(vectorPalmNormal);
        handDirectionLeap = new Vector((float) vectorDirection.get(0,0), (float) vectorDirection.get(1,0), (float) vectorDirection.get(2,0));
        handPalmNormalLeap = new Vector((float) vectorPalmNormal.get(0,0), (float) vectorPalmNormal.get(1,0), (float) vectorPalmNormal.get(2,0));

        //We get the third rotation
        SimpleMatrix thirdRotation = zAxisRotationMatrix3d(handPalmNormalLeap.roll() * -1);

        // Here, we inverse the order of the rotations because we want the first one to be multiple firstly to the vectors
        //Indeed, order is really important in matrix
        return getNewNormalisationMatrix(translator, thirdRotation, secondRotation, firstRotation);
    }



        /**
         * A method to get a normalisation matrix from a translation and some rotations matrix
         * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
         * @param translation The translation we want in the normalisation
         * @param rotations Some rotations we want in the normalisation
         * @return The matrix normalizer produce
         * @throws BadAttributeValueExpException If the matrix normalizer couldn't be produce because of bad values
         */
    public SimpleMatrix getNewNormalisationMatrix(SimpleMatrix translation, SimpleMatrix... rotations) throws BadAttributeValueExpException {
        if(rotations.length < 1) return translation;
        SimpleMatrix rotation = SimpleMatrix.identity(rotations[0].numRows());

        for (SimpleMatrix rot: rotations) {
            if(translation.numCols() != rot.numRows())
            {
                throw new BadAttributeValueExpException("Matrix of rotations must be able to multiplicate each other");
            }
            rotation = rotation.mult(rot);
        }

        if(translation.numCols() != rotation.numCols() || translation.numRows() != rotation.numRows())
        {
            throw new BadAttributeValueExpException("Multiplication of rotations must have the same size of translation matrix");
        }

        SimpleMatrix normalizer = rotation.mult(translation);
        normalizer.set(normalizer.numRows()-1, normalizer.numCols()-1, 1);
        return normalizer;
    }

    /**
     * A method to get a translation matrix
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param translations Some float in the translations
     * @return The new matrix translations
     */
    public SimpleMatrix getNewTranslationMatrix(float... translations) {
        int columns = translations.length;
        SimpleMatrix translator = SimpleMatrix.identity(columns + 1);
        for(int i = 0; i < columns; i++) {
            translator.set(i, columns, translations[i]);
        }

        return translator;
    }

    /**
     * A method to get the translation matrix for vectors of the hand
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param hand The hand that we want the matric from
     * @return The translation matrix for vectors of the hand
     * @throws BadAttributeValueExpException If thand is null or not valid
     */
    public SimpleMatrix getTranslationMatrixFromHand(Hand hand) throws BadAttributeValueExpException {
        if(hand == null) {
            throw new BadAttributeValueExpException("The hand have to be not null and valid");
        }

        float palmX = hand.palmPosition().getX() * -1;
        float palmZ = hand.palmPosition().getZ() * -1;
        float palmY = hand.palmPosition().getY() * -1;

        return getNewTranslationMatrix(palmX, palmY, palmZ);
    }

//    /**
//     * Matrice de rotation, d'angle teta et d'axe passant par O (origine du repère) porté par le vecteur (a,b,c) de norme 0
//     * dans un repère orthonormal direct
//     * @param teta angle de rotation
//     * @param axe axe passant par O
//     * @return La matrice de rotation
//     */
    /**
     * Rotation matrix with teta angle and form any axis passing by the origin.
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * The vector have to be normalized by standard 0 in a direct orthonormal coordinate system
     * @param teta The angle in radian that we want to rotate
     * @param axe The vector normalized by which we want to rotate
     * @return The new matrix rotation
     */
    public SimpleMatrix rotationMatrixByAnyAxis2d(float teta, SimpleMatrix axe) {
        if(axe == null || axe.numRows() != 3 && axe.numRows() != 4) return null;

        SimpleMatrix rotation = SimpleMatrix.identity(4);

        float a = (float) axe.get(0,0), b = (float) axe.get(1,0), c = (float) axe.get(2,0);
        float cos = (float) Math.cos(teta), sin = (float) Math.sin(teta);

        rotation.set(0,0, a*a + (1 - a*a) * cos); rotation.set(0,1, a*b * (1 - cos) - c*sin); rotation.set(0,2, a*c * (1 - cos) + b*sin);
        rotation.set(1,0, a*b * (1 - cos) + c*sin); rotation.set(1,1, b*b + (1 - b*b) * cos); rotation.set(1,2, b*c * (1 - cos) - a*sin);
        rotation.set(2,0, a*c * (1 - cos) - b*sin); rotation.set(2,1, b*c * (1 - cos) + a*sin); rotation.set(2,2, c*c + (1 - c*c) * cos);

        return rotation;
    }

    /**
     * A method to get a rotation around the x axis, for 3d
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param v The angle in radian that we want to rotate
     * @return The new rotation matrix
     */
    public SimpleMatrix xAxisRotationMatrix3d(float v) {
        float cos = (float) Math.cos(v);
        float sin = (float) Math.sin(v);
        SimpleMatrix rotation = SimpleMatrix.identity(4);

        rotation.set(1,1, cos); rotation.set(1,2, sin * -1);
        rotation.set(2,1, sin); rotation.set(2,2, cos);

        return rotation;
    }

    /**
     * A method to get a rotation around the y axis, for 3d
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param v The angle in radian that we want to rotate
     * @return The new rotation matrix
     */
    public SimpleMatrix yAxisRotationMatrix3d(float v) {
        float cos = (float) Math.cos(v);
        float sin = (float) Math.sin(v);
        SimpleMatrix rotation = SimpleMatrix.identity(4);

        rotation.set(0,0, cos); rotation.set(0,2, sin);
        rotation.set(2,0, sin * -1); rotation.set(2,2, cos);

        return rotation;
    }

    /**
     * A method to get a rotation around the z axis, for 3d
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param v The angle in radian that we want to rotate
     * @return The new rotation matrix
     */
    public SimpleMatrix zAxisRotationMatrix3d(float v) {
        float cos = (float) Math.cos(v);
        float sin = (float) Math.sin(v);
        SimpleMatrix rotation = SimpleMatrix.identity(4);

        rotation.set(0,0, cos); rotation.set(0,1, sin * -1);
        rotation.set(1,0, sin); rotation.set(1,1, cos);

        return rotation;
    }

    /**
     * A method to get a scaling matrix
     * All coordinates are homogeneous, so each matrix have a row and column add, and vectors have a row had
     * @param scales The scales that we want in the matrix
     * @return The new matrix scaling
     */
    public SimpleMatrix scalingMatrix(float... scales) {
        if(scales.length < 1) return SimpleMatrix.identity(1);
        SimpleMatrix scaling = SimpleMatrix.identity(scales.length + 1);

        for(int i = 0; i < scales.length; i++) {
            scaling.set(i,i,scales[i]);
        }

        return scaling;
    }

    /**
     * A method to display a matrix
     * @param m The matrix that we want to display
     */
    public static void displayMatrix(SimpleMatrix m) {
        System.out.println("------------------------");
        for(int i = 0; i < m.numRows(); i++) {
            System.out.print(" | ");
            for(int j = 0; j < m.numCols(); j++) {
                System.out.print(m.get(i,j) + " | ");
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }

}
