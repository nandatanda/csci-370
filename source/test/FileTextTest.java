import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;


class FileTextTest {

    @Test
    void should_ReadFile_When_FileTextObjectIsCreated() {
        //given
        String testFilePath = "source/test/resources/Video_games_esrb_rating_test1.csv";
        //when
        Executable executable = () -> new FileText(testFilePath);
        //Then
        assertDoesNotThrow(() -> executable, "IO Error");
    }

    @Test
    void should_ReturnArrayOfStrings_GivenInput() {
        //given
        //when
        //Then
//        assertNotNull();
    }
}