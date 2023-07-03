package com.lastminute.recruitment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiPageRepository;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.persistence.InMemoryWikiPageRepository;
import com.lastminute.recruitment.reader.HtmlWikiReader;
import com.lastminute.recruitment.reader.JsonWikiReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class WikiScrapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Profile("!json") // Instead of using profiles I would suggest to use feature flags
    public WikiReader htmlWikiReader(@Qualifier("htmlWikiClient") WikiClient client) {
        return new HtmlWikiReader(client);
    }

    @Bean
    @Profile("json") // Instead of using profiles I would suggest to use feature flags
    public WikiReader jsonWikiReader(@Qualifier("jsonWikiClient") WikiClient client, ObjectMapper objectMapper) {
        return new JsonWikiReader(client, objectMapper);
    }

    @Bean
    public WikiClient htmlWikiClient() {
        return new HtmlWikiClient();
    }

    @Bean
    public WikiClient jsonWikiClient() {
        return new JsonWikiClient();
    }

    @Bean
    public WikiPageRepository wikiPageRepository() {
        return new InMemoryWikiPageRepository();
    }

    @Bean
    public WikiScrapper wikiScrapper(WikiPageRepository wikiPageRepository, WikiReader wikiReader) {
        return new WikiScrapper(wikiReader, wikiPageRepository);
    }
}
