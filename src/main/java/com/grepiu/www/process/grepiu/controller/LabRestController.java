package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.common.tools.crawler.entity.Cinema;
import com.grepiu.www.process.common.utils.DistanceCalculator;
import com.grepiu.www.process.grepiu.dao.CineDBRepository;
import com.grepiu.www.process.grepiu.dao.CineLocalRepository;
import com.grepiu.www.process.grepiu.domain.CineLocalFilter;
import com.grepiu.www.process.grepiu.domain.form.CinemaInfoOptionForm;
import com.grepiu.www.process.grepiu.entity.RealtimeVote;
import com.grepiu.www.process.grepiu.service.LabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Lab API
 * /grepiu/lab/root 는 권한 체크함
 *
 */
//@Api("GrepIU Lab API")
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

    @Operation(description = "영화관 위치 정보 수동")
    @GetMapping("/root/crawler/cine/saveCinemaLocationByManual")
    public ResponseEntity<Object> saveCinemaLocationByManual() {
        labService.collectCinemaLocation();
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.", HttpStatus.OK);
    }

    @Operation(description = "롯데 영화 상영 정보 크롤링 수동 처리")
    @GetMapping("/root/crawler/cine/saveCinemaInfoByManual")
    public ResponseEntity<Object> saveCinemaInfoByManual(@ModelAttribute CinemaInfoOptionForm cinemaInfoOptionForm) {
        // 비동기 처리
        labService.collectLotteCinemaMovieInfo(cinemaInfoOptionForm);
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
    }

    @Operation(description = "CGV 영화 상영 정보 크롤링 수동 처리")
    @GetMapping("/root/crawler/cine/cgv/saveCinemaInfoByManual")
    public ResponseEntity<Object> saveCgvCinemaInfoByManual(@ModelAttribute CinemaInfoOptionForm cinemaInfoOptionForm) {
        // 비동기 처리
        labService.collectCgvCinemaMovieInfo();
        return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
    }

    @Operation(description = "영화관 상영정보 크롤링 데이터 리스트")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/crawler/cine/screen")
    public ResponseEntity<Object> findCine() {
        return new ResponseEntity<Object>(cineDBRepository.findAllBy(), HttpStatus.OK);
    }

    @Operation(description = "영화관 크롤링 데이터 리스트")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/crawler/cine/screen/{storeName}")
    public ResponseEntity<Object> findCineByStoreName(@PathVariable("storeName") String storeName) {
        Optional<Cinema> o = cineDBRepository.findAllBy().parallelStream()
                .filter(v -> v.getMovieInfo().containsKey(storeName)).findFirst();
        o.orElse(new Cinema());

        return new ResponseEntity<>(o.isPresent()?o.get().getMovieInfo().get(storeName):null, HttpStatus.OK);
    }

    @Operation(description = "시네마 매장 정보")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/crawler/cine/locale")
    public ResponseEntity<Object> getCineLocale(@ModelAttribute @Valid CineLocalFilter cineFilter) {
        return new ResponseEntity<Object>(labService.findCineLocale(cineFilter), HttpStatus.OK);
    }

    @Operation(description = "인접한 영화관 찾기")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/crawler/cine/near")
    public ResponseEntity<Object> findNearCinema(
            @Parameter(description = "위도") @RequestParam("lat") Double lat,
            @Parameter(description = "경도") @RequestParam("lng") Double lng,
            @Parameter(description = "키로미터") @RequestParam("distance") Integer number) {
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

    @Operation(description = "투표 저장")
    @ApiResponse(responseCode = "200", description = "저장성공")
    @PostMapping("/vote")
    public ResponseEntity<Object> saveVote(
        @RequestBody RealtimeVote realtimeVote
    ) {
        return ResponseEntity.ok(labService.saveRealtimeVote(realtimeVote));
    }

    @Operation(description = "투표 전체 조회")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/vote")
    public ResponseEntity<Object> getVote() {
        return ResponseEntity.ok(labService.getRealtimeVotes());
    }

    @Operation(description = "투표 ID로 조회")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/vote/{id}")
    public ResponseEntity<Object> getVoteById(@PathVariable("id") String id) throws Exception {
        return ResponseEntity.ok(labService.getRealtimeVoteById(id));
    }

    @Operation(description = "투표 ID로 수정")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @PutMapping("/vote/{id}")
    public ResponseEntity<Object> updateVoteById(
        @PathVariable("id") String id,
        @RequestBody RealtimeVote realtimeVote
    ) throws Exception {
        // set ID
        realtimeVote.setId(id);
        // update RealtimeVote
        return ResponseEntity.ok(labService.updateRealtimeVoteById(realtimeVote));
    }

  @Operation(description = "투표 ID로 투표, 중복 안됨")
  @ApiResponse(responseCode = "200", description = "투표성공")
  @PostMapping("/vote/{id}")
    public ResponseEntity<Object> addVote(
        HttpServletRequest request,
        @PathVariable("id") String id,
        int voteIndex) throws Exception {
      labService.addRealtimeVote(id, request.getRemoteHost(), voteIndex);
      return ResponseEntity.ok().build();
    }

    @Operation(description = "투표 ID 삭제")
    @ApiResponse(responseCode = "200", description = "삭제성공")
    @DeleteMapping("/vote/{id}")
    public ResponseEntity<Object> deleteVoteById(@PathVariable("id") String id) throws Exception {
        // delete
        labService.deleteRealtimeVote(id);
        // 성공
        return ResponseEntity.ok().build();
    }

}
