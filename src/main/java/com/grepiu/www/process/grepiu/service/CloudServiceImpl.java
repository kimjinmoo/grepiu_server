package com.grepiu.www.process.grepiu.service;

import com.grepiu.www.process.common.api.dao.FileRepository;
import com.grepiu.www.process.common.helper.FileHelper;
import com.grepiu.www.process.grepiu.common.CloudAttributeType;
import com.grepiu.www.process.grepiu.dao.CloudStoreRepository;
import com.grepiu.www.process.grepiu.domain.CloudCreateForm;
import com.grepiu.www.process.grepiu.domain.CloudUpdateForm;
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

/**
 *
 * 클라우드 저장소 서비스
 *
 */
@Slf4j
@Service
public class CloudServiceImpl implements CloudService {

  private final CloudStoreRepository cloudStoreRepository;

  private final FileRepository fileRepository;

  private final FileHelper fileHelper;

  public CloudServiceImpl(CloudStoreRepository cloudStoreRepository,
      FileRepository fileRepository, FileHelper fileHelper) {
    this.cloudStoreRepository = cloudStoreRepository;
    this.fileRepository = fileRepository;
    this.fileHelper = fileHelper;
  }


  /**
   *
   * 생성한다.
   *
   * @parma String uid 유저 ID
   * @param form CloudCreateForm 객체
   */
  @Override
  public void create(String uid, CloudCreateForm form) throws Exception {
    switch (form.getCloudAttributeType()) {
      case FILE:
        // 파일이 없을 경우 Error
        Optional.ofNullable(form.getFile()).orElseThrow(() -> new Exception("업로드할 파일이 존재하지 않습니다."));

        cloudStoreRepository.save(CloudStore.builder()
            .authorizedUsers(Arrays.asList(uid))
            .createId(uid)
            .parentId(Optional.ofNullable(form.getParentId()).filter(o->!o.isEmpty()).orElse(uid))
            .name(form.getFile().getOriginalFilename())
            .attribute(CloudAttributeType.FILE.getCode())
            .files(fileHelper.uploadFile(form.getFile()))
            .build()
        );
        break;
      case DIRECTORY:
        // 폴더명이 없을 경우 Error
        Optional.ofNullable(form.getName()).orElseThrow(() -> new Exception("폴더명이 존재하지 않습니다."));

        cloudStoreRepository.save(
            CloudStore.builder()
                .authorizedUsers(Arrays.asList(uid))
                .createId(uid)
                .parentId(Optional.ofNullable(form.getParentId()).filter(o->!o.isEmpty()).orElse(uid))
                .name(form.getName())
                .attribute(CloudAttributeType.DIRECTORY.getCode())
                .build()
        );
        break;
    }
  }

  /**
   *
   * 현 경로를 가져온다.
   *
   * @param uid 유저 ID
   * @param parentId 상위 ID
   * @return List<CloudStore> 객체
   */
  @Override
  public List<CloudStore> findAll(String uid, String parentId) {
    List<String> authorizedUsers = Arrays.asList(uid);

    return cloudStoreRepository
        .findByAuthorizedUsersInAndParentIdOrderByAttributeAsc(authorizedUsers,
            Optional.ofNullable(parentId).filter(o->!o.isEmpty()).filter(o->!o.isEmpty()).orElse(uid),
            Sort.by(Direction.ASC, "createDate"))
        .stream().map(p->{
          // 필요 없는 정보는 제거한다.
          if(Optional.ofNullable(p.getFiles()).isPresent()){
            p.getFiles().setRootPath(null);
            p.getFiles().setPath(null);
          }
          return p;
        }).collect(Collectors.toList());
  }

  /**
   *
   * parentID로 객체를 가져온다.
   *
   * @param uid 유자 ID
   * @param parentId 상위 ID
   * @return
   */
  @Override
  public CloudStore findOneByParentId(String uid, String parentId) {
    return cloudStoreRepository.findByAuthorizedUsersInAndId(Arrays.asList(uid),
        Optional.ofNullable(parentId).filter(o -> !o.isEmpty()).orElse(uid))
        .orElse(CloudStore.builder().build());
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
   * 파일 이름을 변경한다.
   *
   * @param uid 유저 ID
   * @param id 클라우드 ID
   * @param form CloudUpdateForm 객체
   * @return CloudStore 객체
   */
  @Override
  public void update(String uid, String id, CloudUpdateForm form) throws Exception {
    Optional.ofNullable(id).orElseThrow(()->new Exception("필수값 오류 입니다."));

    CloudStore dir = cloudStoreRepository.findByAuthorizedUsersInAndId(Arrays.asList(uid), id).orElseThrow(() -> new RuntimeException("Not Id"));
    dir.setName(form.getRename());
    cloudStoreRepository.save(dir);
  }

  /**
   *
   * 삭제한다.
   *
   * @param uid 유저 ID
   * @param id ID
   */
  @Override
  public void delete(String uid, String id) throws Exception {

    Optional.ofNullable(id).orElseThrow(()->new Exception("필수값 오류 입니다."));

    // set Auth
    List<String> authorizedUsers = Arrays.asList(uid);

    // get Data
    CloudStore obj = cloudStoreRepository.findByAuthorizedUsersInAndId(authorizedUsers, id).orElseThrow(()->new RuntimeException("wrong file"));

    // 파일인 경우 실제 경로에서도 제거한다.
    if (obj.getAttribute().equals(CloudAttributeType.FILE.getCode())) {
      // DB에서 파일 제거
      fileRepository.deleteById(obj.getFiles().getId());
      // 물리적인 파일 제거
      File file = new File(obj.getFiles().getFullFilePath());
      file.delete();
    }

    // 데이터 삭제
    cloudStoreRepository.deleteByAuthorizedUsersAndId(authorizedUsers, id);
  }
}
