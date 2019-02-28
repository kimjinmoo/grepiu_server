package com.grepiu.www.process.common.tools.crawler.node;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaDetailInfo;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
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
    webDriver.get("http://m.cgv.co.kr/Schedule/?tc=0001&t=T&ymd="+now+"&src=");

    // alert 안뜨도록 설정
    SeleniumUtils.isAlertDisable(webDriver);
    // full 스크린
    webDriver.manage().window().fullscreen();

    // return 값 초기
    List<Cinema> cinemaNodeList = Lists.newArrayList();

    // 모바일용 극장 버튼 클릭
    webDriver.findElement(By.xpath("/html/body/section/div/section/a")).click();
    // 모든 극장 리스트를 가져온다.
    List<WebElement> ul = webDriver.findElements(By.xpath("/html/body/section/div/section/ul/li[not(contains(@class,'on'))]"));
    HashMap<String, Object> areaMovieInfo = Maps.newHashMap();
    Cinema cinema = new Cinema();

    for(WebElement v : ul) {
      if(v.getAttribute("class").equals("list_wrap")) {
        cinema.setType("cgv");
        if(areaMovieInfo != null && areaMovieInfo.size()>0) {
          cinemaNodeList.add(cinema);
          // 시네마 초기화
          cinema = new Cinema();
        }
        cinema.setSido(v.findElement(By.className("txt")).getText());
      } else {
        areaMovieInfo = Maps.newHashMap();
        for(WebElement a : v.findElement(By.className("arealist_wrap")).findElements(By.tagName("a"))) {
          areaMovieInfo.put(a.getAttribute("title"), Lists.newArrayList());
        }
        cinema.setMovieInfo(areaMovieInfo);
      }
    }
    // 영화 데이터 크롤링 시작
    cinemaNodeList.stream().forEach(v->{
      v.getMovieInfo().forEach((s, o) -> {
        webDriver.findElement(By.xpath("/html/body/section/div/section/a")).click();
        SeleniumUtils.elementClick(webDriver, webDriver.findElement(By.xpath("/html/body/section/div/section/ul/li/ul/li/a[@title='"+s+"']")));

        List<CinemaDetailInfo> cinemaDetailLists = Lists.newArrayList();
        // alert 처리
        SeleniumUtils.ifShowAlertAccept(webDriver);
        List<WebElement> webSections = webDriver.findElements(By.xpath("/html/body/section/div/div[@id='divContent']/section"));
        webSections.stream().forEach(info->{
          // Set Movie Info
          String movieName = info.findElement(By.tagName("h3")).getText();
          String room = info.findElement(By.xpath("//div/p/strong/span[3]")).getText();

          info.findElements(By.xpath("//div[@class='time']/ul/li")).stream().forEach(time->{
            CinemaDetailInfo cinemaDetailInfo = new CinemaDetailInfo();
            cinemaDetailInfo.setMovieName(movieName);
            cinemaDetailInfo.setRoom(room);
            cinemaDetailInfo.setTime(time.getText());
            cinemaDetailLists.add(cinemaDetailInfo);
          });
        });
        v.getMovieInfo().put(s, cinemaDetailLists);
      });
    });
    webDriver.quit();
    return cinemaNodeList;
  }
}
