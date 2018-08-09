package com.grepiu.www.process.sample.controller;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.config.auth.dao.UserRepository;
import com.grepiu.www.process.common.config.auth.domain.Role;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.config.Oauth2ClientConfig;
import com.grepiu.www.process.common.helper.FileHelper;
import com.grepiu.www.process.common.tools.domain.FileVO;
import com.grepiu.www.process.common.utils.CollectionUtil;
import com.grepiu.www.process.common.utils.DateUtil;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.utils.DistanceCalculator;
import com.grepiu.www.process.sample.dao.LotteCineDBRepository;
import com.grepiu.www.process.sample.dao.LotteCineLocalRepository;
import com.grepiu.www.process.sample.domain.Post;
import com.grepiu.www.process.sample.domain.SampleMessage;
import com.grepiu.www.process.sample.service.PostService;
import com.grepiu.www.process.sample.service.SampleService;
import com.grepiu.www.process.sample.service.SampleTaskService;
import com.mongodb.DuplicateKeyException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 샘플 컨트롤러이다.
 *
 * @author jm
 * @since 2017.11.01
 */


@CrossOrigin(origins = "*")
@Slf4j
@RestController
public class SampleRestController {

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private LotteCineDBRepository lotteCineDBRepository;

  @Autowired
  private LotteCineLocalRepository lotteCineLocalRepository;

  @Autowired
  private SampleService sampleService;

  @Autowired
  private SampleTaskService sampleTaskService;

  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  private PostService postService;

  @Autowired
  private Oauth2ClientConfig oauth2Service;


  @Autowired
  private FileHelper fileHelper;



  @ApiOperation(value = "헬로월드")
  @GetMapping(value = "/sample/helloworld")
  public ModelAndView helloWorld() {
    return new ModelAndView("home");
  }

