package com.grepiu.www.process.grepiu.service;

import com.google.common.collect.Lists;
import com.grepiu.www.process.grepiu.dao.CloudStoreRepository;
import com.grepiu.www.process.grepiu.domain.CloudStore;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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

  /**
   *
   * 현 경로를 가져온다.
   *
   * @param uid user ID
   * @param path 경로
   * @return List<CloudStore> 객체
   */
  public List<CloudStore> getDir(String uid, String path) {
    List<String> authorizedUsers = Arrays.asList(uid);
    return cloudStoreRepository
        .findByAuthorizedUsersInAndPathOrderByAttributeDesc(authorizedUsers, path,
            Sort.by(Direction.DESC, "createDate"));
  }

  /**
   *
   *
   *
   * @param uid user ID
   * @param fid folder ID
   * @param rename 변경될 이름
   * @return CloudStore 객체
   */
  public CloudStore renameDir(String uid, String fid, String rename) {
    CloudStore st = cloudStoreRepository.findById(fid).orElseThrow(() -> new RuntimeException("Not Id"));
    st.setName(rename);
    return cloudStoreRepository.save(st);
  }

  /**
   *
   * 폴더를 생성한다.
   *
   * @param cloudStore CloudStore 객체
   * @return CloudStore 객체
   */
  public CloudStore createDir(String uid, CloudStore cloudStore, MultipartFile file) {
    // set Auth
    cloudStore.setAuthorizedUsers(Arrays.asList(uid));
    // save
    return cloudStoreRepository.save(cloudStore);
  }
}
