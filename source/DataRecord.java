import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * The {@code DataRecord} class represents a single game entry in a dataset.
 * Each data record has a title and associated values for different features.
 */
public class DataRecord extends LinkedHashMap<String, Object> implements Serializable {

    /**
     * Constructs a {@code DataRecord} object by parsing the provided entry and mapping it to features.
     *
     * @param features  an array of feature names
     * @param entry     the entry string containing the data record information
     * @param delimiter the delimiter used to separate values in the entry string
     */
    public DataRecord(String[] features, String entry, String delimiter) {
        // Split the entire entry using the delimiter
        String[] values = entry.split(delimiter);

        // Populate the LinkedHashMap using features and corresponding values
        for (int j = 0; j < features.length && j < values.length; j++) {
            String newFeature = features[j].trim();

            Object newValue;
            if (j == 0 || (j == values.length - 1 && j == features.length - 1)) {
                // For the first and last features, directly use the value from the entry string
                newValue = values[j].trim();
            } else {
                // For other features, interpret "1" or "true" as boolean true, and other values as boolean false
                newValue = values[j].equals("1") || values[j].equalsIgnoreCase("true");
            }

            put(newFeature, newValue);
        }
    }

    /**
     * Get the key at the specified index in the LinkedHashMap.
     *
     * @param k the index of the key
     * @return the key at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public String keyAt(int k) {
        if (k < 0 || k >= size()) {
            throw new IndexOutOfBoundsException("Index " + k + " is out of bounds for the LinkedHashMap");
        }

        // Get the key at the specified index
        Iterator<String> iterator = keySet().iterator();
        for (int i = 0; i < k; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Get the value at the specified index in the LinkedHashMap.
     *
     * @param k the index of the value
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public String valueAt(int k) {
        if (k < 0 || k >= size()) {
            throw new IndexOutOfBoundsException("Index " + k + " is out of bounds for the LinkedHashMap");
        }

        // Get the value at the specified index
        Iterator<Object> iterator = values().iterator();
        for (int i = 0; i < k; i++) {
            iterator.next();
        }

        Object value = iterator.next();
        return value != null ? value.toString() : null;
    }
}