import java.util.HashMap;

/**
 * The {@code DataPoint} class represents a single game entry in a dataset.
 * Each data point has a title and associated boolean values for different features.
 */
public class DataPoint {

    /**
     * The data associated with the data point, stored as feature-value pairs.
     */
    private final HashMap<String, Object> data;

    /**
     * Constructs a {@code DataPoint} object by parsing the provided entry and mapping it to features.
     *
     * @param features  an array of feature names
     * @param entry     the entry string containing the data point information
     * @param delimiter the delimiter used to separate values in the entry string
     */
    public DataPoint(String[] features, String entry, char delimiter) {
        this.data = new HashMap<>();

        // Split the entire entry using the delimiter
        String[] values = entry.split(String.valueOf(delimiter));

        // Populate the HashMap using features and corresponding values
        for (int j = 0; j < features.length && j < values.length; j++) {
            String newFeature = features[j];
            Object newValue;

            if (j == 0) {
                // For the first feature (title), directly use the value from the entry string
                newValue = values[j];
            } else {
                // For subsequent features, interpret "1" or "true" as boolean true, and other values as boolean false
                newValue = values[j].equals("1") || values[j].equalsIgnoreCase("true");
            }

            data.put(newFeature, newValue);
        }
    }


    /**
     * Get the data of the data point.
     *
     * @return a copy of the data as a HashMap<String, Boolean>
     */
    public HashMap<String, Object> getData() {
        return new HashMap<>(data);
    }

    /**
     * Get a string representation of the DataPoint object.
     *
     * @return a string containing the title and feature-value pairs
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Title: ").append(title).append("\n");

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return result.toString();
    }

}