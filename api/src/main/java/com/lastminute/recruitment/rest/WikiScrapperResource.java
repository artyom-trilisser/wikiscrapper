package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiScrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/wiki")
@RestController
@RequiredArgsConstructor
public class WikiScrapperResource {

    private final WikiScrapper scrapper;

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody String link) {
        System.out.println("Hello Scrap -> " + link);

        scrapper.read(link);
    }

    @GetMapping("/links")
    public Collection<WikiPage> getLinks() {
        return scrapper.getLinks();
    }

}
