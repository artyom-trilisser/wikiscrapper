package com.lastminute.recruitment.domain;

import java.util.Collection;
import java.util.Optional;

public interface WikiPageRepository {

    void save(WikiPage wikiPage);

    Optional<WikiPage> getPageBySelfLink(String link);

    Collection<WikiPage> getPages();
}
