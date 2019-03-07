package com.grepiu.www.process.common.tools.crawler.node;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.grepiu.www.process.common.tools.crawler.domain.CineType;
import com.grepiu.www.process.common.tools.crawler.entity.Cinema;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaDetailInfo;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class CGVCinemaNode implements SeleniumExecuteNode<List<Cinema>> {

  /**
   * Selenium webDriver를 이용한 구현 로직 구현 시작시 webDriver.get(url); 종료시 webDriver.quit();
   */
  @Override
  public List<Cinema> getData(WebDriver webDriver) {
    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd"));
    webDriver.get("http://www.cgv.co.kr/reserve/show-times");

    // alert 안뜨도록 설정
    SeleniumUtils.isAlertDisable(webDriver);
    // full 스크린
    webDriver.manage().window().fullscreen();

    // return 값 초기
    List<Cinema> cinemaNodeList = Lists.newArrayList();

    // step1. 매장별 URL 정보 획득
    List<WebElement> area = webDriver.findElements(By.xpath(
        "/html/body/div[@id='cgvwrap']/div[@id='contaniner']/div[@id='contents']/div[@class='sect-common']/div[@class='favorite-wrap']/div[@class='sect-city']/ul/li"));
    area.forEach(areaDiv -> {
      Cinema cgvCinema = new Cinema();
      HashMap<String, Object> areaMovieInfo = Maps.newHashMap();
      // Set 기본값
      cgvCinema.setType(CineType.CGV.getCode());
      // Tab Click
      cgvCinema.setSido(areaDiv.findElement(By.tagName("a")).getText());
      areaDiv.click();
      SeleniumUtils.elementClick(webDriver, webDriver.findElement(By.tagName("a")));
      List<WebElement> cineBranch = webDriver.findElements(By.xpath(
          "/html/body/div[@id='cgvwrap']/div[@id='contaniner']/div[@id='contents']/div[@class='sect-common']/div[@class='favorite-wrap']/div[@class='sect-city']/ul/li[@class='on']/div[@class='area']/ul/li"));

      List<CinemaDetailInfo> movieInfo = new ArrayList<>();
      cineBranch.forEach(branch -> {
        CinemaDetailInfo detail = new CinemaDetailInfo();
        // set 상영관
        String branchText = branch.getText();
        String href = branch.findElement(By.tagName("a")).getAttribute("href");
        detail.setHref(href);
        areaMovieInfo.put(branchText, href);
      });
      cgvCinema.setMovieInfo(areaMovieInfo);
      cinemaNodeList.add(cgvCinema);
    });
    // step2. 매장 detail 정보 획득
    cinemaNodeList.stream().forEach(cinema -> {
      cinema.getMovieInfo().forEach((v, o) -> {
        String href = cinema.getMovieInfo().get(v).toString();
        webDriver.get(href);
        webDriver.navigate().refresh();

        // iframe으로 되어 있어 iframe 지정
        WebDriver iframe = webDriver.switchTo().frame("ifrm_movie_time_table");

        List<CinemaDetailInfo> moviesList = Lists.newArrayList();
        // 영화 별 구분
        iframe.findElements(By.xpath("/html/body/div[@class='showtimes-wrap']/div[@class='sect-showtimes']/ul/li")).forEach(movies -> {
          String movieName = movies.findElement(By.xpath("//div/div[@class='info-movie']/a")).getText();

          // 영화 상새 정보
          movies.findElements(By.xpath("//div/div[@class='type-hall']")).forEach(info->{
            String room = info.findElement(By.xpath("//div[@class='info-hall']/ul/li[1]")).getText() + " " + info.findElement(By.xpath("//div[@class='info-hall']/ul/li[2]")).getText();
            String seat = info.findElement(By.xpath("//div[@class='info-hall']/ul/li[3]")).getText();

            // 시간
            info.findElements(By.xpath("//div[@class='info-timetable']/ul/li")).forEach(t->{
              CinemaDetailInfo cinemaDetailInfo = new CinemaDetailInfo();
              cinemaDetailInfo.setMovieName(movieName);
              cinemaDetailInfo.setRoom(room);
              cinemaDetailInfo.setSeat(seat);
              cinemaDetailInfo.setTime(t.getText());
              log.info("영화관 : {}", v);
              log.info("name : {}", movieName);
              log.info("room : {}", room);
              log.info("seat : {}", seat);
              log.info("time : {}", t.getText());
              moviesList.add(cinemaDetailInfo);
            });
          });
        });
        cinema.getMovieInfo().put(v, moviesList);
      });
    });
    return cinemaNodeList;
  }
}
