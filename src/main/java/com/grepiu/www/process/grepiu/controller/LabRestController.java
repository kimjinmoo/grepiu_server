package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.domain.CineLocalFilter;
import com.grepiu.www.process.grepiu.domain.form.CinemaInfoOptionForm;
import com.grepiu.www.process.grepiu.entity.RealtimeVote;
import com.grepiu.www.process.grepiu.service.LabService;
import com.grepiu.www.process.common.tools.crawler.entity.Cinema;
import com.grepiu.www.process.common.utils.DistanceCalculator;
import com.grepiu.www.process.grepiu.dao.CineDBRepository;
import com.grepiu.www.process.grepiu.dao.CineLocalRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * Lab API
 * /grepiu/lab/root 는 권한 체크함
 *
 */
@Api("GrepIU Lab API")
@RestController
@RequestMapping("/grepiu/lab")
@Slf4j
public class LabRestController {

    private final LabService labService;

    private final CineDBRepository cineDBRepository;

    private final CineLocalRepository cineLocalRepository;

    public LabRestController(LabService labService,
        CineDBRepository cineDBRepository, CineLocalRepository cineLocalRepository) {
        this.labService = labService;
        this.cineDBRepository = cineDBRepository;
        this.cineLocalRepository = cineLocalRepository;
    }

    @ApiOperation(value = "영화관 위치 정보 수동")
    @GetMapping("/root/crawler/cine/saveCinemaLocationByManual")
    public ResponseEntity<Object> saveCinemaLocationByManual() {
        labService.collectCinemaLocation();
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "롯데 영화 상영 정보 크롤링 수동 처리")
    @GetMapping("/root/crawler/cine/saveCinemaInfoByManual")
    public ResponseEntity<Object> saveCinemaInfoByManual(@ModelAttribute CinemaInfoOptionForm cinemaInfoOptionForm) {
        // 비동기 처리
        labService.collectLotteCinemaMovieInfo(cinemaInfoOptionForm);
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
    }

    @ApiOperation(value = "CGV 영화 상영 정보 크롤링 수동 처리")
    @GetMapping("/root/crawler/cine/cgv/saveCinemaInfoByManual")
    public ResponseEntity<Object> saveCgvCinemaInfoByManual(@ModelAttribute CinemaInfoOptionForm cinemaInfoOptionForm) {
        // 비동기 처리
        labService.collectCgvCinemaMovieInfo();
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
    }

    @ApiOperation(value = "영화관 상영정보 크롤링 데이터 리스트")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/screen")
    public ResponseEntity<Object> findCine() {
        return new ResponseEntity<Object>(cineDBRepository.findAllBy(), HttpStatus.OK);
    }

    @ApiOperation(value = "영화관 크롤링 데이터 리스트")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/screen/{storeName}")
    public ResponseEntity<Object> findCineByStoreName(@PathVariable("storeName") String storeName) {
        Optional<Cinema> o = cineDBRepository.findAllBy().parallelStream()
                .filter(v -> v.getMovieInfo().containsKey(storeName)).findFirst();
        o.orElse(new Cinema());

        return new ResponseEntity<>(o.isPresent()?o.get().getMovieInfo().get(storeName):null, HttpStatus.OK);
    }

    @ApiOperation(value = "시네마 매장 정보")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/locale")
    public ResponseEntity<Object> getCineLocale(@ModelAttribute @Valid CineLocalFilter cineFilter) {
        return new ResponseEntity<Object>(labService.findCineLocale(cineFilter), HttpStatus.OK);
    }

    @ApiOperation(value = "인접한 영화관 찾기")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/crawler/cine/near")
    public ResponseEntity<Object> findNearCinema(
            @ApiParam(value = "위도") @RequestParam("lat") Double lat,
            @ApiParam(value = "경도") @RequestParam("lng") Double lng,
            @ApiParam(value = "키로미터") @RequestParam("distance") Integer number) {
        return new ResponseEntity<Object>(cineLocalRepository
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

    @ApiOperation("투표 저장")
    @ApiResponse(code = 200, message = "저장성공")
    @PostMapping("/vote")
    public ResponseEntity<Object> saveVote(
        @RequestBody RealtimeVote realtimeVote
    ) {
        return ResponseEntity.ok(labService.saveRealtimeVote(realtimeVote));
    }

    @ApiOperation("투표 전체 조회")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/vote")
    public ResponseEntity<Object> getVote() {
        return ResponseEntity.ok(labService.getRealtimeVotes());
    }

    @ApiOperation("투표 ID로 조회")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/vote/{id}")
    public ResponseEntity<Object> getVoteById(@PathVariable("id") String id) throws Exception {
        return ResponseEntity.ok(labService.getRealtimeVoteById(id));
    }

    @ApiOperation("투표 ID 삭제")
    @ApiResponse(code = 200, message = "삭제성공")
    @DeleteMapping("/vote/{id}")
    public ResponseEntity<Object> deleteVoteById(@PathVariable("id") String id) throws Exception {
        // delete
        labService.deleteRealtimeVote(id);
        // 성공
        return ResponseEntity.ok().build();
    }

}
