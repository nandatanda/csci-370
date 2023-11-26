import java.io.IOException;
import java.util.LinkedHashMap;

public class Initializer {
    private LinkedHashMap<String, Object> configPairs = new LinkedHashMap<>();

    Initializer() throws IOException {
        FileText configText = new FileText("config.csv");

        for (String row : configText.rows()) {
            configPairs.put(row.split(",")[0], row.split(",")[1]);
        }
    }

    public String trainingDirectory() {
        return configPairs.get("trainingDirectory").toString();
    }

    public String testingDirectory() {
        return configPairs.get("testingDirectory").toString();
    }

    public int trainingSplit() {
        return Integer.parseInt(configPairs.get("trainingSplit").toString());
    }

    public int testingSplit() {
        return Integer.parseInt(configPairs.get("testingSplit").toString());
    }

    public int nameIndex() {
        return Integer.parseInt(configPairs.get("nameIndex").toString());
    }

    public int ratingIndex() {
        return Integer.parseInt(configPairs.get("ratingIndex").toString());
    }

    public String delimiter() {
        String delimiterName = configPairs.get("delimiter").toString().toLowerCase();
        return switch (delimiterName) {
            case "comma" -> ",";
            case "semicolon" -> ";";
            default -> delimiterName; // Return the input as is if not recognized
        };
    }
}
