import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code Main} class serves as the entry point for the program.
 * It demonstrates the loading and manipulation of a dataset.
 */
public class Main {

    private static final UserConfig settings = new UserConfig();

    /**
     * The main method that is executed when the program starts.
     * It loads a dataset from a CSV file, splits it into subsets, and performs some basic operations.
     *
     * @param args command-line arguments (not used in this program)
     * @throws IOException if an I/O error occurs during file operations
     */
    public static void main(String[] args) throws IOException {
        // Load settings from config.csv
        settings.loadConfig();

        // Initialize serialization object
        Serialization<DataSet> serializer = new Serialization<>();

        // Prepare container for data subsets
        ArrayList<DataSet> datasetList = new ArrayList<>();

        // Check if serialized subsets file exists
        File datasetsFile = new File("data/datasets.ser");
        if (datasetsFile.exists()) {
            // If the serialized subsets file exists, load subsets from the file
            datasetList = serializer.loadListFromFile("data/datasets.ser", DataSet.class);
        } else {
            // If the serialized subsets file doesn't exist, a new one must be constructed
            if (settings.testingDirectory() != null) {
                // If a testing file path is specified, create training and testing datasets from file
                datasetList.add(new DataSet(new FileText(settings.trainingDirectory())));
                datasetList.add(new DataSet(new FileText(settings.testingDirectory())));
            } else {
                // If there isn't a testing file specified, the training data must be partitioned
                ArrayList<DataSet> dataPartitions = new DataSet(new FileText(settings.trainingDirectory())).splitForTrainingAndTesting();
                datasetList.add(dataPartitions.get(0));
                datasetList.add(dataPartitions.get(1));
            }

            // Save the constructed dataset and subsets objects to serialized files
            serializer.saveListToFile(datasetList, "data/datasets.ser");
        }
    }

        /**
         * Loads the dataset from a file, splits it into subsets, and performs some basic operations.
         *
         * @param originalText  the FileText object containing dataset information
         * @param delimiter the delimiter used in the dataset
         */
        public static ArrayList<DataSet> loadDatasetAndSplit (FileText originalText, String delimiter){
            // Create variables to hold the superset and subsets of the dataset
            DataSet allRecords;
            ArrayList<DataSet> recordSubsets;

            // File to store serialized subsets
            File subsetsObjectFile = new File("data/subsets.ser");

            // Create a serializer for object serialization
            Serialization<DataSet> serializer = new Serialization<>();

            // Check if serialized subsets file exists
            if (!subsetsObjectFile.exists()) {
                // If not, create a new superset, split it into subsets, and save them to files
                allRecords = new DataSet(originalText);
                System.out.println(allRecords);
                recordSubsets = allRecords.splitForTrainingAndTesting();
                serializer.saveToFile(allRecords, "data/dataset.ser");
                serializer.saveListToFile(recordSubsets, "data/subsets.ser");

            } else {
                // If the serialized subsets file exists, load subsets from the file
                recordSubsets = serializer.loadListFromFile("data/subsets.ser", DataSet.class);
            }

            // Load the superset again (for demonstration purposes)
            allRecords = new DataSet(originalText);

            // Print information about a data point from the superset
            DataRecord sampleRecord = allRecords.get(99);
            System.out.println("\n" + sampleRecord.title() + " (rated " + sampleRecord.rating() + ")");
            System.out.println(sampleRecord);
            return recordSubsets;
        }

        public static UserConfig settings () {
            return settings;
        }
    }