  @ApiOperation("Oauth 기반 로그인")
  @GetMapping("/sample/oauth/login")
  public ResponseEntity<Object> oauthLogin(){
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
  /**
   *
   * 날짜를 가져온다. JAVA8 LocalDateTime 이용
   * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
   *
   */
  @ApiOperation(value = "시간조회하기")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "type", value = "시간값[data/time/datatime]", required = true, dataType = "String")})
  @GetMapping(value = "/sample/util/now/{type}")
  public ResponseEntity<String> currentTime(@PathVariable("type") String type) {
    String result = "";
    switch (type) {
      case "data":
        result = DateUtil.now(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        break;
      case "time":
        result = DateUtil.now(DateTimeFormatter.ofPattern("hh:mm:ss"));
        break;
      case "datatime":
        result = DateUtil.now(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
        break;
      default:
        result = "option을 확인 하여 주십시오.";
        break;
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }

  /**
   * null 체크 샘플 리스트
   */
  @ApiOperation(value = "샘플리스트")
  @GetMapping("/sample/list")
  public ResponseEntity<Object> getSampleList() {
    Optional<List<String>> value = CollectionUtil.isNull(() -> {
      return sampleService.getSampleList();
    });
    return new ResponseEntity<Object>(value.get(), HttpStatus.OK);
  }

  /**
   * 몽고 DB 유저 등록 RestAPI
   */
  @ApiOperation(value = "일반유저등록")
  @PostMapping("/sample/mongodb/users")
  public ResponseEntity<Void> addSampleUser(@RequestParam String id, @RequestParam String password) {
    try {
      User user = User.build(id, password, Role.USER);
      userRepository.save(user);
    } catch (DuplicateKeyException e) {
      log.debug("error : {}", e.getMessage());
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  };

  /**
   * 몽고DB 리스트 가져오기
   */
  @ApiOperation(value = "몽고DB 조회")
  @GetMapping("/sample/mongodb/users")
  public ResponseEntity<List<User>> getSampleUser() {
    List<User> users = userRepository.findAll();

    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }

  /**
   * 병렬 처리 테스트
   */
  @ApiOperation(value = "병렬테스트")
  @GetMapping("/sample/parallelTask")
  public ResponseEntity<Object> parallelTask() {
    HashMap<String, Object> params = Maps.newHashMap();

    // 병렬 처리를 한다.
    return new ResponseEntity<Object>(sampleTaskService.process(params), HttpStatus.OK);
  }

  @ApiOperation(value = "영화관 정보 등록 수동처리")
  @GetMapping("/sample/saveCinemaLocationByManual")
  public ResponseEntity<Object> saveCinemaLocationByManual() {
    sampleService.collectionCinemaLocation();
    return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
  }

  @ApiOperation(value = "영화 상영관 정보 수동 처리")
  @GetMapping("/sample/saveCinemaInfoByManual")
  public ResponseEntity<Object> saveCinemaInfoByManual() {
    // 비동기 처리
    sampleService.collectionCinemaMovieInfo();
    return new ResponseEntity<Object>("완료되면 grep웹에서 확인 가능합니다.",HttpStatus.OK);
  }

  @ApiOperation(value = "상영 영화 크롤링 데이터 리스트")
  @ApiResponse(code = 200, message = "조회성공")
  @GetMapping("/sample/crawler/cine/screen")
  public ResponseEntity<Object> findCine() {
    return new ResponseEntity<Object>(lotteCineDBRepository.findAllBy(), HttpStatus.OK);
  }

  @ApiOperation(value = "롯데 시네마 상영 영화 크롤링 데이터 리스트")
  @ApiResponse(code = 200, message = "조회성공")
  @GetMapping("/sample/crawler/cine/screen/{storeName}")
  public ResponseEntity<Object> findCineByStoreName(@PathVariable("storeName") String storeName) {
    Optional<Cinema> o = lotteCineDBRepository.findAllBy().parallelStream()
        .filter(v -> v.getMovieInfo().containsKey(storeName)).findFirst();
    o.orElse(new Cinema());

    return new ResponseEntity<>(o.isPresent()?o.get().getMovieInfo().get(storeName):null, HttpStatus.OK);
  }

  @ApiOperation(value = "시네마 매장 정보")
  @ApiResponse(code = 200, message = "조회성공")
  @GetMapping("/sample/crawler/cine/locale")
  public ResponseEntity<Object> getCineLocale() {
    return new ResponseEntity<Object>(lotteCineLocalRepository.findAllBy(), HttpStatus.OK);
  }

  @ApiOperation(value = "인접한 영화관 찾기")
  @ApiResponse(code = 200, message = "조회성공")
  @GetMapping("/sample/crawler/cine/near")
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

  @ApiOperation(value = "메인채팅방에 정보전달")
  @ApiResponse(code = 200, message = "전달성공")
  @GetMapping("/sample/sendChat")
  public ResponseEntity<Object> sendChat() {
    this.template.convertAndSend("/topic/messages", new SampleMessage("시스템 알림","Sample Message가 전달 되었습니다."));
    return new ResponseEntity<Object>(HttpStatus.OK);
  }

  @ApiOperation(value = "단일 파일업로드")
  @PostMapping(path = "/sample/upload/file")
  public ResponseEntity<FileVO> fileUpload(@RequestParam(required=true, name = "file") MultipartFile file) throws Exception {
    return new ResponseEntity<FileVO>(fileHelper.uploadFile(file), HttpStatus.OK);
  }

  @ApiOperation(value = "단일 파일업로드")
  @PostMapping(path = "/sample/upload/file/multiple")
  public ResponseEntity<List<FileVO>> multipleFileUpload(@RequestParam(required=true, name="files") MultipartFile[] files) throws Exception {
    return new ResponseEntity<List<FileVO>>(fileHelper.uploadFiles(files), HttpStatus.OK);
  }

  @ApiOperation("포스트 등록")
  @PostMapping("/sample/post")
  public ResponseEntity<Object> savePost(@RequestBody Post post)  {
    post.setSeq(postService.getNextSequence("post"));
    return new ResponseEntity<>(postService.save(post), HttpStatus.OK);
  }

  @ApiOperation("포스트 상세 업데이트")
  @PutMapping("/sample/post/{id}")
  public ResponseEntity<Object> updatePost(@PathVariable String id, @RequestBody Post post) {
    return new ResponseEntity<>(postService.update(id, post), HttpStatus.OK);
  }

  @ApiOperation("포스트 전체 리스트 가져오기")
  @GetMapping("/sample/post")
  public ResponseEntity<Object> getPostPaging(@ApiParam(value = "페이지") @RequestParam int currentPage,
      @RequestParam @ApiParam(value = "사이즈") int size) {
    return new ResponseEntity<>(postService.findAllPage(currentPage, size), HttpStatus.OK);
  }

  @ApiOperation("포스트 상세 가져오기")
  @GetMapping("/sample/post/{id}")
  public ResponseEntity<Object> getPost(@PathVariable String id) {
    return new ResponseEntity<>(postService.findOneByID(id), HttpStatus.OK);
  }

  @ApiOperation("포스트 삭제")
  @DeleteMapping("/sample/post/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable String id) {
    postService.deleteById(id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   *
   * 로그인 엑세스 토큰을 획득한다.
   *
   * @param id
   * @param passwd
   * @return
   */
  @GetMapping("/oauth/login")
  public ResponseEntity<Object> getAccess(@RequestParam String id, @RequestParam String passwd) {
    return new ResponseEntity<>(oauth2Service.getAccessToken(id, passwd), HttpStatus.OK);
  }
}
