package com.lifequest.quotes.model;

public class Quote {
    private String id;
    private String productName;

    public double getEstimatedPremium() {
        return estimatedPremium;
    }

    public void setEstimatedPremium(double estimatedPremium) {
        this.estimatedPremium = estimatedPremium;
    }

    private double estimatedPremium;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String state;       // e.g. "CA", "NY"
    private int age;
    private double coverageAmount;
    private String status;      // e.g. "NEW", "UPDATED", "EXPIRED"

    // Constructors, getters & setters
    public Quote() {}


}
