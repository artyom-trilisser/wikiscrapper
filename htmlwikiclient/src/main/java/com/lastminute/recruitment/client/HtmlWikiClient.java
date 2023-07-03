package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.net.URL;

import java.util.Optional;

public class HtmlWikiClient implements WikiClient {

    @Override
    public String read(String link) throws WikiPageNotFound {
        String name = link.replace("\"", "")
                .replace("http://wikiscrapper.test/", "/wikiscrapper/") + ".html";
        return Optional.ofNullable(getClass().getResource(name))
                .map(URL::getFile)
                .orElseThrow(WikiPageNotFound::new);
    }
}
