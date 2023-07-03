package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;

public interface WikiClient {
    String read(String link) throws WikiPageNotFound;
}
