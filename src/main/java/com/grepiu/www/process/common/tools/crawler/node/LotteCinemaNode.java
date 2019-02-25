package com.grepiu.www.process.common.tools.crawler.node;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaDetailInfo;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * LotteCinema 크롤링 로직
 *
 */
@Slf4j
public class LotteCinemaNode implements SeleniumExecuteNode<List<Cinema>> {

    @Override
    public List<Cinema> execute(WebDriver webDriver) {
        // URL을 크롤링을 시작한다.
        webDriver.get("http://www.lottecinema.co.kr/LCHS/Contents/ticketing/ticketing.aspx");
        // return 데이터 타입 Set
        List<Cinema> cinemaNodeList = Lists.newArrayList();
        webDriver.findElements(By.cssSelector("[class^=area00]")).forEach(v->{
            Cinema lotteCinema = new Cinema();
            HashMap<String, Object> areaMovieInfo = Maps.newHashMap();
            // Set 시도
            lotteCinema.setSido(v.findElement(By.className("area_zone")).getText());
            String processAria = v.findElement(By.className("area_zone")).getText();
            // 지역 클릭
            SeleniumUtils.elementClick(webDriver, v.findElement(By.cssSelector(".area_zone h4")));
            List<WebElement> areasList = SeleniumUtils.findElements(v, By.cssSelector(".area_cont ul li"));
            areasList.forEach(subV->{
                // set 상영관
                String area = subV.getText();
                SeleniumUtils.elementClick(webDriver, subV.findElement(By.cssSelector("a")));

                WebElement times = webDriver.findElement(By.className("time_box"));
                List<WebElement> movies = times.findElements(By.className("time_line"));
                List<CinemaDetailInfo> movieInfo = new ArrayList<>();

                movies.forEach(movTimes->{
                    // set 영화명
                    String movieName = movTimes.findElement(By.cssSelector("dt")).getText();
                    List<WebElement> regTime = SeleniumUtils.findElements(movTimes, By.cssSelector(".theater_time li"));
                    regTime.forEach(t->{
                        CinemaDetailInfo movie = new CinemaDetailInfo();
                        // 영화명
                        movie.setMovieName(movieName);;
                        // 상영관
                        movie.setRoom(t.findElements(By.tagName("span")).get(0).getText());
                        // 시간
                        movie.setTime(t.findElements(By.tagName("span")).get(1).getText());
                        // 좌석
                        movie.setSeat(t.findElements(By.tagName("span")).get(2).findElement(By.xpath("//em")).getText());
                        movieInfo.add(movie);
                    });
                });
                areaMovieInfo.put(area, movieInfo);
                lotteCinema.setMovieInfo(areaMovieInfo);
                SeleniumUtils.elementClick(webDriver, subV.findElement(By.cssSelector("a")));
            });
            cinemaNodeList.add(lotteCinema);
        });
        webDriver.quit();

        return cinemaNodeList;
    }
}
