import java.io.IOException;
import java.util.LinkedHashMap;

public class Initializer {
    private LinkedHashMap<String, Object> configPairs = new LinkedHashMap<String, Object>();

    Initializer() throws IOException {
        FileText configText = new FileText("config.csv");

        for (String row : configText.rows()) {
            configPairs.put(row.split(",")[0], row.split(",")[1]);
        }
    }

    String trainingDirectory() {
        return configPairs.get("trainingDirectory").toString();
    }

}