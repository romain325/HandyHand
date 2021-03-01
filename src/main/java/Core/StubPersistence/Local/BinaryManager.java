package Core.StubPersistence.Local;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public interface BinaryManager<T extends Object> extends FileManager{
    LinkedList<T> getAll();
    default void checkExistence() throws IOException {
        if(!exists()) createFile();
    }
    default void save(LinkedList<T> object){
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(getPath().toString()));
            objectOut.writeObject(object);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}