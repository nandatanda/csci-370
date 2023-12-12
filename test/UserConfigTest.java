import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

public class UserConfigTest {
    public UserConfig configTest =  new UserConfig();

    @Test
    @DisplayName("Configuration file loaded")
    public void should_LoadConfigFile_WhenGivenFilePath(){
        // Given
        final String testFilePath = "test/resources/configTest.csv";
        // When
        Executable executable = () -> new FileText(testFilePath);
        // Then
        assertDoesNotThrow(executable, "IO Error");
    }
}