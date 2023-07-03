package com.lastminute.recruitment.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record WikiPage(String title, String content, String selfLink, List<String> links) {

}
