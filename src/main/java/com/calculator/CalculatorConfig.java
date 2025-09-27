package com.calculator;

import java.io.*;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Singleton configuration manager for the Calculator application.
 * 
 * This class manages all configuration settings loaded from the calculator.properties file.
 * It implements the Singleton pattern to ensure only one configuration instance exists
 * throughout the application lifecycle.
 * 
 * Features:
 * - Singleton pattern implementation
 * - Properties file loading with fallback defaults
 * - Type-safe getter methods for all configuration values
 * - Runtime configuration reloading
 * - Comprehensive error handling and logging
 * - Default value fallback system
 * 
 * @author Calculator Development Team
 * @version 1.0
 * @since 1.0
 */
public class CalculatorConfig {
    
    private static final Logger LOGGER = Logger.getLogger(CalculatorConfig.class.getName());
    private static final String CONFIG_FILE = "/calculator.properties";
    private static final String EXTERNAL_CONFIG_FILE = "calculator.properties";
    
    // Singleton instance
    private static volatile CalculatorConfig instance;
    private static final Object lock = new Object();
    
    // Configuration properties
    private Properties properties;
    private boolean configurationLoaded = false;
    
    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the configuration by loading properties from file.
     */
    private CalculatorConfig() {
        properties = new Properties();
        loadConfiguration();
    }
    
