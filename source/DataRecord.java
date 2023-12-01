import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * The {@code DataRecord} class represents a single game entry in a dataset.
 * Each data record has a title, rating, and associated values for different features.
 */
public class DataRecord extends LinkedHashMap<String, Object> implements Serializable {

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
     * @param delimiter   the delimiter used to separate values in the entry string
     * @param nameIndex   the index where the record's title is located
     * @param ratingIndex the index where the record's ESRB classification is located
     */
    public DataRecord(String[] headers, String entry, String delimiter, int nameIndex, int ratingIndex) {
        // Split the entire entry using the delimiter
        String[] values = entry.split(delimiter);

        // Populate the LinkedHashMap using headers and corresponding values
        for (int j = 0; j < headers.length && j < values.length; j++) {
            String newFeature = headers[j].trim();

            if (j == nameIndex) {
                // Store the record's title separately
                title = values[j].trim();
            } else if (j == ratingIndex) {
                // Store the record's rating separately
                rating = values[j].trim();
            } else {
                // For other headers, interpret "1" or "true" as boolean true, and store them as a LinkedHashMap
                Object newValue;
                newValue = values[j].trim().equals("1") || values[j].equalsIgnoreCase("true");
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