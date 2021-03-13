package Utils.Converter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Convert data to byte
 */
public class ByteConverter {

    /**
     * Convert int to byte
     * @param array int array
     * @return byte version of the given array
     */
    public static byte [] convert(int [] array) {
        if (array.length == 0) {
            return new byte[0];
        }
        return writeInts(array);
    }

    /**
     * Transform the data to his byte representation
     * @param array data
     * @return transformed data
     */
    private static byte [] writeInts(int [] array) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(array.length * 4);
            DataOutputStream dos = new DataOutputStream(bos);
            for (int i = 0; i < array.length; i++) {
                dos.writeInt(array[i]);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
