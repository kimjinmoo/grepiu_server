package com.iuom.springboot.common.crawler;

import com.iuom.springboot.process.sample.domain.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 *
 * 크롤러 유틸
 *
 */
@Slf4j
public class CrawlerUtils implements Executor{

    private ArrayList<Observer> observers; //처리내역
    private String attr;                    // http 속성
    private String contents;               // 내용
    private String searchBaseUrl;         // search베이스 URL

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        observers.forEach(v->{
            v.update(new Crawler(attr,contents));
        });
    }

    public void sendData(String attr, String contents) {
        this.attr = attr;
        this.contents = contents;
        notifyObserver();
    }

    public String getAttr() {
        return attr;
    }

    public String getContents() {
        return contents;
    }

    private HashSet<String> links;

    public CrawlerUtils() {
        this.observers = new ArrayList<>();
        this.links = new HashSet<>();
    }

    /**
     *
     * URL 체크한다.
     *
     * @param url
     * @return
     */
    public boolean isRunable(String url){
        try{
            URL testUrl = new URL(url);
            testUrl.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getHost(String url) {
        try{
            URL testUrl = new URL(url);
            testUrl.toURI();
            return testUrl.getHost();
        } catch (Exception e) {
            return "";
        }
    }

    @Async
    public void run(String URL)  {
        if(isRunable(URL)) {
            // searchBaseUrl 검색(타른 Url 넘어가는것을 방지한다.
            if(!Optional.ofNullable(searchBaseUrl).isPresent() || searchBaseUrl.equals("")) {
                searchBaseUrl = getHost(URL);
            }
            System.out.println("searchBaseUrl : " + searchBaseUrl);
            // 중복 link 제거
            if (!links.contains(URL)) {
                try {
                    links.add(URL);

                    //2. 각 속성별 정보를 가져온다.
                    Document document = Jsoup.connect(URL).get();
                    System.out.println(document.outerHtml());
                    Elements link = document.select("a[href]");
                    Elements media = document.select("src");
                    Elements imports = document.select("link[href]");
                    Elements spans = document.select("span.messageBody");

                    //step1. span 데이터를 가져온다.
                    spans.forEach(v->{
                        this.sendData("span", v.text());
                    });
                    //step2. link 정보
                    link.forEach(v->{
                        String url = v.attr("abs:href");
                        System.out.println("abs:href : " + url);
                        System.out.println("abs:href : " + v.text());
                        this.sendData("abs:href", url+"/"+v.text());
                        if(url.length() > 0 && url.contains(searchBaseUrl)){
                            System.out.println("INNER =====> " + url);
                            run(v.attr("abs:href"));
                        }
                    });
                    //step3 images
                    media.forEach(v->{
                        if(v.tagName().equals("img")) {
                            this.sendData("image", v.tagName()+"/"+v.attr("abs:src"));
                            System.out.println("img width: " + v.attr("width"));
                            System.out.println("img height: " + v.attr("height"));
                        } else {
                            this.sendData("etc", v.tagName()+"/"+v.attr("abs:src"));
                            System.out.println("img tag: " + v.tagName());
                            System.out.println("img src: " + v.attr("abs:src"));
                        }

                    });
                    //step4. import
                    imports.forEach(v->{
                        this.sendData("abs:href", v.attr("link[href]")+"/"+v.text());
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("For '" + URL + "': " + e.getMessage());
                }
            }
        }
    }

    public static void main(String...args) {
        try {
            CrawlerUtils cu = new CrawlerUtils();
            cu.run("http://www.lottecinema.co.kr/LCHS/Contents/Cinema/Cinema-Detail.aspx?divisionCode=1&detailDivisionCode=101&cinemaID=2011");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
