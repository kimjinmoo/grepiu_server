package com.grepiu.www.process.grepiu.service;

import com.grepiu.www.process.grepiu.domain.form.CloudCreateForm;
import com.grepiu.www.process.grepiu.domain.form.CloudUpdateForm;
import com.grepiu.www.process.grepiu.entity.CloudStore;
import java.util.List;

/**
 *
 * 클라우드 서비스
 *
 */
public interface CloudService {

  // 파일을 등록한다.
  public void create(String uid, CloudCreateForm form) throws Exception;

  // 경로를 가져온다.
  public List<CloudStore> findAll(String uid, String parentId);

  // parentId기준 상위 객체를 가져온다.
  public CloudStore findOneByParentId(String uid, String parentId);

  // 변경한다.
  public void update(String uid, String id, CloudUpdateForm form) throws Exception;

  // 삭제한다.
  public void delete(String uid, String id) throws Exception;

  // 파일을 가져온다.
  public String getFilePath(String uid, String fileId);
}
