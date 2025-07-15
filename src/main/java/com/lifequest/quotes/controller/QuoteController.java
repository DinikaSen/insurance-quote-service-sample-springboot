package com.lifequest.quotes.controller;

import com.lifequest.quotes.model.Quote;
import com.lifequest.quotes.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService service;

    public QuoteController(QuoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
        Quote created = service.createQuote(quote);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Quote>> getQuotes(@RequestParam Optional<String> productName) {
        return ResponseEntity.ok(service.getAllQuotes(productName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable String id) {
        return service.getQuoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Quote> updateQuote(@PathVariable String id, @RequestBody Quote quote) {
//        return service.updateQuote(id, quote)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<Quote> patchQuote(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        return service.patchQuote(id, updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable String id) {
        if (service.deleteQuote(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
