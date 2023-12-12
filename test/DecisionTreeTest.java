import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.mockito.Mock;
import java.util.ArrayList;
import static org.mockito.Mockito.mock;

public class DecisionTreeTest {

    public DecisionTree treeTest;
    public ArrayList<DataRecord> dataPointsTest = new ArrayList<>();
    public ArrayList<String> baggedFeaturesTest = new ArrayList<>();
    @Mock
    public UserConfig settingsTest;
    public DataSet bootstrappedDataSetTest;



    @BeforeEach
    public void setup(){
        this.settingsTest = mock(UserConfig.class);
        this.treeTest = mock(DecisionTree.class);
        this.bootstrappedDataSetTest = mock(DataSet.class);
        this.treeTest = new DecisionTree(bootstrappedDataSetTest, baggedFeaturesTest);
    }

    @Test
    public void should_ReturnValueBetweenZeroAndOne_When_ReturningGini() {
        //Given
//        double giniTest = treeTest.calculateGiniImpurity(dataPointsTest);
        //When
        //Then
//        assertEquals(0, giniTest, 1.00);
        System.out.println("poop");
    }

}