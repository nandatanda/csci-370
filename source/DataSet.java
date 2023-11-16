import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 * The {@code DataSet} class represents a collection of data points with associated features.
 * It provides functionality for creating training, testing, and validation sets,
 * as well as bootstrapped datasets.
 */
public class DataSet {

    /** List to store individual data points in the dataset. */
    public ArrayList<DataPoint> dataPoints = new ArrayList<>();

    /** Array to store feature names. */
    private final String[] features;

    /** Boolean array used for tracking if data points have been used. */
    private boolean[] used;

    /** The size of the dataset. */
    private int size;

    /**
     * Constructor for creating testing, validation & training datasets.
     *
     * @param features the array of feature names
     * @param size     the size of the dataset
     */
    DataSet(String[] features, int size) {
        this.features = features;
        this.size = 0;
        this.used = new boolean[size];
    }

    /**
     * Constructor for creating bootstrapped datasets.
     *
     * @param features the array of feature names
     * @param data     the existing data points
     */
    public DataSet(String[] features, ArrayList<DataPoint> data) {
        this.features = features;
        this.dataPoints = data;
        this.size = dataPoints.size();
        this.used = new boolean[size];
    }

    /**
     * Constructor for the original read.
     *
     * @param fileText  the FileText object containing dataset information
     * @param delimiter the delimiter used in the dataset
     */
    public DataSet(FileText fileText, char delimiter) {
        String fileAsString = fileText.getText();
        String[] entries = fileAsString.split("\n");
        this.features = entries[0].split(String.valueOf(delimiter));

        for (int i = 1; i < entries.length; i++) {
            DataPoint row = new DataPoint(features, entries[i], delimiter);
            dataPoints.add(row);
        }
        this.size = dataPoints.size();
    }

    /**
     * Gets the size of the dataset.
     *
     * @return the size of the dataset
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds a data point to the dataset.
     *
     * @param dp the data point to be added
     */
    public void addEntry(DataPoint dp) {
        dataPoints.add(dp);
        this.size++;
    }

    /**
     * Splits the dataset into training, testing, and validation sets.
     *
     * @return an ArrayList containing the three subsets
     */
    public ArrayList<DataSet> split() {
        // training, testing, validation
        ArrayList<DataSet> subsets = new ArrayList<>();

        // randomize in O(n) time
        Collections.shuffle(dataPoints, new Random());

        for (int i = 0; i < 3; i++) {
            subsets.add(new DataSet(features, 0));
        }

        // loop through all data points and separate
        for (int i = 0; i < size; i++) {
            int subsetIndex = i % 10;
            int targetSubset = (subsetIndex < 7) ? 0 : ((subsetIndex < 9) ? 1 : 2);
            subsets.get(targetSubset).addEntry(dataPoints.get(i));
        }

        return subsets;
    }
}