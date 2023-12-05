import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class DecisionTreeTest {

    private DecisionTree treeTest;
    private ArrayList<DataRecord> dataPointsTest = new ArrayList<>();
    private ArrayList<String> baggedFeaturesTest = new ArrayList<>();
    private int minSampleTest;
    private int maxDepthTest;
    @Mock
    private UserConfig settingsTest;
    private DataSet bootstrappedDataSetTest;



    @BeforeEach
    void setup(){
        this.settingsTest = mock(UserConfig.class);
        this.treeTest = mock(DecisionTree.class);
        this.bootstrappedDataSetTest = mock(DataSet.class);
        this.treeTest = new DecisionTree(bootstrappedDataSetTest, baggedFeaturesTest, settingsTest);
    }

    @Test
    void should_ReturnValueBetweenZeroAndOne_WhenReturningGini() {
        //Given
        double giniTest = treeTest.calculateGiniImpurity(dataPointsTest);
        //When
        //Then
        assertEquals(0, giniTest, 1.00);
    }

}