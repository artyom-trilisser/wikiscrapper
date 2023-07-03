package com.lastminute.recruitment.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.domain.error.WikiPageParsingError;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;


@RequiredArgsConstructor
public class JsonWikiReader implements WikiReader {

    private final WikiClient client;
    private final ObjectMapper objectMapper;

    @Override
    public WikiPage read(String link) throws WikiPageNotFound, WikiPageParsingError {
        String fileName = client.read(link);

        try {
            return objectMapper.readValue(new File(fileName), WikiPage.class);
        } catch (IOException e) {
            throw new WikiPageParsingError(e.getMessage());
        }
    }
}
