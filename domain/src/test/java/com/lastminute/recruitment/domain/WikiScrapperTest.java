package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.domain.error.WikiPageNotFoundRuntime;
import com.lastminute.recruitment.domain.error.WikiPageParsingError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WikiScrapperTest {

    @Mock
    private WikiReader wikiReader;

    @Mock
    private WikiPageRepository repository;

    @InjectMocks
    private WikiScrapper wikiScrapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLinks_ReturnsPagesFromRepository() {
        // Arrange
        List<WikiPage> mockPages = new ArrayList<>();
        mockPages.add(new WikiPage("Page1", "Content1", "http://wikiscrapper.test/site1", new ArrayList<>()));
        mockPages.add(new WikiPage("Page2", "Content2", "http://wikiscrapper.test/site2", new ArrayList<>()));
        when(repository.getPages()).thenReturn(mockPages);

        // Act
        Collection<WikiPage> result = wikiScrapper.getLinks();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsAll(mockPages));
    }

    @Test
    public void testRead_WhenPageExists_DoesNotCallWikiReader() throws Exception {
        // Arrange
        String link = "http://example.com/page";
        when(repository.getPageBySelfLink(link))
                .thenReturn(Optional.of(new WikiPage("Page1", "Content1", "http://wikiscrapper.test/site1", new ArrayList<>())));

        // Act
        wikiScrapper.read(link);

        // Assert
        verify(wikiReader, never()).read(anyString());
    }

    @Test
    public void testRead_WhenPageDoesNotExist_CallsWikiReaderAndSavesToRepository() throws Exception {
        // Arrange
        String link = "http://example.com/page";
        when(repository.getPageBySelfLink(link)).thenReturn(Optional.empty());
        WikiPage mockPage =
                new WikiPage("Page1", "Content1", "http://wikiscrapper.test/site1", new ArrayList<>());
        when(wikiReader.read(link)).thenReturn(mockPage);

        // Act
        wikiScrapper.read(link);

        // Assert
        verify(wikiReader, times(1)).read(link);
        verify(repository, times(1)).save(mockPage);
    }

    @Test
    public void testRead_WhenParsingError_LogsError() throws WikiPageNotFound, WikiPageParsingError {
        // Arrange
        String link = "http://example.com/page";
        when(repository.getPageBySelfLink(link)).thenReturn(Optional.empty());
        doThrow(new WikiPageParsingError("Parsing error")).when(wikiReader).read(link);

        // Act & Assert (Verify no exception is thrown)
        assertDoesNotThrow(() -> wikiScrapper.read(link));
    }

    @Test
    public void testRead_WhenRootPageNotFound_ThrowsWikiPageNotFoundRuntime() throws Exception {
        // Arrange
        String link = "http://example.com/page";
        when(repository.getPageBySelfLink(link)).thenReturn(Optional.empty());
        doThrow(new WikiPageNotFound("Page not found")).when(wikiReader).read(link);

        // Act & Assert
        assertThrows(WikiPageNotFoundRuntime.class, () -> wikiScrapper.read(link));
    }
}
