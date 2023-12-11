import java.util.ArrayList;
import java.util.Random;

public class RandomForest {
    RandomForest(DataSet trainingSet, DataSet testingSet) {
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;
        this.NUM_TREES = Main.settings().maxTrees();
        this.decisionTrees = new ArrayList<>();
        this.testingClassifications = new ArrayList<>();

    }

    private final DataSet trainingSet;
    private final DataSet testingSet;

    private final int NUM_TREES;


    public ArrayList<String> testingClassifications;

    private ArrayList<DecisionTree> decisionTrees;

    public ArrayList<String> getTestingClassifications(){
        return this.testingClassifications;
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
        ArrayList<String> treeVotes = new ArrayList<>();
        for (int i = 0; i < testingSet.size(); i++) {
            datapoint = testingSet.get(i);
            for (DecisionTree decisionTree : decisionTrees) {
                treeVotes.add(decisionTree.castVote(datapoint));
            }
        }
        testingClassifications.add(findMajorityOfTrees(treeVotes));

    }

    public String predict(DataRecord d){
        String predictedLabel = "";
        for (DecisionTree decisionTree : decisionTrees) {
            Node treeNode = decisionTree.root();
            while (!treeNode.isLeaf()) {
                String feature = treeNode.splitFeature();
                if ((boolean) d.get(feature)) {
                    treeNode = treeNode.right();
                } else {
                    treeNode = treeNode.left();
                }
            }
            predictedLabel = treeNode.label();
        }
        return predictedLabel;
    }

    private String findMajorityOfTrees(ArrayList<String> votes){
        RatingsMap ratingsCount = new RatingsMap();
        ratingsCount.initialize();
        // loop through tree votes and get those counts
        for(String v : votes){
            ratingsCount.increment(v);
        }
        String highestRating = "";
        int highestCount = 0;
        // loop through and find the highest rating among them
        for (String r : ratingsCount.keySet()) {
           if (ratingsCount.get(r) > highestCount){
               highestCount = ratingsCount.get(r);
               highestRating = r;
           }
        }
        return highestRating;


    }

    public DataSet generateBootstrapDataSet() {
        //Randomly generate bootstrap dataset
        Random rand = new Random();

        DataSet bootstrappedDataset = new DataSet();
        bootstrappedDataset.configure(Main.settings());

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