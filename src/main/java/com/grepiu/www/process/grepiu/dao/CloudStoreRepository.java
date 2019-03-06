package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.entity.CloudStore;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * 클라우드 스토어 DAO
 *
 */
public interface CloudStoreRepository extends MongoRepository<CloudStore, String> {

  // 클라우드 내용 가져오기
  List<CloudStore> findByAuthorizedUsersInAndPidOrderByAttributeAsc(List<String> userIds, String pid, Sort sort);
  // 클라우드 내용 가져오기
  Optional<CloudStore> findByAuthorizedUsersInAndId(List<String> userIds, String id);
  // 클라우드 내용 삭제
  void deleteByAuthorizedUsersAndId(List<String> userId, String id);
}
