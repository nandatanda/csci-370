import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

public class DataSet implements Serializable {

    private final String[] features;
    public ArrayList<String> splittingFeatures = new ArrayList<>();
    public ArrayList<DataRecord> data = new ArrayList<>();
    private int size;
    private int trainingSize = 7;
    private int testingSize = 3;

    // Constructor for creating Training, Testing, and Validation datasets
    DataSet(String[] features, ArrayList<String> splittingFeatures, int size) {
        this.features = features;
        this.splittingFeatures = splittingFeatures;
        this.size = size;
    }

    // Constructor for creating bootstrapped datasets
    public DataSet(String[] features, ArrayList<DataRecord> data) {
        this.features = features;
        this.data = data;
        this.size = data.size();
    }

    // Constructor for original read
    public DataSet(FileText file, String delimiter) {
        // Split the file's contents by row to form entries
        String[] entries = file.rows();
        // Stores all the features
        this.features = entries[0].split(delimiter);
        // Separates the features that are important for determining split from other splits
        this.splittingFeatures.addAll(Arrays.asList(features).subList(1, features.length - 1));


        // Iterate over the remaining entries, starting from index 1
        for (int i = 1; i < entries.length; i++) {
            // Create a DataPoint using the features and the current entry
            DataRecord row = new DataRecord(features, entries[i], delimiter);
            // Add the DataPoint to the dataset
            data.add(row);
        }

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
            subsets.add(new DataSet(features, splittingFeatures, 0));
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