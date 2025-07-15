package com.lifequest.quotes.util;

import com.lifequest.quotes.model.Quote;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class FileStorageUtil {
    private static final String FILE_PATH = "quotes.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Quote> loadQuotes() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                mapper.writeValue(file, new ArrayList<Quote>());
            }
            return mapper.readValue(file, new TypeReference<List<Quote>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveQuotes(List<Quote> quotes) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), quotes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
