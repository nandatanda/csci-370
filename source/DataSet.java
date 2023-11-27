import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class DataSet implements Serializable {

    private UserConfig settings;
    public ArrayList<String> features = new ArrayList<>();
    private ArrayList<DataRecord> data = new ArrayList<>();
    private int size;

    // Constructor for original read
    public DataSet(FileText file, UserConfig settings) {
        this.settings = settings;

        // Split the file's contents by row to form entries
        String[] entries = file.rows();

        // Store column headers in the first row to a string array
        String[] csvHeader = entries[0].split(settings.delimiter());

        // Iterate over the remaining entries, starting from index 1
        for (int i = 1; i < entries.length; i++) {
            // Create a DataPoint using the header from the csv and the latest entry in the loop
            String latestEntry = entries[i];
            DataRecord newRow = new DataRecord(csvHeader, latestEntry, settings.delimiter(), settings.nameIndex(), settings.ratingIndex());

            // Add the DataPoint to the dataset
            data.add(newRow);
        }

        // Store the features from the first record & update the size
        this.features = new ArrayList<String>(data.get(0).keySet());
        this.size = data.size();
    }

    // Constructor for creating Training, Testing, and Validation datasets
    DataSet(String[] headers, ArrayList<String> features, int size) {
        this.headers = headers;
        this.features = features;
        this.size = size;
    }

    // Constructor for creating bootstrapped datasets
    public DataSet(String[] headers, ArrayList<DataRecord> data) {
        this.headers = headers;
        this.data = data;
        this.size = data.size();
    }

    public ArrayList<String> features() {
        return features;
    }

    public int size() {
        return size;
    }

    public DataRecord get(int i) {
        return data.get(i);
    }

    public void addEntry(DataRecord dp) {
        data.add(dp);
        this.size++;
    }

    // splits Dataset into training and testing
    public ArrayList<DataSet> split() {
        // training, testing, validation
        ArrayList<DataSet> subsets = new ArrayList<>();

        // randomize in O(n) time
        Collections.shuffle(data, new Random());

        for (int i = 0; i < 3; i++) {
            subsets.add(new DataSet(headers, features, 0));
        }

        // loop through all data points and separate
        for (int i = 0; i < size; i++) {
            int subsetIndex = i % 10;
            int targetSubset = (subsetIndex < settings.trainingSplit() / 10) ? 0 : 1;
            subsets.get(targetSubset).addEntry(data.get(i));
        }

        return subsets;

    }
}