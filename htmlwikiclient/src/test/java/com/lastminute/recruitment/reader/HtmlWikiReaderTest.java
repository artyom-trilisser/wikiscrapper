package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class HtmlWikiReaderTest {

    WikiClient mockWikiClient = mock(WikiClient.class);

    HtmlWikiReader htmlWikiReader = new HtmlWikiReader(mockWikiClient);

    @Test
    public void testRead_Successful() throws Exception {
        // Mock WikiClient

        when(mockWikiClient.read(any()))
                .thenReturn(Objects.requireNonNull(getClass().getClassLoader().getResource("site1.html")).getFile());

        // Perform the test
        WikiPage wikiPage = htmlWikiReader.read("http://wikiscrapper.test/site1");

        // Verify that the WikiClient is called with the correct link
        verify(mockWikiClient).read("http://wikiscrapper.test/site1");

        // Verify the parsed WikiPage object
        assertNotNull(wikiPage);
        assertEquals("Site 1", wikiPage.title());
        assertEquals("Content1", wikiPage.content());
        assertEquals("http://wikiscrapper.test/site1", wikiPage.selfLink());
        assertEquals(List.of("http://wikiscrapper.test/site4", "http://wikiscrapper.test/site5"), wikiPage.links());
    }

    @Test
    public void testRead_WikiPageNotFound() {

        when(mockWikiClient.read(anyString())).thenThrow(new WikiPageNotFound());

        // Perform the test
        assertThrows(WikiPageNotFound.class, () -> htmlWikiReader.read("invalid_wiki_link"));

        // Verify that the WikiClient is called with the correct link
        verify(mockWikiClient).read("invalid_wiki_link");
    }
}