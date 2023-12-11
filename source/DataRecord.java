import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * The {@code DataRecord} class represents a single game entry in a dataset.
 * Each data record has a title, rating, and associated values for different features.
 */
public class DataRecord extends LinkedHashMap<String, Boolean> implements Serializable {

    /**
     * universal version identifier for a Serializable class.
     * Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object.
     * If no match is found, then an InvalidClassException is thrown.
     */
    @Serial
    private static final long serialVersionUID = 712275634602611871L;
    private String title;
    private String rating;

    /**
     * Default constructor for {@code DataRecord}.
     * Sets the fields to default values.
     */
    public DataRecord() {
        this.title = "";
        this.rating = "";
    }

    /**
     * Constructs a {@code DataRecord} object by parsing the provided entry and mapping it to headers.
     *
     * @param headers     an array of all column headers from the csv file
     * @param entry       the entry string containing the data record information
     */
    public DataRecord(String[] headers, String[] entry) {
        String delimiter = Main.settings().delimiter();
        int nameIndex = Main.settings().nameIndex();
        int ratingIndex = Main.settings().ratingIndex();

        // Split the entire entry using the delimiter


        // Populate the LinkedHashMap using headers and corresponding values
        for (int j = 0; j < headers.length; j++) {
            String newFeature = headers[j].trim();

            if (j == nameIndex) {
                // Store the record's title separately
                title = entry[j].trim();
            } else if (j == ratingIndex) {
                // Store the record's rating separately
                rating = entry[j].trim();
            } else {
                // For other headers, interpret "1" or "true" as boolean true, and store them as a LinkedHashMap
                Boolean newValue = entry[j].trim().equals("1") || entry[j].equalsIgnoreCase("true");
                put(newFeature, newValue);
            }
        }
    }

    /**
     * Get the title of the data record.
     *
     * @return the title of the data record
     */
    public String title() {
        return title;
    }

    /**
     * Get the rating of the data record.
     *
     * @return the ESRB rating of the data record
     */
    public String rating() {
        return rating;
    }

    /**
     * Get the key at the specified index in the LinkedHashMap.
     *
     * @param k the index of the key
     * @return the key at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public String keyAt(int k) {
        // Check that the specified index is not out of bounds
        if (k < 0 || k >= size()) {
            throw new IndexOutOfBoundsException("Index " + k + " is out of bounds for the LinkedHashMap");
        }

        // Get the key at that index
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
    public Boolean valueAt(int k) {
        // Check that the specified index is not out of bounds
        if (k < 0 || k >= size()) {
            throw new IndexOutOfBoundsException("Index " + k + " is out of bounds for the LinkedHashMap");
        }

        // Get the value at that index
        Iterator<Boolean> iterator = values().iterator();
        for (int i = 0; i < k; i++) {
            iterator.next();
        }

        return iterator.next();
    }
}