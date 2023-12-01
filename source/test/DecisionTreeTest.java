import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DecisionTreeTest {

    private ArrayList<String> targetFeaturesValuesMock;
    private DataSet bootstrappedDataSetMock;
    private ArrayList<String> baggedFeaturesMock;
    private Node rootMock;
    private int minSampleMock;
    private int maxDepthMock;

    @BeforeEach
    void setup(){

    }

    @Test
    void should_BuildTree_When_ObjectIsCreated() {
    }

}