package Core.Gesture.Matrix.Normalization;

import Core.MatrixGesture.Matrix;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;

public class MatrixNormalizer {
    private SimpleMatrix normalizer;
    private SimpleMatrix translator;
    private SimpleMatrix rotator;
    public SimpleMatrix rotator1;
    public SimpleMatrix rotator2;
    public SimpleMatrix rotator3;

    public SimpleMatrix getNormalizer() {
        return normalizer.copy();
    }

    public SimpleMatrix getTranslator() {
        return translator;
    }

    public SimpleMatrix getRotator() {
        return rotator;
    }

    //mult
    //plus
    //div ?

    private void setNormalizer(SimpleMatrix normalizer) throws BadAttributeValueExpException {
        if(this.normalizer != null) {
            if(normalizer == null) throw new BadAttributeValueExpException("New normalizer have to be not null");
            if(normalizer.numCols() != this.normalizer.numCols() || normalizer.numRows() != this.normalizer.numRows()) {
                throw new BadAttributeValueExpException("Size of new normalizer have to be equal to the last");
            }
        } else {
            if(normalizer == null) {
                throw new BadAttributeValueExpException("New normalizer have to be not null");
            }
        }
        this.normalizer = normalizer;
    }

    public MatrixNormalizer(SimpleMatrix normalizer) throws BadAttributeValueExpException {
        setNormalizer(normalizer);
    }

    /*
    public MatrixNormalizer(SimpleMatrix translation, SimpleMatrix rotation) {
        if(translation.numCols() != rotation.numCols()) return;
        if(translation.numRows() != rotation.numRows()) return;

        setNormalizer(translation.plus(rotation));
    }
    */

    public MatrixNormalizer(SimpleMatrix translation, SimpleMatrix... rotations) throws Exception {
        SimpleMatrix normalizer = getNewNormalisationMatrix(translation, rotations);
//        if(normalizer == null) setNormalizer(SimpleMatrix.identity(0));
        try {
            setNormalizer(normalizer);
        } catch (BadAttributeValueExpException e) {
            e.printStackTrace();
            throw new Exception("An exception during setting normalizer has occured");
        }
    }

    public MatrixNormalizer(Hand hand) throws BadAttributeValueExpException {
        if(hand == null || !hand.isValid()) {
            throw new BadAttributeValueExpException("The hand have to be not null and valid");
        }

        SimpleMatrix translator = getTranslationMatrixFromHand(hand);

        SimpleMatrix vectorDirection = new SimpleMatrix(4, 1);
        vectorDirection.set(0,0, hand.direction().getX());
        vectorDirection.set(1,0, hand.direction().getY());
        vectorDirection.set(2,0, hand.direction().getZ());
        vectorDirection.set(3,0, 1);

        SimpleMatrix vectorPalmNormal = new SimpleMatrix(4, 1);
        vectorPalmNormal.set(0,0, hand.palmNormal().getX());
        vectorPalmNormal.set(1,0, hand.palmNormal().getY());
        vectorPalmNormal.set(2,0, hand.palmNormal().getZ());
        vectorPalmNormal.set(3,0, 1);

        Vector handDirectionLeap = new Vector((float) vectorDirection.get(0,0), (float) vectorDirection.get(1,0), (float) vectorDirection.get(2,0));
        Vector handPalmNormalLeap = new Vector((float) vectorPalmNormal.get(0,0), (float) vectorPalmNormal.get(1,0), (float) vectorPalmNormal.get(2,0));

        boolean isHandupSideDown = Math.abs(handPalmNormalLeap.roll()) > Math.PI/2;

        SimpleMatrix firstRotation = rotationMatrixByAnyAxes2d(hand.direction().yaw() * (isHandupSideDown ? 0 : -1), vectorPalmNormal);

        vectorDirection = firstRotation.mult(vectorDirection);
        vectorPalmNormal = firstRotation.mult(vectorPalmNormal);
        handDirectionLeap = new Vector((float) vectorDirection.get(0,0), (float) vectorDirection.get(1,0), (float) vectorDirection.get(2,0));
        handPalmNormalLeap = new Vector((float) vectorPalmNormal.get(0,0), (float) vectorPalmNormal.get(1,0), (float) vectorPalmNormal.get(2,0));

        SimpleMatrix secondRotation = xAxisRotationMatrix2d(handDirectionLeap.pitch() * -1);

        vectorDirection = secondRotation.mult(vectorDirection);
        vectorPalmNormal = secondRotation.mult(vectorPalmNormal);
        handDirectionLeap = new Vector((float) vectorDirection.get(0,0), (float) vectorDirection.get(1,0), (float) vectorDirection.get(2,0));
        handPalmNormalLeap = new Vector((float) vectorPalmNormal.get(0,0), (float) vectorPalmNormal.get(1,0), (float) vectorPalmNormal.get(2,0));

        SimpleMatrix thirdRotation = zAxisRotationMatrix2d(handPalmNormalLeap.roll() * -1);

//        setNormalizer(getNewNormalisationMatrix(translator, firstRotation, secondRotation, thirdRotation));
//        setNormalizer(getNewNormalisationMatrix(translator, firstRotation));

        this.translator = translator;
        this.rotator1 = firstRotation;
        this.rotator2 = secondRotation;
        this.rotator3 = thirdRotation;
        this.rotator = firstRotation.mult(secondRotation);
        this.rotator = getRotator().mult(thirdRotation);
        setNormalizer(getNewNormalisationMatrix(firstRotation));
    }

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

