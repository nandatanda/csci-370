import java.util.Arrays;
import java.util.HashMap;

/**
 * The {@code DataPoint} class represents a single game entry in a dataset.
 * Each data point has a title and associated boolean values for different features.
 */
public class DataPoint {

    /**
     * The data associated with the data point, stored as feature-value pairs.
     */
    private final HashMap<String, Boolean> data;

    /**
     * The title of the data point, typically the first part of the row string in a dataset entry.
     */
    private final String title;

    /**
     * Constructs a {@code DataPoint} object by parsing the provided entry and mapping it to features.
     *
     * @param features  an array of feature names
     * @param entry     the entry string containing the data point information
     * @param delimiter the delimiter used to separate values in the entry string
     */
    public DataPoint(String[] features, String entry, char delimiter) {
        this.data = new HashMap<>();

        // Use StringBuilder to build the title until the delimiter is encountered
        StringBuilder titleBuilder = new StringBuilder();
        int i = 0;
        while (i < entry.length() && entry.charAt(i) != delimiter) {
            char c = entry.charAt(i);
            titleBuilder.append(c);
            i++;
        }
        this.title = titleBuilder.toString();

        // Move to the next character after the delimiter
        i++;

        // Split the remaining entry using the delimiter
        String[] values = entry.substring(i).split(String.valueOf(delimiter));

        // Populate the HashMap using features and corresponding values
        for (int j = 0; j < features.length && j < values.length; j++) {
            data.put(features[j], Boolean.parseBoolean(values[j]));
        }
    }

    /**
     * Get the title of the data point.
     *
     * @return the title as a String
     */
    public String getTitle() {
        return title;
    }


    /**
     * Get the data of the data point.
     *
     * @return a copy of the data as a HashMap<String, Boolean>
     */
    public HashMap<String, Boolean> getData() {
        return new HashMap<>(data);
    }

}