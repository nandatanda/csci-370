
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.Serializable;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class DataSetTest {

    final String testFilePath = "test/resources/Video_games_esrb_rating_test.csv";
    FileText testDataCopy = new FileText(testFilePath);
    @Mock
    public DataSet dataSetTest;
    @Mock
    public UserConfig settingsTest;

    DataSetTest() throws IOException {
    }


    @BeforeEach
    void setup() {
        this.settingsTest = mock(UserConfig.class);
        this.dataSetTest = new DataSet(testDataCopy);
    }
    @Test
    void get() {
        System.out.println("hello");
    }
//
//    @Test
//    void add() {
//    }
//
//    @Test
//    void features() {
//    }
//
//    @Test
//    void size() {
//    }
//
//    @Test
//    void asArrayList() {
//    }
//
//    @Test
//    void split() {
//    }
}