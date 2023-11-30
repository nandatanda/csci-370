import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;


class FileTextTest {

    @Test
    void should_ReadFile_When_FileTextObjectIsCreated() {
        //given
        final String testFilePath = "source/test/resources/Video_games_esrb_rating_test1.csv";
        //when
        Executable executable = () -> new FileText(testFilePath);
        //Then
        assertDoesNotThrow(executable, "IO Error");
    }

    @Test
    void should_ReturnArrayOfStrings_GivenInput() throws Exception {
        //given
        final String testFilePath = "source/test/resources/Video_games_esrb_rating_test1.csv";
        //when
        FileText testDataCopy = new FileText(testFilePath);
        String[] testSplit = testDataCopy.rows();
        //Then
        assertNotEquals(0, testSplit.length);
    }
}