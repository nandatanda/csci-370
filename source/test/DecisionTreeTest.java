import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class DecisionTreeTest {
    @Mock
    private UserConfig settingsMock;
    private DecisionTree treeMock;
    private ArrayList<String> targetFeaturesValuesMock = new ArrayList<String>();
    private DataSet bootstrappedDataSetMock;
    private ArrayList<String> baggedFeaturesMock = new ArrayList<String>();
    private Node rootMock;
    private int minSampleMock;
    private int maxDepthMock;

    @BeforeEach
    void setup(){
        this.rootMock = mock(Node.class);
        this.settingsMock = mock(UserConfig.class);
        this.treeMock = mock(DecisionTree.class);
        this.bootstrappedDataSetMock = mock(DataSet.class);


        this.treeMock = new DecisionTree(bootstrappedDataSetMock, baggedFeaturesMock,settingsMock);
    }

    @Test
    void should_BuildTree_When_ObjectIsCreated() {
        System.out.println("Hello");
    }

}