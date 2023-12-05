import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DataSetTest implements Serializable {

    final String testFilePath = "source/test/resources/Video_games_esrb_rating_test.csv";
    FileText testDataCopy = new FileText(testFilePath);
    private DataSet dataSetTest;
    @Mock
    private UserConfig settingsTest;

    DataSetTest() throws IOException {
    }


    @BeforeEach
    void setup() {
        this.settingsTest = mock(UserConfig.class);
        this.dataSetTest = new DataSet(testDataCopy, settingsTest);
    }
    @Test
    void get() {

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