    /**
     * Returns the singleton instance of CalculatorConfig.
     * Uses double-checked locking pattern for thread safety.
     * 
     * @return the singleton CalculatorConfig instance
     */
    public static CalculatorConfig getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CalculatorConfig();
                }
            }
        }
        return instance;
    }
    
    /**
     * Loads configuration from properties file with fallback to defaults.
     * First attempts to load from external file, then from classpath resources.
     */
    private void loadConfiguration() {
        loadDefaultProperties();
        
        // Try to load from external file first
        if (!loadFromExternalFile()) {
            // Fallback to classpath resource
            loadFromClasspath();
        }
        
        configurationLoaded = true;
        LOGGER.info("Configuration loaded successfully");
    }
    
    /**
     * Loads default configuration values as fallback.
     */
    private void loadDefaultProperties() {
        // Calculation settings
        properties.setProperty("calculation.precision", "4");
        properties.setProperty("calculation.max.digits", "15");
        properties.setProperty("calculation.high.precision.enabled", "false");
        properties.setProperty("calculation.rounding.mode", "HALF_UP");
        
        // Memory settings
        properties.setProperty("memory.persistent", "false");
        properties.setProperty("memory.max.slots", "1");
        properties.setProperty("memory.auto.clear.on.startup", "true");
        
        // History settings
        properties.setProperty("history.max.entries", "100");
        properties.setProperty("history.auto.save.enabled", "true");
        properties.setProperty("history.file.path", ".calculator/history.txt");
        properties.setProperty("history.clear.on.startup", "false");
        
        // UI settings
        properties.setProperty("ui.theme", "default");
        properties.setProperty("ui.window.width", "400");
        properties.setProperty("ui.window.height", "600");
        properties.setProperty("ui.keyboard.shortcuts.enabled", "true");
        properties.setProperty("ui.show.operation.preview", "true");
        properties.setProperty("ui.sound.effects.enabled", "false");
        
        // Export settings
        properties.setProperty("export.default.format", "csv");
        properties.setProperty("export.include.timestamp", "true");
        properties.setProperty("export.default.directory", "Documents/Calculator");
        properties.setProperty("export.auto.filename", "true");
        
        // Advanced settings
        properties.setProperty("debug.logging.enabled", "false");
        properties.setProperty("debug.log.file.path", ".calculator/debug.log");
        properties.setProperty("validation.enabled", "true");
        properties.setProperty("validation.strict.mode", "false");
        properties.setProperty("validation.max.number.value", "1E15");
        properties.setProperty("validation.min.number.value", "-1E15");
        
        // Performance settings
        properties.setProperty("performance.caching.enabled", "false");
        properties.setProperty("performance.cache.max.size", "50");
        properties.setProperty("performance.multithreading.enabled", "false");
    }
    
    /**
     * Attempts to load configuration from external properties file.
     * 
     * @return true if external file was loaded successfully, false otherwise
     */
    private boolean loadFromExternalFile() {
        try (InputStream input = new FileInputStream(EXTERNAL_CONFIG_FILE)) {
            properties.load(input);
            LOGGER.info("Configuration loaded from external file: " + EXTERNAL_CONFIG_FILE);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.FINE, "External configuration file not found: " + EXTERNAL_CONFIG_FILE);
            return false;
        }
    }
    
    /**
     * Loads configuration from classpath resources.
     */
    private void loadFromClasspath() {
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
                LOGGER.info("Configuration loaded from classpath: " + CONFIG_FILE);
            } else {
                LOGGER.warning("Configuration file not found in classpath: " + CONFIG_FILE);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error loading configuration from classpath", e);
        }
    }
    
    /**
     * Reloads the configuration from the properties file.
     * This method can be called at runtime to refresh configuration settings.
     */
    public void reloadConfiguration() {
        LOGGER.info("Reloading configuration...");
        loadConfiguration();
    }
    
    // ========================================
    // CALCULATION SETTINGS GETTERS
    // ========================================
    
    /**
     * Gets the number of decimal places for calculation precision.
     * 
     * @return precision value (0-10), defaults to 4
     */
    public int getPrecision() {
        return getIntProperty("calculation.precision", 4, 0, 10);
    }
    
    /**
     * Gets the maximum number of digits to display in results.
     * 
     * @return maximum digits, defaults to 15
     */
    public int getMaxDigits() {
        return getIntProperty("calculation.max.digits", 15, 1, 20);
    }
    
    /**
     * Checks if high precision mode is enabled.
     * 
     * @return true if high precision mode is enabled, false otherwise
     */
    public boolean isHighPrecisionEnabled() {
        return getBooleanProperty("calculation.high.precision.enabled", false);
    }
    
    /**
     * Gets the rounding mode for calculations.
     * 
     * @return RoundingMode enum value, defaults to HALF_UP
     */
    public RoundingMode getRoundingMode() {
        String mode = getStringProperty("calculation.rounding.mode", "HALF_UP");
        try {
            return RoundingMode.valueOf(mode);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid rounding mode: " + mode + ", using HALF_UP");
            return RoundingMode.HALF_UP;
        }
    }
    
    // ========================================
    // MEMORY SETTINGS GETTERS
    // ========================================
    
    /**
     * Checks if memory should persist across application sessions.
     * 
     * @return true if memory is persistent, false otherwise
     */
    public boolean isMemoryPersistent() {
        return getBooleanProperty("memory.persistent", false);
    }
    
    /**
     * Gets the maximum number of memory slots available.
     * 
     * @return number of memory slots (1-10), defaults to 1
     */
    public int getMaxMemorySlots() {
        return getIntProperty("memory.max.slots", 1, 1, 10);
    }
    
    /**
     * Checks if memory should be automatically cleared on startup.
     * 
     * @return true if memory auto-clear is enabled, false otherwise
     */
    public boolean isMemoryAutoClearOnStartup() {
        return getBooleanProperty("memory.auto.clear.on.startup", true);
    }
    
    // ========================================
    // HISTORY SETTINGS GETTERS
    // ========================================
    
    /**
     * Gets the maximum number of history entries to keep in memory.
     * 
     * @return maximum history entries, defaults to 100
     */
    public int getMaxHistoryEntries() {
        return getIntProperty("history.max.entries", 100, 10, 1000);
    }
    
    /**
     * Checks if automatic history saving is enabled.
     * 
     * @return true if auto-save is enabled, false otherwise
     */
    public boolean isAutoSaveEnabled() {
        return getBooleanProperty("history.auto.save.enabled", true);
    }
    
    /**
     * Gets the file path for history storage.
     * 
     * @return history file path, defaults to ".calculator/history.txt"
     */
    public String getHistoryFilePath() {
        return getStringProperty("history.file.path", ".calculator/history.txt");
    }
    
    /**
     * Checks if history should be cleared on application startup.
     * 
     * @return true if history clear on startup is enabled, false otherwise
     */
    public boolean isHistoryClearOnStartup() {
        return getBooleanProperty("history.clear.on.startup", false);
    }
    
    // ========================================
    // UI SETTINGS GETTERS
    // ========================================
    
    /**
     * Gets the UI theme setting.
     * 
     * @return theme name (default, dark, light, system), defaults to "default"
     */
    public String getTheme() {
        return getStringProperty("ui.theme", "default");
    }
    
    /**
     * Gets the window width setting.
     * 
     * @return window width in pixels, defaults to 400
     */
    public int getWindowWidth() {
        return getIntProperty("ui.window.width", 400, 300, 1200);
    }
    
    /**
     * Gets the window height setting.
     * 
     * @return window height in pixels, defaults to 600
     */
    public int getWindowHeight() {
        return getIntProperty("ui.window.height", 600, 400, 800);
    }
    
    /**
     * Checks if keyboard shortcuts are enabled.
     * 
     * @return true if keyboard shortcuts are enabled, false otherwise
     */
    public boolean isKeyboardShortcutsEnabled() {
        return getBooleanProperty("ui.keyboard.shortcuts.enabled", true);
    }
    
    /**
     * Checks if operation preview should be shown in display.
     * 
     * @return true if operation preview is enabled, false otherwise
     */
    public boolean isOperationPreviewEnabled() {
        return getBooleanProperty("ui.show.operation.preview", true);
    }
    
    /**
     * Checks if sound effects are enabled.
     * 
     * @return true if sound effects are enabled, false otherwise
     */
    public boolean isSoundEffectsEnabled() {
        return getBooleanProperty("ui.sound.effects.enabled", false);
    }
    
    // ========================================
    // EXPORT SETTINGS GETTERS
    // ========================================
    
    /**
     * Gets the default export format.
     * 
     * @return export format (csv, txt, json, xml), defaults to "csv"
     */
    public String getDefaultExportFormat() {
        return getStringProperty("export.default.format", "csv");
    }
    
    /**
     * Checks if timestamp should be included in exported files.
     * 
     * @return true if timestamp inclusion is enabled, false otherwise
     */
    public boolean isExportTimestampEnabled() {
        return getBooleanProperty("export.include.timestamp", true);
    }
    
    /**
     * Gets the default export directory.
     * 
     * @return export directory path, defaults to "Documents/Calculator"
     */
    public String getDefaultExportDirectory() {
        return getStringProperty("export.default.directory", "Documents/Calculator");
    }
    
    /**
     * Checks if automatic filename generation is enabled.
     * 
     * @return true if auto filename is enabled, false otherwise
     */
    public boolean isAutoFilenameEnabled() {
        return getBooleanProperty("export.auto.filename", true);
    }
    
    // ========================================
    // VALIDATION SETTINGS GETTERS
    // ========================================
    
    /**
     * Checks if input validation is enabled.
     * 
     * @return true if validation is enabled, false otherwise
     */
    public boolean isValidationEnabled() {
        return getBooleanProperty("validation.enabled", true);
    }
    
    /**
     * Checks if strict validation mode is enabled.
     * 
     * @return true if strict mode is enabled, false otherwise
     */
    public boolean isStrictModeEnabled() {
        return getBooleanProperty("validation.strict.mode", false);
    }
    
    /**
     * Gets the maximum allowed number value.
     * 
     * @return maximum number value, defaults to 1E15
     */
    public double getMaxNumberValue() {
        return getDoubleProperty("validation.max.number.value", 1E15);
    }
    
    /**
     * Gets the minimum allowed number value.
     * 
     * @return minimum number value, defaults to -1E15
     */
    public double getMinNumberValue() {
        return getDoubleProperty("validation.min.number.value", -1E15);
    }
    
    // ========================================
    // UTILITY METHODS
    // ========================================
    
    /**
     * Gets a string property with default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value if property is not found
     * @return the property value or default
     */
    private String getStringProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets an integer property with validation and default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value
     * @param minValue the minimum allowed value
     * @param maxValue the maximum allowed value
     * @return the validated property value or default
     */
    private int getIntProperty(String key, int defaultValue, int minValue, int maxValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        
        try {
            int intValue = Integer.parseInt(value.trim());
            if (intValue < minValue || intValue > maxValue) {
                LOGGER.warning(String.format("Property %s value %d is out of range [%d, %d], using default %d", 
                              key, intValue, minValue, maxValue, defaultValue));
                return defaultValue;
            }
            return intValue;
        } catch (NumberFormatException e) {
            LOGGER.warning(String.format("Invalid integer value for property %s: %s, using default %d", 
                          key, value, defaultValue));
            return defaultValue;
        }
    }
    
    /**
     * Gets a double property with default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value
     * @return the property value or default
     */
    private double getDoubleProperty(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            LOGGER.warning(String.format("Invalid double value for property %s: %s, using default %f", 
                          key, value, defaultValue));
            return defaultValue;
        }
    }
    
    /**
     * Gets a boolean property with default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value
     * @return the property value or default
     */
    private boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        
        return Boolean.parseBoolean(value.trim());
    }
    
    /**
     * Checks if the configuration has been successfully loaded.
     * 
     * @return true if configuration is loaded, false otherwise
     */
    public boolean isConfigurationLoaded() {
        return configurationLoaded;
    }
    
    /**
     * Gets all configuration properties as a Properties object.
     * Returns a copy to prevent external modification.
     * 
     * @return copy of all configuration properties
     */
    public Properties getAllProperties() {
        Properties copy = new Properties();
        copy.putAll(properties);
        return copy;
    }
    
    /**
     * Returns a string representation of the configuration status.
     * 
     * @return configuration status string
     */
    @Override
    public String toString() {
        return String.format("CalculatorConfig{loaded=%s, properties=%d}", 
                           configurationLoaded, properties.size());
    }
}