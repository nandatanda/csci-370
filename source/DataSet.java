import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class DataSet implements Serializable {

    private ArrayList<DataRecord> data;
    private int size;
    private final UserConfig settings;
    private final ArrayList<String> features;

    // Default constructor for an empty dataset
    public DataSet(UserConfig settings) {
        this.settings = settings;
        this.features = new ArrayList<>();
        this.size = 0;
    }

    // Constructor for original read
    public DataSet(FileText file, UserConfig settings) {
        this.data = new ArrayList<>();
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
        this.features = new ArrayList<>(data.get(0).keySet());
        this.size = data.size();
    }

    public DataRecord get(int i) {
        return data.get(i);
    }

    public void add(DataRecord dp) {
        data.add(dp);
        this.size++;
    }

    public ArrayList<String> features() {
        return features;
    }

    public int size() {
        return size;
    }

    public ArrayList<DataSet> split() {
        // Reserve two empty DataSet objects for the split
        ArrayList<DataSet> subsets = new ArrayList<>();
        subsets.add(new DataSet(settings));
        subsets.add(new DataSet(settings));

        // Randomize the superset to be selected from
        Collections.shuffle(data, new Random());

        // Calculate the sizes of training and testing subsets based on ratios
        int trainingSize = (int) (size * (settings.trainingRatio() / (settings.trainingRatio() + settings.testingRatio())));

        // Assign data points to training and testing subsets
        for (int i = 0; i < size; i++) {
            int subsetIndex = i < trainingSize ? 0 : 1;
            subsets.get(subsetIndex).add(data.get(i)); // Use the add method to add DataRecord
        }
        return subsets;
    }
}