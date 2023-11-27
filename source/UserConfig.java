import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * The {@code UserConfig} class reads configuration settings from a CSV file and provides methods
 * to access these settings.
 */
public class UserConfig {
    private LinkedHashMap<String, Object> configPairs = new LinkedHashMap<>();

    /**
     * Default constructor for {@code UserConfig}.
     * Creates an object with null values. Actual values can be loaded later using the {@code loadConfig} method.
     */
    public UserConfig() {
        // Initialize configPairs with null values
        configPairs.put("trainingDirectory", null);
        configPairs.put("testingDirectory", null);
        configPairs.put("trainingSplit", null);
        configPairs.put("testingSplit", null);
        configPairs.put("nameIndex", null);
        configPairs.put("ratingIndex", null);
        configPairs.put("delimiter", null);
        configPairs.put("ratings", null);
        configPairs.put("maxDepth", null);
        configPairs.put("maxTrees", null);
        configPairs.put("minSamples", null);
    }

    /**
     * Constructs a {@code UserConfig} object by reading configuration settings from a CSV file.
     *
     * @throws IOException if an I/O error occurs during file operations
     */
    public void loadConfig() throws IOException {
        FileText configText = new FileText("config.csv");

        // Parse each row in the configuration file and store key-value pairs
        for (String row : configText.rows()) {
            String[] rowArray = row.split(",");
            String key = rowArray[0].trim();

            // Additional handling for the "ratings" setting
            if ("ratings".equals(key)) {
                // Split the ratings into an array and convert it to a String[]
                String[] values = Arrays.copyOfRange(rowArray, 1, rowArray.length);
                configPairs.put(key, values);
            } else {
                String value = rowArray[1].trim();
                configPairs.put(key, value);
            }
        }
    }

    /**
     * Gets the directory path for training data.
     *
     * @return the training data directory path
     */
    public String trainingDirectory() {
        return configPairs.get("trainingDirectory").toString();
    }

    /**
     * Gets the directory path for testing data.
     *
     * @return the testing data directory path
     */
    public String testingDirectory() {
        return configPairs.get("testingDirectory").toString();
    }

    /**
     * Gets the percentage split for training data.
     *
     * @return the percentage split for training data
     */
    public int trainingSplit() {
        return Integer.parseInt(configPairs.get("trainingSplit").toString());
    }

    /**
     * Gets the percentage split for testing data.
     *
     * @return the percentage split for testing data
     */
    public int testingSplit() {
        return Integer.parseInt(configPairs.get("testingSplit").toString());
    }

    /**
     * Gets the index for the name feature.
     *
     * @return the index for the name feature
     */
    public int nameIndex() {
        String i = configPairs.get("nameIndex").toString();
        return Integer.parseInt(i);
    }

    /**
     * Gets the index for the rating feature.
     *
     * @return the index for the rating feature
     */
    public int ratingIndex() {
        String i = configPairs.get("ratingIndex").toString();
        return Integer.parseInt(i);
    }

    /**
     * Gets the delimiter specified in the configuration as a punctuation.
     *
     * @return the delimiter as a punctuation (e.g., "," or ";")
     */
    public String delimiter() {
        String delimiterName = configPairs.get("delimiter").toString().toLowerCase();

        // Convert delimiter name to punctuation using a switch statement
        return switch (delimiterName) {
            case "comma" -> ",";
            case "semicolon" -> ";";
            default -> delimiterName; // Return the input as is if not recognized
        };
    }

    /**
     * Gets the value(s) of the "ratings" setting, representing the possible ratings in the dataset.
     *
     * @return the value(s) of the "ratings" setting
     * (e.g., ["E", "ET", "T", "M"] representing different content ratings)
     */
    public ArrayList<String> ratings() {
        String[] ratingsArray = (String[]) configPairs.get("ratings");
        return new ArrayList<String>(Arrays.asList(ratingsArray));
    }

    /**
     * Gets the value of the "maxDepth" setting, representing the maximum depth of each decision tree
     * in the random forest.
     *
     * @return the maximum depth of each decision tree in the random forest
     */
    public int maxDepth() {
        String value = configPairs.get("maxDepth").toString();
        return Integer.parseInt(value);
    }

    /**
     * Gets the value of the "maxTrees" setting, representing the maximum number of decision trees
     * to be included in the random forest.
     *
     * @return the maximum number of decision trees in the random forest
     */
    public int maxTrees() {
        String value = configPairs.get("maxTrees").toString();
        return Integer.parseInt(value);
    }

    /**
     * Gets the value of the "minSamples" setting, representing the minimum number of samples
     * required to split an internal node in each decision tree of the random forest.
     *
     * @return the minimum number of samples required to split an internal node in each decision tree
     * of the random forest
     */
    public int minSamples() {
        String value = configPairs.get("minSamples").toString();
        return Integer.parseInt(value);
    }
}