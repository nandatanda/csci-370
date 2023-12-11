import java.util.ArrayList;
import java.util.Random;

public class RandomForest {
    RandomForest(DataSet trainingSet, DataSet testingSet, UserConfig settings) {
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;
        this.MIN_SAMPLES = settings.minSamples();
        this.NUM_TREES = settings.maxTrees();
        this.MAX_DEPTH = settings.maxDepth();
        this.settings = settings;

    }

    private final DataSet trainingSet;
    private final DataSet testingSet;

    private final int MIN_SAMPLES;
    private final int NUM_TREES;
    private final int MAX_DEPTH;

    UserConfig settings;
    private ArrayList<String> treeVotes;
    private ArrayList<DecisionTree> decisionTrees;

    public ArrayList<String> getTreeVotes(){
        return this.treeVotes;
    }


    public void train() {

        for (int i = 0; i < NUM_TREES; i++) {
            DataSet bootstrappedDataSet = generateBootstrapDataSet();
            ArrayList<String> baggedFeatures = generateBaggedFeatures(trainingSet.features());
            DecisionTree tree = new DecisionTree(bootstrappedDataSet, baggedFeatures);
            decisionTrees.add(tree);
        }

    }

    public void test() {
        DataRecord datapoint;
        for (int i = 0; i < testingSet.size(); i++) {
            datapoint = testingSet.get(i);
            for (DecisionTree decisionTree : decisionTrees) {
                treeVotes.add(decisionTree.castVote(datapoint));
            }
        }
    }

    public DataSet generateBootstrapDataSet() {
        //Randomly generate bootstrap dataset
        Random rand = new Random();

        DataSet bootstrappedDataset = new DataSet();
        bootstrappedDataset.configure(settings);

        for (int i = 0; i < trainingSet.size(); i++) {
            int r = rand.nextInt(trainingSet.size());
            DataRecord randomRecord = trainingSet.get(r);
            bootstrappedDataset.add(randomRecord);
        }

        return bootstrappedDataset;
    }

    public ArrayList<String> generateBaggedFeatures(ArrayList<String> splittingFeatures) {
        ArrayList<String> baggedFeatures = new ArrayList<>();
        Random rand = new Random();
        int fSize = rand.nextInt(splittingFeatures.size() + 1);

        for (int i = 0; i < fSize; i++) {
            int randomIndex = rand.nextInt(splittingFeatures.size());
            baggedFeatures.add(splittingFeatures.get(randomIndex));

        }

        return baggedFeatures;
    }

}