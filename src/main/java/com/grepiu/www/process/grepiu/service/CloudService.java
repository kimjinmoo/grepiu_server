package com.grepiu.www.process.grepiu.service;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.api.domain.Files;
import com.grepiu.www.process.common.constant.CloudAttributeType;
import com.grepiu.www.process.common.helper.FileHelper;
import com.grepiu.www.process.grepiu.dao.CloudStoreRepository;
import com.grepiu.www.process.grepiu.domain.CloudStore;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 클라우드 서비스
 *
 */
@Slf4j
@Service
public class CloudService {

  @Autowired
  private CloudStoreRepository cloudStoreRepository;

  @Autowired
  private FileHelper fileHelper;

  /**
   *
   * 파일을 가져온다.
   *
   * @param uid 유저 ID
   * @param id 클라우드 ID
   * @return File 객체
   */
  public String getFilePath(String uid, String id) {
    List<String> authorizedUsers = Arrays.asList(uid);
    CloudStore cloudStore = cloudStoreRepository.findByAuthorizedUsersInAndId(authorizedUsers, id).orElseThrow(()->new RuntimeException("잘못된 정보를 호출 하였습니다."));
    return cloudStore.getFiles().getFullFilePath();
  }

  /**
   *
   * 현 경로를 가져온다.
   *
   * @param uid 유저 ID
   * @param pid 상위경로
   * @return List<CloudStore> 객체
   */
  public List<CloudStore> find(String uid, String pid) {
    List<String> authorizedUsers = Arrays.asList(uid);
    return cloudStoreRepository
        .findByAuthorizedUsersInAndPidOrderByAttributeAsc(authorizedUsers, pid,
            Sort.by(Direction.ASC, "createDate"))
        .stream().map(p->{
          // 파일 정보는 제거한다.
          if(Optional.ofNullable(p.getFiles()).isPresent()){
            p.getFiles().setRootPath(null);
            p.getFiles().setPath(null);
          }
          return p;
        }).collect(Collectors.toList());
  }

  /**
   *
   * 이름을 변경한다.
   *
   * @param uid 유저 ID
   * @param fid folder ID
   * @param rename 변경될 이름
   * @return CloudStore 객체
   */
  public CloudStore rename(String uid, String fid, String rename) {
    CloudStore st = cloudStoreRepository.findByAuthorizedUsersInAndId(Arrays.asList(uid), fid).orElseThrow(() -> new RuntimeException("Not Id"));
    st.setName(rename);
    return cloudStoreRepository.save(st);
  }

  /**
   *
   * 생성한다.
   *
   * @param cloudStore CloudStore 객체
   * @return CloudStore 객체
   */
  public CloudStore create(String uid, CloudStore cloudStore, MultipartFile file) throws Exception {
    // Set Default Data
    cloudStore.setAuthorizedUsers(Arrays.asList(uid));
    cloudStore.setCreateId(uid);
    cloudStore.setPid(Optional.ofNullable(cloudStore.getPid()).orElse("/"));;

    // File Logic
    if(Optional.ofNullable(file).isPresent()) {
      cloudStore.setName(file.getOriginalFilename());
      cloudStore.setAttribute(CloudAttributeType.FILE.getCode());
      cloudStore.setFiles(fileHelper.uploadFile(file));
    }

    return cloudStoreRepository.save(cloudStore);
  }

  /**
   *
   * 삭제한다.
   *
   * @param uid 유저 ID
   * @param id 클라우드 ID
   */
  public void delete(String uid, String id) {
    // set Auth
    List<String> authorizedUsers = Arrays.asList(uid);
    // get Data
    CloudStore obj = cloudStoreRepository.findById(id).orElseThrow(()->new RuntimeException("wrong file"));
    // 파일인 경우 실제 경로에서도 제거한다.
    if (obj.getAttribute().equals(CloudAttributeType.FILE.getCode())) {
      File file = new File(obj.getFiles().getFullFilePath());
      file.delete();
    }

    // delete
    cloudStoreRepository.deleteByAuthorizedUsersAndId(authorizedUsers, id);
  }
}
