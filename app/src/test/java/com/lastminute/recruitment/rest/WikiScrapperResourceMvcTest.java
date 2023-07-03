package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiScrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class WikiScrapperResourceMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WikiScrapper wikiScrapper;

    @Test
    public void testScrapWikipedia_ValidLink_ReturnsNoContent() throws Exception {
        // Arrange
        String validLink = "http://wikiscrapper.test/site1";

        doNothing().when(wikiScrapper).read(validLink);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/wiki/scrap")
                        .content(validLink)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetLinks_ReturnsWikiPages() throws Exception {
        // Arrange
        List<WikiPage> wikiPages = new ArrayList<>();
        wikiPages.add(new WikiPage("Page1", "Content1", "http://wikiscrapper.test/site1", new ArrayList<>()));
        wikiPages.add(new WikiPage("Page2", "Content2", "http://wikiscrapper.test/site2", new ArrayList<>()));

        // Set up the mock behavior for the WikiScrapper
        when(wikiScrapper.getLinks()).thenReturn(wikiPages);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/wiki/links"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Page1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Page2"));
    }
}

