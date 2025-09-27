package com.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CalculatorGUI extends JFrame implements ActionListener, KeyListener {
    
    private JTextField display;
    private JLabel operationLabel;
    private JLabel memoryLabel;
    private JLabel statusLabel;
    private Calculator calculator;
    private double firstNumber = 0;
    private String operation = "";
    private boolean isNewCalculation = true;
    
    public CalculatorGUI() {
        calculator = new Calculator();
        initializeGUI();
        setupKeyboardShortcuts();
        createExportButton();
        applyConfiguration();
        createConfigurationMenu();
    }
    
    private void initializeGUI() {
        setTitle("🧮 Advanced Calculator - Use keyboard for faster calculations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(45, 45, 45));
        
        // Create display panel with memory and status indicators
        JPanel displayPanel = createDisplayPanel();
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Status bar for keyboard feedback
        statusLabel = new JLabel("Ready - Use keyboard or mouse");
        statusLabel.setForeground(Color.LIGHT_GRAY);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        
        // Make focusable for keyboard input
        setFocusable(true);
        addKeyListener(this);
        display.addKeyListener(this);
        
        // Request focus for keyboard input
        SwingUtilities.invokeLater(() -> {
            requestFocus();
            display.requestFocus();
        });
    }
    
    private void setupKeyboardShortcuts() {
        JComponent rootPane = getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();
        
        // Memory shortcuts
        inputMap.put(KeyStroke.getKeyStroke("ctrl M"), "memoryStore");
        actionMap.put("memoryStore", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double current = Double.parseDouble(display.getText());
                    calculator.memoryStore(current);
                    updateStatusLabel("Memory stored: " + calculator.formatResult(current));
                    updateMemoryDisplay();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("ctrl R"), "memoryRecall");
        actionMap.put("memoryRecall", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                double memValue = calculator.memoryRecall();
                display.setText(calculator.formatResult(memValue));
                updateStatusLabel("Memory recalled: " + calculator.formatResult(memValue));
                isNewCalculation = true;
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("ctrl shift C"), "memoryClear");
        actionMap.put("memoryClear", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                calculator.memoryClear();
                updateStatusLabel("Memory cleared");
                updateMemoryDisplay();
            }
        });
        
        // Constants shortcuts
        inputMap.put(KeyStroke.getKeyStroke("ctrl P"), "insertPi");
        actionMap.put("insertPi", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                display.setText(calculator.formatResult(Math.PI));
                updateStatusLabel("π inserted: " + calculator.formatResult(Math.PI));
                isNewCalculation = true;
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("ctrl E"), "insertE");
        actionMap.put("insertE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                display.setText(calculator.formatResult(Math.E));
                updateStatusLabel("e inserted: " + calculator.formatResult(Math.E));
                isNewCalculation = true;
            }
        });
        
        // Function shortcuts
        inputMap.put(KeyStroke.getKeyStroke("ctrl S"), "squareRoot");
        actionMap.put("squareRoot", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleSquareRoot();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("F1"), "showHelp");
        actionMap.put("showHelp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });
    }
    
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 45, 45));
        
        // Memory indicator
        memoryLabel = new JLabel("Memory: 0", SwingConstants.RIGHT);
        memoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        memoryLabel.setForeground(Color.LIGHT_GRAY);
        memoryLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        panel.add(memoryLabel, BorderLayout.NORTH);
        
        // Operation indicator
        operationLabel = new JLabel("", SwingConstants.CENTER);
        operationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        operationLabel.setForeground(Color.ORANGE);
        operationLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        panel.add(operationLabel, BorderLayout.CENTER);
        
        // Main display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        display.setPreferredSize(new Dimension(350, 60));
        panel.add(display, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 5, 3, 3));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttons = {
            "MC", "MR", "MS", "M+", "M-",
            "C", "CE", "√", "x²", "1/x",
            "∛", "x^y", "n√", "%", "±",
            "7", "8", "9", "÷", "Help",
            "4", "5", "6", "×", "Clear H",
            "1", "2", "3", "-", "",
            "0", ".", "=", "+", ""
        };
        
        for (String text : buttons) {
            if (!text.isEmpty()) {
                JButton button = createButton(text);
                panel.add(button);
            } else {
                panel.add(new JLabel()); // Empty space
            }
        }
        
        return panel;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(65, 45));
        button.addActionListener(this);
        
        // Color coding
        if (text.matches("[0-9.]")) {
            // Numbers and decimal
            button.setBackground(new Color(80, 80, 80));
            button.setForeground(Color.WHITE);
        } else if (text.matches("[+\\-×÷=]")) {
            // Basic operations
            button.setBackground(new Color(255, 149, 0));
            button.setForeground(Color.WHITE);
        } else if (text.matches("M[CRS+\\-]")) {
            // Memory operations
            button.setBackground(new Color(100, 149, 237));
            button.setForeground(Color.WHITE);
        } else if (text.matches("√|x²|1/x|%|±")) {
            // Advanced functions
            button.setBackground(new Color(138, 43, 226));
            button.setForeground(Color.WHITE);
        } else {
            // Other functions
            button.setBackground(new Color(165, 165, 165));
            button.setForeground(Color.BLACK);
        }
        
        // Add tooltips for constant buttons
        if (text.equals("π")) {
            button.setToolTipText("Pi (3.14159...) - for circles");
        } else if (text.equals("e")) {
            button.setToolTipText("Euler's number (2.71828...) - for exponential");
        } else if (text.equals("φ")) {
            button.setToolTipText("Golden Ratio (1.61803...) - nature proportions");
        } else if (text.equals("2π")) {
            button.setToolTipText("2×Pi (6.28318...) - full circle circumference");
        }
        
        button.setFocusPainted(false);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        try {
            if (command.matches("[0-9]")) {
                handleNumber(command);
            } else if (command.equals(".")) {
                handleDecimal();
            } else if (command.matches("[+\\-×÷]")) {
                handleOperation(command);
            } else if (command.equals("=")) {
                handleEquals();
            } else if (command.equals("C") || command.equals("CE")) {
                handleClear();
            } else if (command.equals("±")) {
                handlePlusMinus();
            } else if (command.equals("%")) {
                handlePercent();
            } else if (command.equals("√")) {
                handleSquareRoot();
            } else if (command.equals("∛")) {
                handleCubeRoot();
            } else if (command.equals("x²")) {
                handleSquare();
            } else if (command.equals("xʸ")) {
                handlePowerOperation();
            } else if (command.equals("ⁿ√")) {
                handleNthRoot();
            } else if (command.equals("1/x")) {
                handleReciprocal();
            } else if (command.startsWith("M")) {
                handleMemoryOperation(command);
            } else if (command.equals("History")) {
                showHistory();
            } else if (command.equals("Clear H")) {
                clearHistory();
            } else if (command.equals("Help")) {
                showHelp();
            }
            
            updateMemoryDisplay();
            
        } catch (Exception ex) {
            display.setText("Error");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            isNewCalculation = true;
        }
    }
    
    private void handleNumber(String number) {
        if (isNewCalculation) {
            display.setText(number);
            isNewCalculation = false;
        } else {
            String current = display.getText();
            if (!current.equals("0")) {
                display.setText(current + number);
            } else {
                display.setText(number);
            }
        }
    }
    
    private void handleDecimal() {
        String current = display.getText();
        if (isNewCalculation) {
            display.setText("0.");
            isNewCalculation = false;
        } else if (!current.contains(".")) {
            display.setText(current + ".");
        }
    }
    
    private void handleOperation(String op) {
        if (!operation.isEmpty()) {
            handleEquals();
        }
        
        firstNumber = Double.parseDouble(display.getText());
        operation = op;
        isNewCalculation = true;
        
        // Update operation display with simple symbols
        String operationSymbol = switch (op) {
            case "+" -> "+";
            case "-" -> "-";
            case "×" -> "×";
            case "÷" -> "÷";
            case "power" -> "^";
            default -> op;
        };
        operationLabel.setText(calculator.formatResult(firstNumber) + " " + operationSymbol);
    }
    
    private void handleEquals() {
        if (!operation.isEmpty()) {
            try {
                double secondNumber = Double.parseDouble(display.getText());
                double result = 0;
                
                switch (operation) {
                    case "+":
                        result = calculator.add(firstNumber, secondNumber);
                        break;
                    case "-":
                        result = calculator.subtract(firstNumber, secondNumber);
                        break;
                    case "×":
                        result = calculator.multiply(firstNumber, secondNumber);
                        break;
                    case "÷":
                        result = calculator.divide(firstNumber, secondNumber);
                        break;
                    case "power":
                        result = calculator.power(firstNumber, secondNumber);
                        break;
                }
                
                display.setText(calculator.formatResult(result));
                operation = "";
                operationLabel.setText(""); // Clear operation display
                isNewCalculation = true;
                
            } catch (CalculatorException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                handleClear();
            }
        }
    }
    
    private void handleClear() {
        display.setText("0");
        firstNumber = 0;
        operation = "";
        operationLabel.setText(""); // Clear operation display
        isNewCalculation = true;
    }
    
    private void handlePlusMinus() {
        double current = Double.parseDouble(display.getText());
        current = -current;
        display.setText(calculator.formatResult(current));
    }
    
    private void handlePercent() {
        double current = Double.parseDouble(display.getText());
        current = current / 100;
        display.setText(calculator.formatResult(current));
    }
    
    private void handleSquareRoot() {
        try {
            double current = Double.parseDouble(display.getText());
            double result = calculator.squareRoot(current);
            display.setText(calculator.formatResult(result));
            isNewCalculation = true;
        } catch (CalculatorException ex) {
            showError(ex.getMessage());
        }
    }
    
    private void handleSquare() {
        try {
            double current = Double.parseDouble(display.getText());
            double result = calculator.power(current, 2);
            display.setText(calculator.formatResult(result));
            isNewCalculation = true;
        } catch (CalculatorException ex) {
            showError(ex.getMessage());
        }
    }
    
    private void handleReciprocal() {
        try {
            double current = Double.parseDouble(display.getText());
            double result = calculator.divide(1, current);
            display.setText(calculator.formatResult(result));
            isNewCalculation = true;
        } catch (CalculatorException ex) {
            showError(ex.getMessage());
        }
    }
    
    private void handleMemoryOperation(String command) throws CalculatorException {
        double current = Double.parseDouble(display.getText());
        
        switch (command) {
            case "MS":
                calculator.memoryStore(current);
                JOptionPane.showMessageDialog(this, "Value stored in memory", "Memory", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "MR":
                double memValue = calculator.memoryRecall();
                display.setText(calculator.formatResult(memValue));
                isNewCalculation = true;
                break;
            case "MC":
                calculator.memoryClear();
                JOptionPane.showMessageDialog(this, "Memory cleared", "Memory", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "M+":
                calculator.memoryAdd(current);
                JOptionPane.showMessageDialog(this, "Value added to memory", "Memory", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "M-":
                calculator.memorySubtract(current);
                JOptionPane.showMessageDialog(this, "Value subtracted from memory", "Memory", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    private void showHistory() {
        var history = calculator.getHistory();
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No calculations in history", "History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder("Calculation History:\n\n");
            for (int i = Math.max(0, history.size() - 10); i < history.size(); i++) {
                sb.append(history.get(i)).append("\n");
            }
            
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            JOptionPane.showMessageDialog(this, scrollPane, "History", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearHistory() {
        calculator.clearHistory();
        JOptionPane.showMessageDialog(this, "History cleared", "History", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateMemoryDisplay() {
        double memValue = calculator.getMemoryValue();
        if (calculator.hasMemoryValue()) {
            memoryLabel.setText("Memory: " + calculator.formatResult(memValue));
            memoryLabel.setForeground(Color.CYAN);
        } else {
            memoryLabel.setText("Memory: 0");
            memoryLabel.setForeground(Color.LIGHT_GRAY);
        }
    }
    
    private void handleCubeRoot() {
        try {
            double current = Double.parseDouble(display.getText());
            double result = calculator.cubeRoot(current);
            display.setText(calculator.formatResult(result));
            isNewCalculation = true;
        } catch (CalculatorException ex) {
            showError(ex.getMessage());
        }
    }
    
    private void handlePowerOperation() {
        firstNumber = Double.parseDouble(display.getText());
        operation = "power";
        isNewCalculation = true;
    }
    
    private void handleNthRoot() {
        String nStr = JOptionPane.showInputDialog(this, "Enter root degree (n):", "Nth Root", JOptionPane.QUESTION_MESSAGE);
        if (nStr != null && !nStr.trim().isEmpty()) {
            try {
                double current = Double.parseDouble(display.getText());
                double n = Double.parseDouble(nStr.trim());
                double result = calculator.nthRoot(current, n);
                display.setText(calculator.formatResult(result));
                isNewCalculation = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private JPanel createConstantsInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Mathematical Constants"));
        panel.setBackground(new Color(60, 60, 60));
        
        JLabel piLabel = new JLabel("π (Pi) ≈ 3.14159 - Circle calculations");
        piLabel.setForeground(Color.WHITE);
        piLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel eLabel = new JLabel("e (Euler) ≈ 2.71828 - Exponential functions");
        eLabel.setForeground(Color.WHITE);
        eLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel goldenLabel = new JLabel("φ (Golden) ≈ 1.61803 - Golden ratio");
        goldenLabel.setForeground(Color.WHITE);
        goldenLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panel.add(piLabel);
        panel.add(eLabel);
        panel.add(goldenLabel);
        
        return panel;
    }
    
    private void showHelp() {
        String helpText = """
            🧮 CALCULATOR HELP & KEYBOARD SHORTCUTS
            
            ⌨️ KEYBOARD SHORTCUTS:
            
            📱 NUMBER INPUT:
            • 0-9 keys - Type numbers directly
            • . key - Decimal point
            • Backspace - Delete last digit
            
            🔢 OPERATIONS:
            • + key - Addition
            • - key - Subtraction
            • * key - Multiplication
            • / key - Division
            • Enter - Calculate result
            • Esc or C - Clear calculation
            
            💾 MEMORY FUNCTIONS:
            • Ctrl+M - Memory Store (MS)
            • Ctrl+R - Memory Recall (MR)
            • Ctrl+Shift+C - Memory Clear (MC)
            • M+ and M- buttons for add/subtract
            
            🔢 CONSTANTS:
            • Ctrl+P - Insert π (Pi = 3.14159...)
            • Ctrl+E - Insert e (Euler = 2.71828...)
            
            🔧 ADVANCED FUNCTIONS:
            • Ctrl+S - Square root (√)
            • F1 - Show this help
            
            🖱️ MOUSE OPERATIONS:
            • √ - Square root
            • ∛ - Cube root  
            • x² - Square a number
            • xʸ - Power operation
            • ⁿ√ - Nth root
            • 1/x - Reciprocal
            • % - Percentage
            • ± - Change sign
            
            📊 HISTORY:
            • History - View calculations
            • Clear H - Clear history
            
            💡 TIPS:
            • Mix keyboard and mouse as you prefer
            • Status bar shows confirmation of key presses
            • All shortcuts work the same as clicking buttons
            • Memory value shown at top when active
            • History tracks all calculations
            """;
        
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setBackground(new Color(50, 50, 50));
        textArea.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Calculator Help & Keyboard Shortcuts", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();
        
        try {
            if (Character.isDigit(keyChar)) {
                handleNumber(String.valueOf(keyChar));
                updateStatusLabel("Number entered: " + keyChar);
            } else if (keyChar == '.') {
                handleDecimal();
                updateStatusLabel("Decimal point added");
            } else if (keyChar == '+') {
                handleOperation("+");
                updateStatusLabel("Operation: Addition");
            } else if (keyChar == '-') {
                handleOperation("-");
                updateStatusLabel("Operation: Subtraction");
            } else if (keyChar == '*') {
                handleOperation("×");
                updateStatusLabel("Operation: Multiplication");
            } else if (keyChar == '/') {
                handleOperation("÷");
                updateStatusLabel("Operation: Division");
            } else if (keyCode == KeyEvent.VK_ENTER) {
                handleEquals();
                updateStatusLabel("Result calculated");
            } else if (keyCode == KeyEvent.VK_ESCAPE || keyChar == 'c' || keyChar == 'C') {
                handleClear();
                updateStatusLabel("Calculation cleared");
            } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                handleBackspace();
                updateStatusLabel("Last digit deleted");
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }
    
    private void handleBackspace() {
        String current = display.getText();
        if (current.length() > 1 && !current.equals("0")) {
            display.setText(current.substring(0, current.length() - 1));
        } else {
            display.setText("0");
            isNewCalculation = true;
        }
    }
    
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.CYAN);
        
        // Clear status message after 3 seconds
        Timer timer = new Timer(3000, e -> {
            statusLabel.setText("Ready - Use keyboard or mouse");
            statusLabel.setForeground(Color.LIGHT_GRAY);
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showError(String message) {
        display.setText("Error");
        updateStatusLabel("Error: " + message);
        statusLabel.setForeground(Color.RED);
        isNewCalculation = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used but required by KeyListener interface
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required by KeyListener interface
    }
    
    private void createExportButton() {
        JButton exportButton = new JButton("Export CSV");
        exportButton.setFont(new Font("Arial", Font.PLAIN, 12));
        exportButton.addActionListener(e -> exportHistoryToCSV());
        
        // Add to button panel
        JPanel exportPanel = new JPanel();
        exportPanel.add(exportButton);
        add(exportPanel, BorderLayout.SOUTH);
    }

    private void exportHistoryToCSV() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
            fileChooser.setSelectedFile(new java.io.File(CSVExporter.generateFilename()));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                CSVExporter.exportToCSV(calculator.getHistory(), fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "History exported successfully!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void applyConfiguration() {
        CalculatorConfig config = calculator.getConfig();
        
        // Apply window size from configuration
        setSize(config.getWindowWidth(), config.getWindowHeight());
        
        // Apply theme settings
        applyTheme(config.getTheme());
        
        // Configure keyboard shortcuts based on settings
        if (config.isKeyboardShortcutsEnabled()) {
            setupKeyboardShortcuts();
        }
        
        // Update status label with configuration info
        updateStatusLabel("Configuration loaded: " + config.toString());
    }

    private void applyTheme(String theme) {
        Color backgroundColor;
        Color foregroundColor;
        
        switch (theme.toLowerCase()) {
            case "dark":
                backgroundColor = new Color(30, 30, 30);
                foregroundColor = Color.WHITE;
                break;
            case "light":
                backgroundColor = Color.WHITE;
                foregroundColor = Color.BLACK;
                break;
            default:
                backgroundColor = new Color(45, 45, 45);
                foregroundColor = Color.WHITE;
        }
        
        // Apply theme colors to components
        getContentPane().setBackground(backgroundColor);
        display.setBackground(backgroundColor);
        display.setForeground(foregroundColor);
    }

    private void createConfigurationMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu configMenu = new JMenu("Configuration");
        
        JMenuItem reloadConfig = new JMenuItem("Reload Configuration");
        reloadConfig.addActionListener(e -> {
            calculator.getConfig().reloadConfiguration();
            applyConfiguration();
            JOptionPane.showMessageDialog(this, "Configuration reloaded successfully!");
        });
        
        JMenuItem showConfig = new JMenuItem("Show Configuration");
        showConfig.addActionListener(e -> showConfigurationDialog());
        
        configMenu.add(reloadConfig);
        configMenu.add(showConfig);
        menuBar.add(configMenu);
        
        setJMenuBar(menuBar);
    }

    private void showConfigurationDialog() {
        CalculatorConfig config = calculator.getConfig();
        StringBuilder info = new StringBuilder("Current Configuration:\n\n");
        
        info.append("Precision: ").append(config.getPrecision()).append("\n");
        info.append("Max History: ").append(config.getMaxHistoryEntries()).append("\n");
        info.append("Theme: ").append(config.getTheme()).append("\n");
        info.append("Auto Save: ").append(config.isAutoSaveEnabled()).append("\n");
        info.append("Validation: ").append(config.isValidationEnabled()).append("\n");
        info.append("Export Format: ").append(config.getDefaultExportFormat()).append("\n");
        
        JTextArea textArea = new JTextArea(info.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Configuration", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}




