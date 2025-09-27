package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Configuration Tests")
class CalculatorConfigTest {
    
    private CalculatorConfig config;
    private static final String TEST_CONFIG_FILE = "calculator.properties";
    
    @BeforeEach
    void setUp() {
        config = CalculatorConfig.getInstance();
    }
    
    @AfterEach
    void cleanup() {
        // Clean up test configuration file
        try {
            Files.deleteIfExists(Paths.get(TEST_CONFIG_FILE));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
    
    @Test
    @DisplayName("Should implement Singleton pattern correctly")
    void testSingletonPattern() {
        CalculatorConfig instance1 = CalculatorConfig.getInstance();
        CalculatorConfig instance2 = CalculatorConfig.getInstance();
        
        assertSame(instance1, instance2, "Should return same instance");
        assertNotNull(instance1, "Instance should not be null");
    }
    
    @Test
    @DisplayName("Should load default configuration values")
    void testDefaultConfiguration() {
        assertEquals(4, config.getPrecision());
        assertEquals(15, config.getMaxDigits());
        assertFalse(config.isHighPrecisionEnabled());
        assertEquals(RoundingMode.HALF_UP, config.getRoundingMode());
        
        assertFalse(config.isMemoryPersistent());
        assertEquals(1, config.getMaxMemorySlots());
        assertTrue(config.isMemoryAutoClearOnStartup());
        
        assertEquals(100, config.getMaxHistoryEntries());
        assertTrue(config.isAutoSaveEnabled());
        assertEquals(".calculator/history.txt", config.getHistoryFilePath());
        assertFalse(config.isHistoryClearOnStartup());
        
        assertEquals("default", config.getTheme());
        assertEquals(400, config.getWindowWidth());
        assertEquals(600, config.getWindowHeight());
        assertTrue(config.isKeyboardShortcutsEnabled());
        
        assertEquals("csv", config.getDefaultExportFormat());
        assertTrue(config.isExportTimestampEnabled());
        assertEquals("Documents/Calculator", config.getDefaultExportDirectory());
        
        assertTrue(config.isValidationEnabled());
        assertFalse(config.isStrictModeEnabled());
        assertEquals(1E15, config.getMaxNumberValue(), 0.001);
        assertEquals(-1E15, config.getMinNumberValue(), 0.001);
    }
    
    @Test
    @DisplayName("Should handle custom configuration file")
    void testCustomConfiguration() throws IOException {
        // Create custom configuration file
        try (FileWriter writer = new FileWriter(TEST_CONFIG_FILE)) {
            writer.write("calculation.precision=6\n");
            writer.write("history.max.entries=50\n");
            writer.write("ui.theme=dark\n");
            writer.write("export.default.format=json\n");
            writer.write("validation.enabled=false\n");
        }
        
        // Reload configuration
        config.reloadConfiguration();
        
        // Verify custom values are loaded
        assertEquals(6, config.getPrecision());
        assertEquals(50, config.getMaxHistoryEntries());
        assertEquals("dark", config.getTheme());
        assertEquals("json", config.getDefaultExportFormat());
        assertFalse(config.isValidationEnabled());
    }
    
    @Test
    @DisplayName("Should validate integer property ranges")
    void testIntegerPropertyValidation() throws IOException {
        // Create configuration with out-of-range values
        try (FileWriter writer = new FileWriter(TEST_CONFIG_FILE)) {
            writer.write("calculation.precision=15\n");  // Max is 10
            writer.write("history.max.entries=2000\n");  // Max is 1000
            writer.write("ui.window.width=100\n");       // Min is 300
        }
        
        config.reloadConfiguration();
        
        // Should use default values for out-of-range properties
        assertEquals(4, config.getPrecision());      // Default
        assertEquals(100, config.getMaxHistoryEntries()); // Default
        assertEquals(400, config.getWindowWidth());  // Default
    }
    
    @Test
    @DisplayName("Should handle invalid property values gracefully")
    void testInvalidPropertyValues() throws IOException {
        // Create configuration with invalid values
        try (FileWriter writer = new FileWriter(TEST_CONFIG_FILE)) {
            writer.write("calculation.precision=invalid\n");
            writer.write("history.max.entries=not_a_number\n");
            writer.write("validation.enabled=maybe\n");
            writer.write("calculation.rounding.mode=INVALID_MODE\n");
        }
        
        config.reloadConfiguration();
        
        // Should use default values for invalid properties
        assertEquals(4, config.getPrecision());
        assertEquals(100, config.getMaxHistoryEntries());
        assertTrue(config.isValidationEnabled()); // Default for boolean
        assertEquals(RoundingMode.HALF_UP, config.getRoundingMode());
    }
    
    @Test
    @DisplayName("Should provide configuration status information")
    void testConfigurationStatus() {
        assertTrue(config.isConfigurationLoaded());
        assertNotNull(config.getAllProperties());
        assertFalse(config.getAllProperties().isEmpty());
        
        String configString = config.toString();
        assertTrue(configString.contains("CalculatorConfig"));
        assertTrue(configString.contains("loaded=true"));
    }
    
    @Test
    @DisplayName("Should support configuration reloading")
    void testConfigurationReload() throws IOException {
        // Get initial value
        int initialPrecision = config.getPrecision();
        
        // Create new configuration file
        try (FileWriter writer = new FileWriter(TEST_CONFIG_FILE)) {
            writer.write("calculation.precision=8\n");
        }
        
        // Reload and verify change
        config.reloadConfiguration();
        assertEquals(8, config.getPrecision());
        assertNotEquals(initialPrecision, config.getPrecision());
    }
    
    @Test
    @DisplayName("Should return immutable properties copy")
    void testPropertiesCopy() {
        var properties = config.getAllProperties();
        int originalSize = properties.size();
        
        // Attempt to modify returned properties
        properties.setProperty("test.property", "test.value");
        
        // Original configuration should be unchanged
        var newProperties = config.getAllProperties();
        assertEquals(originalSize, newProperties.size());
        assertFalse(newProperties.containsKey("test.property"));
    }
}