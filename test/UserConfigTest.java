import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class UserConfigTest {
    private UserConfig configTest =  new UserConfig();

    @Test
    @DisplayName("Configuration file loaded")
    void should_LoadConfigFile_WhenGivenFilePath(){
        // Given
        final String testFilePath = "source/test/resources/configTest.csv";
        // When
        Executable executable = () -> new FileText(testFilePath);
        // Then
        assertDoesNotThrow(executable, "IO Error");
    }
}