package com.calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    
    private JTextField display;
    private JLabel memoryLabel;
    private Calculator calculator;
    private double firstNumber = 0;
    private String operation = "";
    private boolean isNewCalculation = true;
    
    public CalculatorGUI() {
        calculator = new Calculator();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("🧮 Advanced Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(45, 45, 45));
        
        // Create display panel with memory indicator
        JPanel displayPanel = createDisplayPanel();
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
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
        
        // Main display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        display.setPreferredSize(new Dimension(350, 60));
        panel.add(display, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 5, 3, 3));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttons = {
            "MC", "MR", "MS", "M+", "M-",
            "C", "CE", "√", "x²", "1/x",
            "7", "8", "9", "÷", "%",
            "4", "5", "6", "×", "±",
            "1", "2", "3", "-", "History",
            "0", ".", "=", "+", "Clear H"
        };
        
        for (String text : buttons) {
            JButton button = createButton(text);
            panel.add(button);
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
            } else if (command.equals("x²")) {
                handleSquare();
            } else if (command.equals("1/x")) {
                handleReciprocal();
            } else if (command.startsWith("M")) {
                handleMemoryOperation(command);
            } else if (command.equals("History")) {
                showHistory();
            } else if (command.equals("Clear H")) {
                clearHistory();
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
        firstNumber = Double.parseDouble(display.getText());
        operation = op;
        isNewCalculation = true;
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
                }
                
                display.setText(calculator.formatResult(result));
                operation = "";
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
    
    private void handleSquareRoot() throws CalculatorException {
        double current = Double.parseDouble(display.getText());
        double result = calculator.sqrt(current);
        display.setText(calculator.formatResult(result));
        isNewCalculation = true;
    }
    
    private void handleSquare() throws CalculatorException {
        double current = Double.parseDouble(display.getText());
        double result = calculator.power(current, 2);
        display.setText(calculator.formatResult(result));
        isNewCalculation = true;
    }
    
    private void handleReciprocal() throws CalculatorException {
        double current = Double.parseDouble(display.getText());
        double result = calculator.divide(1, current);
        display.setText(calculator.formatResult(result));
        isNewCalculation = true;
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}




