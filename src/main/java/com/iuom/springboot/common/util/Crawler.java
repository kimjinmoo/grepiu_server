package com.iuom.springboot.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    private HashSet<String> links;

    public Crawler() {
        this.links = new HashSet<>();
    }

    public void getPageLinks(String URL)  {
        //4. Check if you have already crawled the URLs
        //(we are intentionally not checking for duplicate content in this example)
        if (!links.contains(URL)) {
            try {
                //4. (i) If not add it to the index
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get();
                Elements link = document.select("a[href]");
                Elements media = document.select("src");
                Elements imports = document.select("link[href]");

                //5. For each extracted URL... go back to Step 4.
                link.forEach(v->{
                    String url = v.attr("abs:href");
                    System.out.println("abs:href : " + url);
                    System.out.println("abs:href : " + v.text());
                    if(url.length() > 0)
                        getPageLinks(v.attr("abs:href"));
                });
                media.forEach(v->{
                    if(v.tagName().equals("img")) {
                        System.out.println("img tag: " + v.tagName());
                        System.out.println("img src: " + v.attr("abs:src"));
                        System.out.println("img width: " + v.attr("width"));
                        System.out.println("img height: " + v.attr("height"));
                    } else {
                        System.out.println("img tag: " + v.tagName());
                        System.out.println("img src: " + v.attr("abs:src"));
                    }

                });
                imports.forEach(v->{
                    System.out.println("img tag: " + v.tagName());
                });
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String...args) {
        Crawler cw = new Crawler();
        cw.getPageLinks("http://www.lottecinema.co.kr/LCHS/index.aspx");
    }
}
