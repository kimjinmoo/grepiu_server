package com.iuom.springboot.common.crawler.node;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.domain.CinemaDetailInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 * LotteCinema node
 *
 */
@Slf4j
public class LotteCinemaNode extends BaseNode<Cinema> {

    @Override
    public List<Cinema> executeLogic() {
        // 크롬 초기화
        initChrome("http://www.lottecinema.co.kr/LCHS/Contents/ticketing/ticketing.aspx");
        // return 데이터 타입 Set
        List<Cinema> cinemaNodeList = Lists.newArrayList();
        getDriver().findElements(By.cssSelector("[class^=area00]")).forEach(v->{
            Cinema lotteCinema = new Cinema();
            HashMap<String, Object> areaMovieInfo = Maps.newHashMap();
            // Set 시도
            lotteCinema.setSido(v.findElement(By.className("area_zone")).getText());
            String processAria = v.findElement(By.className("area_zone")).getText();
            // 지역 클릭
            elementClick(v.findElement(By.cssSelector(".area_zone h4")));
            List<WebElement> areasList = findElements(v, By.cssSelector(".area_cont ul li"));
            areasList.forEach(subV->{
                // set 상영관
                String area = subV.getText();
                elementClick(subV.findElement(By.cssSelector("a")));

                WebElement times = getDriver().findElement(By.className("time_box"));
                List<WebElement> movies = times.findElements(By.className("time_line"));
                List<CinemaDetailInfo> movieInfo = new ArrayList<>();

                movies.forEach(movTimes->{
                    // set 영화명
                    CinemaDetailInfo movie = new CinemaDetailInfo();

                    movie.setMovieName(movTimes.findElement(By.cssSelector("dt")).getText());;
                    List<WebElement> regTime = findElements(movTimes, By.cssSelector(".theater_time li"));
                    regTime.forEach(t->{
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
                elementClick(subV.findElement(By.cssSelector("a")));
            });
            cinemaNodeList.add(lotteCinema);
        });
        quit();
        return cinemaNodeList;
    }
}
