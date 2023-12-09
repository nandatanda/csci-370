import javax.xml.crypto.Data;
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

        // Prepare container for training and testing sets
        ArrayList<DataSet> datasetList = new ArrayList<>();

        // Check if a serialized datasets file exists
        File datasetsFile = new File("data/datasets.ser");
        if (datasetsFile.exists()) {
            // If a serialized datasets file exists, load training and testing subsets from the file
            datasetList = serializer.loadListFromFile("data/datasets.ser", DataSet.class);
        } else {
            // If the serialized datasets file doesn't exist, a new one must be constructed
            if (settings.testingDirectory() != null) {
                // If a testing file path is specified, create training and testing datasets from their respective files
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

        // Test the operation by querying a record that was read from disk
        // System.out.println(datasetList.get(0).get(0).title() + " : " + datasetList.get(1).get(0).toString());

        // Everything is loaded, time to build some trees
        DataSet trainingSet = datasetList.get(0);
        DecisionTree tree = new DecisionTree(trainingSet, trainingSet.features());

    }

        public static UserConfig settings () {
            return settings;
        }
    }
