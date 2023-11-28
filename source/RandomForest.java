import java.util.ArrayList;
import java.util.Random;

public class RandomForest {
    public DataSet dataSet;
    UserConfig settings;
    public int[] treeVotes;
    private ArrayList<DecisionTree> decisionTrees;

    private final int MIN_SAMPLES;
    private final int NUM_TREES;
    private final int MAX_DEPTH;

    RandomForest(DataSet dSet, UserConfig settings) {
        this.MIN_SAMPLES = settings.minSamples();
        this.NUM_TREES = settings.maxTrees();
        this.MAX_DEPTH = settings.maxDepth();
        this.dataSet = dSet;
        this.settings = settings;

    }

    public void train(DataSet dSet) {

        for (int i = 0; i < NUM_TREES; i++) {
            DataSet bootstrappedDataSet = generateBootstrapDataSet(dataSet);
            ArrayList<String> baggedFeatures = generateBaggedFeatures(dataSet.features());
            DecisionTree tree = new DecisionTree(bootstrappedDataSet, baggedFeatures, settings);
            decisionTrees.add(tree);
        }

    }

    public DataSet generateBootstrapDataSet(DataSet dSet) {
        //Randomly generate bootstrap dataset
        Random rand = new Random();

        DataSet bootstrappedDataset = new DataSet(settings);
        for (int i = 0; i < this.dataSet.size(); i++) {
            int r = rand.nextInt(this.dataSet.size());
            DataRecord randomRecord = dataSet.get(r);
            bootstrappedDataset.add(randomRecord);
        }

        return bootstrappedDataset;
    }

    public ArrayList<String> generateBaggedFeatures(ArrayList<String> splittingFeatures) {
        ArrayList<String> baggedFeatures = new ArrayList<>();
        Random rand = new Random();
        int fSize = rand.nextInt(splittingFeatures.size());

        for (int i = 0; i < fSize; i++) {
            int randomIndex = rand.nextInt(splittingFeatures.size());
            baggedFeatures.add(splittingFeatures.get(randomIndex));

        }

        return baggedFeatures;
    }

}