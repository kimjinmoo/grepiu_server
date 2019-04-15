package com.grepiu.www.process.grepiu.service;

import com.grepiu.www.process.grepiu.common.CloudAttributeType;
import com.grepiu.www.process.common.helper.FileHelper;
import com.grepiu.www.process.grepiu.common.Constant;
import com.grepiu.www.process.grepiu.dao.CloudStoreRepository;
import com.grepiu.www.process.grepiu.entity.CloudStore;
import java.io.File;
import java.util.Arrays;
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
public class CloudServiceImpl implements CloudService {

  @Autowired
  private CloudStoreRepository cloudStoreRepository;

  @Autowired
  private FileHelper fileHelper;


  /**
   *
   * 생성한다.
   *
   * @parma String uid 유저 ID
   * @param parentId String 상위 ID
   * @param file MultipartFile 객체
   * @return CloudStore 객체
   */
  @Override
  public CloudStore createFile(String uid, String parentId, MultipartFile file) throws Exception {
    // 파일이 없을 경우 Error
    Optional.ofNullable(file).orElseThrow(() -> new Exception("업로드할 파일이 존재하지 않습니다."));

    return cloudStoreRepository.save(CloudStore.builder()
        .authorizedUsers(Arrays.asList(uid))
        .createId(uid)
        .parentId(Optional.ofNullable(parentId).orElse(uid))
        .name(file.getOriginalFilename())
        .attribute(CloudAttributeType.FILE.getCode())
        .files(fileHelper.uploadFile(file))
        .build()
    );
  }

  /**
   *
   * 폴더를 생성한다.
   *
   * @param uid 유저 ID
   * @param parentId 상위 ID
   * @param folderName 디렉토리 명
   * @return CloudStore 객체
   */
  @Override
  public CloudStore createDir(String uid, String parentId, String folderName) {
    return cloudStoreRepository.save(
        CloudStore.builder()
            .authorizedUsers(Arrays.asList(uid))
            .createId(uid)
            .parentId(Optional.ofNullable(parentId).orElse(uid))
            .name(folderName)
            .attribute(CloudAttributeType.DIRECTORY.getCode())
            .build()
    );
  }

  /**
   *
   * 파일을 가져온다.
   *
   * @param uid 유저 ID
   * @param id 클라우드 ID
   * @return File 객체
   */
  @Override
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
  @Override
  public List<CloudStore> findAll(String uid, String pid) {
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
   * 파일 이름을 변경한다.
   *
   * @param uid 유저 ID
   * @param fid folder ID
   * @param rename 변경될 이름
   * @return CloudStore 객체
   */
  @Override
  public CloudStore updateFileName(String uid, String fid, String rename) {
    CloudStore st = cloudStoreRepository.findByAuthorizedUsersInAndId(Arrays.asList(uid), fid).orElseThrow(() -> new RuntimeException("Not Id"));
    st.setName(rename);
    return cloudStoreRepository.save(st);
  }

  /**
   *
   * 디렉토리 이름을 변경한다.
   *
   * @param uid 유저 ID
   * @param fid folder ID
   * @param rename 변경될 이름
   * @return CloudStore 객체
   */
  @Override
  public CloudStore updateDirName(String uid, String fid, String rename) {
    CloudStore st = cloudStoreRepository.findByAuthorizedUsersInAndId(Arrays.asList(uid), fid).orElseThrow(() -> new RuntimeException("Not Id"));
    st.setName(rename);
    return cloudStoreRepository.save(st);
  }

  /**
   *
   * 파일을 삭제한다.
   *
   * @param uid 유저 ID
   * @param id 클라우드 ID
   */
  @Override
  public void deleteFile(String uid, String id) {
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

  /**
   *
   * 디렉토리를 삭제한다.
   *
   * @param uid 유저 ID
   * @param id 클라우드 ID
   */
  @Override
  public void deleteDir(String uid, String id) {
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
