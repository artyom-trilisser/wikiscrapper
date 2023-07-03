package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.domain.error.WikiPageNotFoundRuntime;
import com.lastminute.recruitment.domain.error.WikiPageParsingError;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class WikiScrapper {

    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    public Collection<WikiPage> getLinks() {
        return repository.getPages();
    }

    public void read(String link) {
        readWithExHandling(link, false);
    }

    private void readWithExHandling(String link, boolean toHandle) {
        if (repository.getPageBySelfLink(link).isEmpty()) {

            try {
                WikiPage page = wikiReader.read(link);
                repository.save(page);

                for (var lnk : page.links()) {
                    readWithExHandling(lnk, true); // TODO consider multi-threading
                }
            } catch (WikiPageParsingError ex) {
                // No needs to stop whole process because of parsing error.
                // It is better just to log that some parsing went wrong
                ex.printStackTrace();
            } catch (WikiPageNotFound ex) {
                // My proposition is to throw 404 error only in case the root page is not found
                if (toHandle) {
                    ex.printStackTrace();
                } else throw new WikiPageNotFoundRuntime(ex.getMessage());
            }
        }
    }
}
