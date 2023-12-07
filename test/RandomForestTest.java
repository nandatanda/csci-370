import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;



class RandomForestTest {

    public ArrayList<DecisionTree> decisionTreesTest = new ArrayList<>(0);
    @Mock
    private RandomForest randomForestTest;
    private DataSet dataSetTest;
    private UserConfig settingsTest;


    @BeforeEach
    void setup() {
        this.dataSetTest = mock(DataSet.class);
        this.settingsTest = mock(UserConfig.class);
        this.randomForestTest = new RandomForest(dataSetTest, settingsTest);
    }

    @Test
    @DisplayName("Tree populated")
    void should_PopulateForestWithDecisionTrees_When_BaggedFeaturesAndBoostrapDataIsGenerated() {

        for (int i = 0; i < 100; i++) {
            // Given
            DataSet bootstrappedDataSet = randomForestTest.generateBootstrapDataSet(dataSetTest);
            ArrayList<String> baggedFeatures = randomForestTest.generateBaggedFeatures(dataSetTest.features());
            DecisionTree tree = new DecisionTree(bootstrappedDataSet, baggedFeatures, settingsTest);
            // When
            decisionTreesTest.add(tree);
        }
        // Then
        assertNotEquals(0, decisionTreesTest.size());
    }

}