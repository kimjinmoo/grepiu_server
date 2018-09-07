package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.service.LabService;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.utils.DistanceCalculator;
import com.grepiu.www.process.grepiu.dao.LotteCineDBRepository;
import com.grepiu.www.process.grepiu.dao.LotteCineLocalRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * Lab API
 * /grepiu/lab/root 는 권한 체크함
 *
 */
@ApiOperation("GrepIU Lab API")
@RestController
@RequestMapping("/grepiu/lab")
@Slf4j
public class LabRestController {

    @Autowired
    private LabService labService;

    @Autowired
    private LotteCineDBRepository lotteCineDBRepository;

    @Autowired
    private LotteCineLocalRepository lotteCineLocalRepository;

    @ApiOperation(value = "영화관 정보 등록 수동처리")
    @GetMapping("/root/crawler/cine/saveCinemaLocationByManual")
    public ResponseEntity<Object> saveCinemaLocationByManual() {
        labService.collectionCinemaLocation();
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "영화 상영관 정보 수동 처리")
    @GetMapping("/root/crawler/cine/saveCinemaInfoByManual")
    public ResponseEntity<Object> saveCinemaInfoByManual() {
        // 비동기 처리
        labService.collectionCinemaMovieInfo();
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
    }

    @ApiOperation(value = "상영 영화 크롤링 데이터 리스트")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/screen")
    public ResponseEntity<Object> findCine() {
        return new ResponseEntity<Object>(lotteCineDBRepository.findAllBy(), HttpStatus.OK);
    }

    @ApiOperation(value = "롯데 시네마 상영 영화 크롤링 데이터 리스트")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/screen/{storeName}")
    public ResponseEntity<Object> findCineByStoreName(@PathVariable("storeName") String storeName) {
        Optional<Cinema> o = lotteCineDBRepository.findAllBy().parallelStream()
                .filter(v -> v.getMovieInfo().containsKey(storeName)).findFirst();
        o.orElse(new Cinema());

        return new ResponseEntity<>(o.isPresent()?o.get().getMovieInfo().get(storeName):null, HttpStatus.OK);
    }

    @ApiOperation(value = "시네마 매장 정보")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/locale")
    public ResponseEntity<Object> getCineLocale() {
        return new ResponseEntity<Object>(lotteCineLocalRepository.findAllBy(), HttpStatus.OK);
    }

    @ApiOperation(value = "인접한 영화관 찾기")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/near")
    public ResponseEntity<Object> findNearCinema(
            @ApiParam(value = "위도") @RequestParam("lat") Double lat,
            @ApiParam(value = "경도") @RequestParam("lng") Double lng,
            @ApiParam(value = "키로미터") @RequestParam("distance") Integer number) {
        return new ResponseEntity<Object>(lotteCineLocalRepository
                .findByLocationNear(new Point(Double.valueOf(lng), Double.valueOf(lat)),
                        new Distance(number.longValue(), Metrics.KILOMETERS)).stream().map(v -> {
                    double locationLat = v.getLocation().getX();
                    double locationLng = v.getLocation().getY();
                    // 거리 계산
                    v.setDistance(DistanceCalculator
                            .distance(lat, lng, locationLat, locationLng,
                                    Metrics.KILOMETERS));
                    return v;
                }).collect(Collectors.toList()), HttpStatus.OK);
    }
}
