package com.lastminute.recruitment;

import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.reader.JsonWikiReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("json")
public class WikiScrapperConfigurationActiveJsonTest {

    @Autowired
    private WikiReader jsonWikiReader;

    @Test
    public void testJsonWikiReaderBean() {
        assertNotNull(jsonWikiReader);
        assertTrue(jsonWikiReader instanceof JsonWikiReader);
    }
}
