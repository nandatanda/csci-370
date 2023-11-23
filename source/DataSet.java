import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class DataSet implements Serializable {

    private final String[] features;
    public ArrayList<DataRecord> data = new ArrayList<>();
    private int size;
    private int trainingSize = 7;
    private int testingSize = 3;
    private boolean[] used; // Boolean array that can be used later for checking

    // Constructor for creating Training, Testing, and Validation datasets
    DataSet(String[] features, int size) {
        this.features = features;
        this.size = size;
        this.used = new boolean[size];
    }

    // Constructor for creating bootstrapped datasets
    public DataSet(String[] features, ArrayList<DataRecord> data) {
        this.features = features;
        this.data = data;
        this.size = data.size();
        this.used = new boolean[size];
    }

    // Constructor for original read
    public DataSet(FileText file, String delimiter) {
        // Split the file's contents by row to form entries
        String[] entries = file.rows();

        // Split the first entry (header) into features using the specified delimiter
        this.features = entries[0].split(delimiter);

        // Iterate over the remaining entries, starting from index 1
        for (int i = 1; i < entries.length; i++) {
            // Create a DataPoint using the features and the current entry
            DataRecord row = new DataRecord(features, entries[i], delimiter);
            // Add the DataPoint to the dataset
            data.add(row);
        }

        // Set the size of the dataset
        this.size = data.size();
    }

    public int getSize() {
        return size;
    }

    public void addEntry(DataRecord dp) {
        data.add(dp);
        this.size++;
    }

    // splits Dataset into training and testing
    public ArrayList<DataSet> split(){
        // training, testing, validation
        ArrayList<DataSet> subsets = new ArrayList<>();

        // randomize in O(n) time
        Collections.shuffle(data, new Random());

        for(int i = 0; i < 3; i++){
            subsets.add(new DataSet(features, 0));
        }

        // loop through all data points and separate
        for(int i = 0; i < size; i++){
            int subsetIndex = i % 10;
            int targetSubset = (subsetIndex < trainingSize) ? 0 : 1;
            subsets.get(targetSubset).addEntry(data.get(i));
        }

        return subsets;

    }
}