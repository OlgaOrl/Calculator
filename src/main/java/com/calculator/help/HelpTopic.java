package com.calculator.help;

import java.util.List;

public class HelpTopic {
    private String title;
    private String description;
    private List<String> examples;
    private List<String> keywords;
    
    public HelpTopic(String title, String description, List<String> examples, List<String> keywords) {
        this.title = title;
        this.description = description;
        this.examples = examples;
        this.keywords = keywords;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<String> getExamples() {
        return examples;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }
    
    public boolean matchesQuery(String query) {
        return title.toLowerCase().contains(query) ||
               description.toLowerCase().contains(query) ||
               keywords.stream().anyMatch(k -> k.contains(query));
    }
    
    @Override
    public String toString() {
        return title;
    }
}