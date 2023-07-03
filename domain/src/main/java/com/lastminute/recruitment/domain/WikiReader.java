package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.domain.error.WikiPageParsingError;

public interface WikiReader {

    WikiPage read(String link) throws WikiPageNotFound, WikiPageParsingError;

}