        SimpleMatrix normalizer = translation.plus(rotation);
        normalizer.set(normalizer.numRows()-1, normalizer.numCols()-1, 1);
        return normalizer;
    }

    public SimpleMatrix getNewTranslationMatrix(float... translations) {
        int columns = translations.length;
        SimpleMatrix translator = SimpleMatrix.identity(columns + 1);
        for(int i = 0; i < columns; i++) {
            translator.set(i, columns, translations[i]);
        }

        return translator;
    }

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
    public SimpleMatrix rotationMatrixByAnyAxes2d(float teta, SimpleMatrix axe) {
        if(axe == null || axe.numRows() != 3 && axe.numRows() != 4) return null;

        SimpleMatrix rotation = SimpleMatrix.identity(4);

        float a = (float) axe.get(0,0), b = (float) axe.get(1,0), c = (float) axe.get(2,0);
        float cos = (float) Math.cos(teta), sin = (float) Math.sin(teta);

        rotation.set(0,0, a*a + (1 - a*a) * cos); rotation.set(0,1, a*b * (1 - cos) - c*sin); rotation.set(0,2, a*c * (1 - cos) + b*sin);
        rotation.set(1,0, a*b * (1 - cos) + c*sin); rotation.set(1,1, b*b + (1 - b*b) * cos); rotation.set(1,2, b*c * (1 - cos) - a*sin);
        rotation.set(2,0, a*c * (1 - cos) - b*sin); rotation.set(2,1, b*c * (1 - cos) + a*sin); rotation.set(2,2, c*c + (1 - c*c) * cos);

        return rotation;
    }

    public SimpleMatrix xAxisRotationMatrix2d(float v) {
        float cos = (float) Math.cos(v);
        float sin = (float) Math.sin(v);
        SimpleMatrix rotation = SimpleMatrix.identity(4);

        rotation.set(1,1, cos); rotation.set(1,2, sin * -1);
        rotation.set(2,1, sin); rotation.set(2,2, cos);

        return rotation;
    }

    public SimpleMatrix yAxisRotationMatrix2d(float v) {
        float cos = (float) Math.cos(v);
        float sin = (float) Math.sin(v);
        SimpleMatrix rotation = SimpleMatrix.identity(4);

        rotation.set(0,0, cos); rotation.set(0,2, sin);
        rotation.set(2,0, sin * -1); rotation.set(2,2, cos);

        return rotation;
    }

    public SimpleMatrix zAxisRotationMatrix2d(float v) {
        float cos = (float) Math.cos(v);
        float sin = (float) Math.sin(v);
        SimpleMatrix rotation = SimpleMatrix.identity(4);

        rotation.set(0,0, cos); rotation.set(0,1, sin * -1);
        rotation.set(1,0, sin); rotation.set(1,1, cos);

        return rotation;
    }

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
