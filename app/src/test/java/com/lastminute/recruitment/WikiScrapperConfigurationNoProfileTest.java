package com.lastminute.recruitment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiPageRepository;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.persistence.InMemoryWikiPageRepository;
import com.lastminute.recruitment.reader.HtmlWikiReader;
import com.lastminute.recruitment.client.HtmlWikiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("html")
public class WikiScrapperConfigurationNoProfileTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WikiReader htmlWikiReader;

    @Autowired
    private WikiClient htmlWikiClient;

    @Autowired
    private WikiPageRepository wikiPageRepository;

    @Autowired
    private WikiScrapper wikiScrapper;

    @Test
    public void testHtmlWikiReaderBean() {
        assertNotNull(htmlWikiReader);
        assertTrue(htmlWikiReader instanceof HtmlWikiReader);
    }

    @Test
    public void testObjectMapperBean() {
        assertNotNull(objectMapper);
    }

    @Test
    public void testHtmlWikiClientBean() {
        assertNotNull(htmlWikiClient);
        assertTrue(htmlWikiClient instanceof HtmlWikiClient);
    }

    @Test
    public void testWikiPageRepositoryBean() {
        assertNotNull(wikiPageRepository);
        assertTrue(wikiPageRepository instanceof InMemoryWikiPageRepository);
    }

    @Test
    public void testWikiScrapperBean() {
        assertNotNull(wikiScrapper);
    }
}
