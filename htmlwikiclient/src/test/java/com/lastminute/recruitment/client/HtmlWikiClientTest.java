package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlWikiClientTest {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRead_Successful() {
        // Create the JsonWikiClient instance
        HtmlWikiClient jsonWikiClient = new HtmlWikiClient();

        // Perform the test
        String link = "http://wikiscrapper.test/site1";
        String result = jsonWikiClient.read(link);

        // Verify that the correct absolute file path is returned
        assertTrue(result.endsWith("site1.html"));
    }

    @Test
    public void testRead_WikiPageNotFound() {
        // Create the JsonWikiClient instance
        HtmlWikiClient jsonWikiClient = new HtmlWikiClient();

        // Perform the test with an invalid link
        String link = "http://wikiscrapper.test/non_existent_link";
        assertThrows(WikiPageNotFound.class, () -> jsonWikiClient.read(link));
    }

    @Test
    public void testRead_SpecialCharacters() {
        // Create the JsonWikiClient instance
        HtmlWikiClient jsonWikiClient = new HtmlWikiClient();

        // Perform the test with a link containing special characters
        String link = "http://wikiscrapper.test/\"site1\"";
        String result = jsonWikiClient.read(link);

        // Verify that the correct absolute file path is returned after replacing special characters
        assertTrue(result.endsWith("site1.html"));
    }

}