import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Collections;

/**
 * The {@code DataSet} class represents a collection of data records and provides methods
 * for manipulation and analysis of datasets.
 */
public class DataSet implements Serializable, Iterable<DataRecord> {

    // List to store data records
    private final ArrayList<DataRecord> data;

    // Configuration settings for the dataset
    private UserConfig settings;

    // List of features in the dataset
    private ArrayList<String> features;

    /**
     * Default constructor for an empty dataset.
     */
    public DataSet() {
        this.data = new ArrayList<>();
        this.settings = null;
        this.features = null;
    }

    /**
     * Constructor for creating a dataset from a file.
     *
     * @param file the file containing the dataset
     */
    public DataSet(FileText file) {
        this.data = new ArrayList<>();
        configure(Main.settings());

        // Split the file's contents by row to form entries
        try (CSVReader reader = new CSVReader(new FileReader(Main.settings().trainingDirectory()))) {
            // reads the first line
            String[] csvHeader = reader.readNext();
            String[] row;
            while ((row = reader.readNext()) != null) {
                DataRecord newRow = new DataRecord(csvHeader, row);
                data.add(newRow);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        String[] entries = file.rows();

        // Store the features from the first record & update the size
        this.features = new ArrayList<>(data.get(0).keySet());
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
    public void configure(UserConfig settings) {
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
    }

    /**
     * Gets the size of the dataset.
     *
     * @return the size of the dataset
     */
    public int size() {
        return data.size();
    }

    /**
     * Converts the dataset to an ArrayList of data records.
     *
     * @return the dataset as an ArrayList of data records
     */
    public ArrayList<DataRecord> data() {
        return data;
    }

    /**
     * Shuffles the order of records in the dataset.
     */
    public void shuffle() {
        Collections.shuffle(data, new Random());
    }

    /**
     * Partitions the dataset into x equal subsets.
     *
     * @param x the number of partitions
     * @return an ArrayList containing x subsets
     */
    public ArrayList<DataSet> partition(int x) {
        ArrayList<DataSet> partitionList = new ArrayList<>();

        // Calculate the size of each partition
        int partitionSize = size() / x;

        // Handle the case where x is greater than the size of the dataset
        if (partitionSize == 0) {
            partitionSize = 1;
            x = size();  // Set x to the size of the dataset
        }

        // Randomize the order of records
        shuffle();

        // Partition the records
        int startIndex = 0;
        for (int i = 0; i < x; i++) {
            // Calculate the end index for the current partition
            int endIndex = startIndex + partitionSize;
            if (i == x - 1) {
                // Adjust the end index for the last partition to include any remaining records
                endIndex = size();
            }

            // Create a new DataSet for the current partition
            DataSet partition = new DataSet();
            partition.configure(settings);
            partition.setFeatures(features);

            // Add records from startIndex to endIndex to the current partition
            for (int j = startIndex; j < endIndex; j++) {
                partition.add(data.get(j));
            }

            // Add the current partition to the list
            partitionList.add(partition);

            // Update the startIndex for the next partition
            startIndex = endIndex;
        }

        return partitionList;
    }

    /**
     * Splits the dataset into training and testing subsets based on the specified ratios.
     *
     * @return an ArrayList containing two subsets (training and testing)
     */
    public ArrayList<DataSet> splitForTrainingAndTesting() {
        // Make a container to return both subsets
        ArrayList<DataSet> subsets = new ArrayList<>();

        // Prepare an empty dataset, maintaining settings and features from parent
        DataSet emptySet = new DataSet();
        emptySet.configure(settings);
        emptySet.setFeatures(features);

        // Add two empty dataset copies to the container
        subsets.add(emptySet);
        subsets.add(emptySet);

        // Randomize the superset to be selected from
        shuffle();

        // Calculate the sizes of training and testing subsets based on ratios
        int trainingSize = (int) (size() * settings.partitionRatio());

        // Test the partition of the data
        System.out.println("The total size is " + size());
        System.out.println("The training size should be " + trainingSize);

        // Assign data points to training and testing subsets
        for (int i = 0; i < size(); i++) {
            int subsetIndex;
            if (i < trainingSize) {
                subsetIndex = 0;
            } else {
                subsetIndex = 1;
            }
            subsets.get(subsetIndex).add(data.get(i)); // Use the add method to add DataRecord
        }

        return subsets;


    }

    /**
     * Overrides the toString() method to print all records in a formatted manner.
     *
     * @return a string representation of the dataset
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Iterate through all records
        for (DataRecord record : data) {
            sb.append(record.toString()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Iterator implementation for the DataSet class.
     */
    private class DataSetIterator implements Iterator<DataRecord> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size();
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