import java.io.IOException;
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
            configPairs.put(row.split(",")[0], row.split(",")[1]);
        }
    }

    /**
     * Gets the directory path for training data.
     *
     * @return the training data directory path
     */
    public String trainingDirectory() {
        return configPairs.get("trainingDirectory").toString().trim();
    }

    /**
     * Gets the directory path for testing data.
     *
     * @return the testing data directory path
     */
    public String testingDirectory() {
        return configPairs.get("testingDirectory").toString().trim();
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
        String i = configPairs.get("nameIndex").toString().trim();
        return Integer.parseInt(i);
    }

    /**
     * Gets the index for the rating feature.
     *
     * @return the index for the rating feature
     */
    public int ratingIndex() {
        String i = configPairs.get("ratingIndex").toString().trim();
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
}