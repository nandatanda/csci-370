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
        ArrayList<DataSet> subsetsList = new ArrayList<>();

        // Check if serialized subsets file exists
        File subsetsObjectFile = new File("data/subsets.ser");
        if (subsetsObjectFile.exists()) {
            // If the serialized subsets file exists, load subsets from the file
            subsetsList = serializer.loadListFromFile("data/subsets.ser", DataSet.class);
        } else {
            // If the serialized subsets file doesn't exist, a new one must be constructed
            if (settings.testingDirectory() != null) {
                // If a testing file path is specified, create training and testing datasets from file
                subsetsList.add(new DataSet(new FileText(settings.trainingDirectory())));
                subsetsList.add(new DataSet(new FileText(settings.testingDirectory())));
            } else {
                // If there isn't a testing file specified, the training data must be partitioned
                serializer.saveToFile(new DataSet(trainingText), "data/dataset.ser");
                trainingRecords = new DataSet(trainingText);
                subsetsList = new DataSet(trainingText).splitForTrainingAndTesting();
            }

            serializer.saveToFile(allRecords, "data/dataset.ser");
            serializer.saveListToFile(subsetsList, "data/subsets.ser");


            ArrayList<DataSet> subsets = loadDatasetAndSplit(trainingText, settings.delimiter());
            DataSet trainingSet = subsets.get(0);
            DataSet testingSet = subsets.get(1);

            RandomForest rf = new RandomForest(trainingSet, testingSet, settings);
            System.out.println(trainingSet.features());
            //System.out.println(rf.set.data.get(0));

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
