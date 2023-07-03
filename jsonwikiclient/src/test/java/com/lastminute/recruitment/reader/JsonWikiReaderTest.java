package com.lastminute.recruitment.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JsonWikiReaderTest {

    WikiClient mockWikiClient = mock(WikiClient.class);

    JsonWikiReader jsonWikiReader = new JsonWikiReader(mockWikiClient, new ObjectMapper());

    @Test
    public void testRead_Successful() throws Exception {
        // Mock WikiClient

        when(mockWikiClient.read(any()))
                .thenReturn(Objects.requireNonNull(getClass().getClassLoader().getResource("site2.json")).getFile());

        // Perform the test
        WikiPage wikiPage = jsonWikiReader.read("http://wikiscrapper.test/site2");

        // Verify that the WikiClient is called with the correct link
        verify(mockWikiClient).read("http://wikiscrapper.test/site2");

        // Verify the parsed WikiPage object
        assertNotNull(wikiPage);
        assertEquals("Site 2", wikiPage.title());
        assertEquals("Content 2", wikiPage.content());
        assertEquals("http://wikiscrapper.test/site2", wikiPage.selfLink());
        assertEquals(List.of("http://wikiscrapper.test/site4", "http://wikiscrapper.test/site5"), wikiPage.links());
    }

    @Test
    public void testRead_WikiPageNotFound() {

        when(mockWikiClient.read(anyString())).thenThrow(new WikiPageNotFound());

        // Perform the test
        assertThrows(WikiPageNotFound.class, () -> jsonWikiReader.read("invalid_wiki_link"));

        // Verify that the WikiClient is called with the correct link
        verify(mockWikiClient).read("invalid_wiki_link");
    }

}