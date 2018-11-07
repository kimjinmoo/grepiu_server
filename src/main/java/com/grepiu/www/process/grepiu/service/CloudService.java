package com.grepiu.www.process.grepiu.service;

import com.grepiu.www.process.grepiu.dao.CloudStoreRepository;
import com.grepiu.www.process.grepiu.domain.CloudStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * 클라우드 서비스
 *
 */
public class CloudService {

  @Autowired
  private CloudStoreRepository cloudStoreRepository;

  /**
   *
   * 폴더를 생성한다.
   *
   * @param cloudStore
   * @return CloudStore 객체
   */
  public CloudStore createDir(CloudStore cloudStore) {
    return cloudStoreRepository.save(cloudStore);
  }
}
