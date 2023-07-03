package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.domain.error.WikiPageParsingError;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HtmlWikiReader implements WikiReader {

    private final WikiClient wikiClient;

    @Override
    public WikiPage read(String link) throws WikiPageNotFound {

        Document document = readFromDisk(link);

        Element titleElement = document.selectFirst(".title");
        String title = titleElement != null ? titleElement.text() : "";

        Element selfLinkElement = document.selectFirst("meta");
        String selfLink = selfLinkElement != null ? selfLinkElement.attr("selfLink") : "";

        Element contentElement = document.selectFirst(".content");
        String content = contentElement != null ? contentElement.text() : "";

        List<String> links = new ArrayList<>();
        Element linksContainer = document.selectFirst(".links");
        if (linksContainer != null) {
            Elements linksElements = linksContainer.select("a");
            linksElements.forEach(el -> links.add(el.attr("href")));
        }

        return WikiPage.builder()
                .title(title)
                .content(content)
                .selfLink(selfLink)
                .links(links)
                .build();
    }

    private Document readFromDisk(String link) {
        String fileName = wikiClient.read(link);

        try {
            return Jsoup.parse(new File(fileName), "UTF-8");
        } catch (IOException e) {
            throw new WikiPageNotFound();
        }
    }
}
