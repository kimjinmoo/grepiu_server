package com.iuom.springboot.common.crawler.node;

import com.google.common.collect.Lists;
import com.iuom.springboot.common.crawler.domain.CinemaLocation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

/**
 *
 * 시네마 주소를 크롤링한다.
 *
 */
@Slf4j
public class LotteCinemaLocationNode extends BaseNode<CinemaLocation> {

  @Override
  public List<CinemaLocation> executeLogic() {
    // 크롬 초기화
    initChrome("http://www.lottecinema.co.kr/LCHS/Contents/Cinema/charlotte-special-cinema.aspx");
    // return 데이터 타입 Set
    List<CinemaLocation> cinemaNodeList = Lists.newArrayList();
    getDriver().findElements(By.cssSelector("[class^=depth]")).forEach(v->{
      v.findElements(By.cssSelector("ul")).forEach(li -> {
        elementClick(li.findElement(By.cssSelector("li")));
        log.info("d : {}", li.getText());
      });
    });
    quit();
    return cinemaNodeList;
  }
}
