package com.grepiu.www.process.grepiu.service;

import com.grepiu.www.process.grepiu.entity.CloudStore;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 클라우드 서비스
 *
 */
public interface CloudService {

  // 파일을 등록한다.
  public CloudStore createFile(String uid, String parentId, MultipartFile file) throws Exception;

  // 경로를 등록한다.
  public CloudStore createDir(String uid, String parentId, String folderName);

  // 경로를 가져온다.
  public List<CloudStore> findAll(String uid, String path);

  // 파일 이름을 변경한다.
  public CloudStore updateFileName(String uid, String id, String rename);

  // 디렉토리 이름을 변경한다.
  public CloudStore updateDirName(String uid, String id, String rename);

  // 파일을 삭제한다.
  public void deleteFile(String uid, String id);

  // 경로를 삭제한다.
  public void deleteDir(String uid, String id);

  // 파일을 가져온다.
  public String getFilePath(String uid, String id);
}
