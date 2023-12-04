import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Collections;

/**
 * The {@code DataSet} class represents a collection of data records and provides methods
 * for manipulation and analysis of datasets.
 */
public class DataSet implements Serializable, Iterable<DataRecord>{

    private final ArrayList<DataRecord> data;
    private int size;
    private UserConfig settings;
    private ArrayList<String> features;

    /**
     * Default constructor for an empty dataset.
     */
    public DataSet() {
        this.data = new ArrayList<>();
        this.settings = null;
        this.features = null;
        this.size = 0;
    }

    /**
     * Constructor for creating a dataset from a file.
     *
     * @param file     the file containing the dataset
     * @param settings the configuration settings for the dataset
     */
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

    /**
     * Gets the list of features in the dataset.
     *
     * @return the list of features
     */
    public ArrayList<String> features() {
        return features;
    }

    /**
     * Sets the list of features in the dataset.
     *
     * @param featureList the list of features to set
     */
    public void setFeatures(ArrayList<String> featureList) {
        features = featureList;
    }

    /**
     * Gets the configuration settings for the dataset.
     *
     * @return the configuration settings
     */
    public UserConfig settings() {
        return settings;
    }

    /**
     * Sets the configuration settings for the dataset.
     *
     * @param settings the configuration settings to set
     */
    public void config(UserConfig settings) {
        this.settings = settings;
    }

    /**
     * Gets the data record at the specified index.
     *
     * @param i the index of the data record
     * @return the data record at the specified index
     */
    public DataRecord get(int i) {
        return data.get(i);
    }

    /**
     * Adds a data record to the dataset.
     *
     * @param dp the data record to be added
     */
    public void add(DataRecord dp) {
        data.add(dp);
        this.size++;
    }

    /**
     * Gets the size of the dataset.
     *
     * @return the size of the dataset
     */
    public int size() {
        return size;
    }

    /**
     * Converts the dataset to an ArrayList of data records.
     *
     * @return the dataset as an ArrayList of data records
     */
    public ArrayList<DataRecord> asArrayList() {
        return data;
    }

    /**
     * Splits the dataset into training and testing subsets based on the specified ratios.
     *
     * @return an ArrayList containing two subsets (training and testing)
     */
    public ArrayList<DataSet> split() {
        // Make a container to return all subsets
        ArrayList<DataSet> subsets = new ArrayList<>();

        // Prepare an empty dataset, maintaining settings and features from parent
        DataSet emptySet = new DataSet();
        emptySet.config(this.settings);
        emptySet.setFeatures(this.features);

        // Add two empty dataset copies to the container
        subsets.add(emptySet);
        subsets.add(emptySet);

        // Randomize the superset to be selected from
        Collections.shuffle(data, new Random());

        // Calculate the sizes of training and testing subsets based on ratios
        int trainingSize = size * (settings.trainingRatio() / (settings.trainingRatio() + settings.testingRatio()));

        // Assign data points to training and testing subsets
        for (int i = 0; i < size; i++) {
            int subsetIndex = i < trainingSize ? 0 : 1;
            subsets.get(subsetIndex).add(data.get(i)); // Use the add method to add DataRecord
        }
        return subsets;
    }

    /**
     * Iterator implementation for the DataSet class.
     */
    private class DataSetIterator implements Iterator<DataRecord> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public DataRecord next() {
            return data.get(currentIndex++);
        }
    }

    /**
     * Gets an iterator over the data records in the dataset.
     *
     * @return an iterator over the data records
     */
    @Override
    public Iterator<DataRecord> iterator() {
        return new DataSetIterator();
    }
}