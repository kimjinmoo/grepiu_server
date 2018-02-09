package com.iuom.springboot.process.sample.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="crawling")
@Data
public class Crawler{
    private String attr;
    private String contents;

    public Crawler(String attr, String contents) {
        this.attr = attr;
        this.contents = contents;
    }
}
