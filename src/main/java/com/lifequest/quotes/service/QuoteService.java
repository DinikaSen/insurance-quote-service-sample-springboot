package com.lifequest.quotes.service;

import com.lifequest.quotes.PremiumCalculator;
import com.lifequest.quotes.model.Quote;
import com.lifequest.quotes.util.FileStorageUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private List<Quote> quotes;

    public QuoteService() {
        this.quotes = FileStorageUtil.loadQuotes();

        // Seed some sample quotes if empty
        if (quotes.isEmpty()) {
            seedSampleQuotes();
        }
    }

    private void seedSampleQuotes() {
        quotes.add(createSampleQuote("QT001", "Health Insurance", "CA", 30, 50000, "NEW"));
        quotes.add(createSampleQuote("QT002", "Auto Insurance", "NY", 45, 20000, "NEW"));
        quotes.add(createSampleQuote("QT003", "Life Insurance", "TX", 50, 100000, "NEW"));
        FileStorageUtil.saveQuotes(quotes);
    }

    private Quote createSampleQuote(String id, String productName, String state, int age, double coverage, String status) {
        Quote q = new Quote();
        q.setId(id);
        q.setProductName(productName);
        q.setState(state);
        q.setAge(age);
        q.setCoverageAmount(coverage);
        q.setStatus(status);
        return q;
    }

    public List<Quote> getAllQuotes(Optional<String> productNameFilter) {
        if (productNameFilter.isPresent()) {
            return quotes.stream()
                    .filter(q -> q.getProductName().equalsIgnoreCase(productNameFilter.get()))
                    .collect(Collectors.toList());
        }
        return quotes;
    }

    public Optional<Quote> getQuoteById(String id) {
        return quotes.stream().filter(q -> q.getId().equals(id)).findFirst();
    }

    public Quote createQuote(Quote quote) {
        quote.setId(generateNextQuoteId());
        quote.setStatus("NEW");
        double premium = PremiumCalculator.calculatePremium(quote);
        quote.setEstimatedPremium(premium);
        quotes.add(quote);
        FileStorageUtil.saveQuotes(quotes);
        return quote;
    }


    public Optional<Quote> updateQuote(String id, Quote updatedQuote) {
        return getQuoteById(id).map(existing -> {
            existing.setProductName(updatedQuote.getProductName());
            existing.setState(updatedQuote.getState());
            existing.setAge(updatedQuote.getAge());
            existing.setCoverageAmount(updatedQuote.getCoverageAmount());
            existing.setStatus(updatedQuote.getStatus());
            FileStorageUtil.saveQuotes(quotes);
            return existing;
        });
    }

    public Optional<Quote> patchQuote(String id, Map<String, Object> updates) {
        return getQuoteById(id).map(existing -> {
            updates.forEach((k, v) -> {
                switch (k) {
                    case "productName" -> existing.setProductName(v.toString());
                    case "state" -> existing.setState(v.toString());
                    case "age" -> existing.setAge((Integer) v);
                    case "coverageAmount" -> existing.setCoverageAmount(Double.parseDouble(v.toString()));
                    case "status" -> existing.setStatus(v.toString());
                }
            });
            FileStorageUtil.saveQuotes(quotes);
            return existing;
        });
    }

    public boolean deleteQuote(String id) {
        boolean removed = quotes.removeIf(q -> q.getId().equals(id));
        if (removed) {
            FileStorageUtil.saveQuotes(quotes);
        }
        return removed;
    }

    private String generateNextQuoteId() {
        int maxId = quotes.stream()
                .map(Quote::getId)
                .filter(id -> id != null && id.startsWith("QT"))
                .map(id -> {
                    try {
                        return Integer.parseInt(id.substring(2));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0);

        return String.format("QT%03d", maxId + 1);
    }

}
