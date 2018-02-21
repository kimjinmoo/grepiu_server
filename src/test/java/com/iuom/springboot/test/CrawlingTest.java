package com.iuom.springboot.test;

import com.google.gson.Gson;
import com.iuom.springboot.common.crawler.domain.LotteCinema;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class CrawlingTest {

//    @Test
    public void lotteMovie() throws Exception {
        System.setProperty("webdriver.chrome.driver", "c:\\workspace\\sw\\selenium\\chromedriver.exe");
//        File file = new File("c:/workspace/sw/selenium/IEDriverServer.exe");
//        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.lottecinema.co.kr/LCHS/Contents/Cinema/Cinema-Detail.aspx?divisionCode=1&detailDivisionCode=101&cinemaID=2011");
        Thread.sleep(5000);  // Let the user actually see something!
        List<WebElement> movieName = driver.findElements(By.className("time_line"));
        movieName.forEach(v->{
            System.out.println("title : " + v.getText());
            List<WebElement> times = v.findElements(By.className("clock"));
            times.forEach(t->{
                System.out.println("time : " + t.getText());
            });
        });
        Thread.sleep(5000);  // Let the user actually see something!
        driver.quit();
    }

    /**
     *
     * 예매 사이트 데이터 가져오기
     * @throws Exception
     */
    @Test
    public void reg() throws Exception {
        System.setProperty("webdriver.chrome.driver", "c:\\workspace\\sw\\selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        List<LotteCinema> data = new ArrayList<>();

        //http://www.lottecinema.co.kr/LCHS/Contents/ticketing/ticketing.aspx
        driver.get("http://www.lottecinema.co.kr/LCHS/Contents/ticketing/ticketing.aspx");
        Thread.sleep(2000);
        List<WebElement> areas = driver.findElements(By.cssSelector("[class^=area00]"));
        areas.forEach(v->{
            LotteCinema lotteCinema = new LotteCinema();
            // Set 시도
            lotteCinema.setSido(v.findElement(By.className("area_zone")).getText());
            log.debug("지역 : {}", v.findElement(By.className("area_zone")).getText());
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(v.findElement(By.cssSelector(".area_zone h4"))).click().perform();
                Thread.sleep(2000);
                List<WebElement> areasList = v.findElements(By.cssSelector(".area_cont ul li"));
                areasList.forEach(subV->{
                    try {
                        // set 상영관
                        lotteCinema.setArea(subV.getText());
                        actions.moveToElement(subV.findElement(By.cssSelector("a"))).click().perform();
                        Thread.sleep(1500);
                        WebElement times = driver.findElement(By.className("time_box"));
                        List<WebElement> movies = times.findElements(By.className("time_line"));
                        movies.forEach(movTimes->{
                            // set 영화명
                            LotteCinema.Movie movie = lotteCinema.new Movie();
                            List<LotteCinema.Movie> movieInfo = new ArrayList<>();
                            movie.setMovieName(movTimes.findElement(By.cssSelector("dt")).getText());;
                            List<WebElement> regTime = movTimes.findElements(By.cssSelector(".theater_time li"));
                            regTime.forEach(t->{
                                // 상영관
                                movie.setRoom(t.findElements(By.tagName("span")).get(0).getText());
                                // 시간
                                movie.setTime(t.findElements(By.tagName("span")).get(1).getText());
                                // 좌석
                                movie.setSeat(t.findElements(By.tagName("span")).get(2).getText());
                                movieInfo.add(movie);
                            });
                            lotteCinema.setMovieInfo(movieInfo);
                        });
                        actions.moveToElement(subV.findElement(By.cssSelector("a"))).click().perform();
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                data.add(lotteCinema);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Gson gson = new Gson();
        log.debug("JSON : {}", gson.toJson(data));
        driver.quit();
    }
}
