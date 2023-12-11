import java.io.*;
import java.util.ArrayList;


public class Serialization<T> {


    public void saveToFile(T objToSave, String filename) {
        save(objToSave, filename);
    }

    public void saveListToFile(ArrayList<T> objToSave, String filename) {
        save(objToSave, filename);
    }

    private void save(Object objToSave, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(objToSave);
            System.out.println("Object saved to <" + filename + ">");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T loadFromFile(String filename, Class<?> objClass) {
        return load(filename, objClass);
    }

    public ArrayList<T> loadListFromFile(String filename, Class<?> objClass) {
        T inputObject = load(filename, ArrayList.class);
        return (ArrayList<T>) inputObject;
    }

    private T load(String filename, Class<?> objClass) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Object inputObject = inputStream.readObject();
            if (objClass.isInstance(inputObject)) {
                System.out.println("Object at <" + filename + "> loaded");
                return (T) inputObject;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}