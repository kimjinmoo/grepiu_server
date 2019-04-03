package com.grepiu.www.process.common.helper;

import com.google.common.collect.Lists;
import com.grepiu.www.process.common.api.dao.FileRepository;
import com.grepiu.www.process.common.api.entity.Files;

import java.io.File;
import java.util.List;

import com.grepiu.www.process.common.utils.DateUtils;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관리
 */
@Slf4j
@Component
public class FileHelper {

    @Autowired
    private FileRepository fileRepository;

    // 기본 최상위 경로
    @Value(value = "${grepiu.file.path}")
    private String rootPath;

    /**
     * 단일 파일 업로드
     *
     * @param file
     * @return
     * @throws Exception
     */
    public Files uploadFile(MultipartFile file) throws Exception {
        // 기본 정보 Set
        Files filesVO = new Files();
        filesVO.setFileName(file.getOriginalFilename());
        filesVO.setOriginalFileName(file.getOriginalFilename());
        filesVO.setSize(file.getSize());
        filesVO.setRootPath(rootPath);
        filesVO.setPath(makeDatePath());

        // 파일 업로드 진행
        this.FileUploadProcess(filesVO, file);

        return filesVO;
    }

    /**
     *
     * 파일을 삭제를 한다.
     *
     * @param fileList 파일들
     * @throws Exception
     */
    public void deleteFiles(List<Files> fileList) throws Exception {
        for(Files f : fileList) {
            new File(f.getFullFilePath()).delete();
        }
    }

    /**
     * 다중 파일 업로드
     *
     * @param files
     * @return
     */
    public List<Files> uploadFiles(MultipartFile[] files) throws Exception {
        List<Files> filesVOList = Lists.newArrayList();

        for (MultipartFile file : files) {
            // set FIle 정보
            Files filesVO = new Files();
            filesVO.setOriginalFileName(file.getOriginalFilename());
            filesVO.setRootPath(rootPath);
            filesVO.setPath(makeDatePath());
            filesVO.setFileName(file.getOriginalFilename());
            filesVO.setSize(file.getSize());
            filesVOList.add(filesVO);

            this.FileUploadProcess(filesVO, file);
        }
        return filesVOList;
    }

    /**
     * 파일 업로드 처리한다.
     *
     * @param Files Files 객체
     * @param file MultipartFile 객체
     * @throws Exception
     */
    private void FileUploadProcess(Files Files, MultipartFile file) throws Exception {
        // 파일 생성
        java.io.File currentFile = new java.io.File(Files.getFullFilePath());
        // 파일 등록
        FileUtils.writeByteArrayToFile(currentFile, file.getBytes());
        // DB 등록
        fileRepository.save(Files);
    }

    /**
     *
     * 날짜 기준으로 경로를 생성한다.
     *
     *
     * @return String 객체
     */
    private String makeDatePath() {
        StringBuilder sb = new StringBuilder();
        return sb.append(DateUtils.getYear()).append("/").append(DateUtils.getMonth()).append("/").append(
            DateUtils.getDay()).append("/").toString();
    }

    /**
     *
     * 파일명을 지정한다.
     *
     * @param oriFileName String 객체
     * @return 파일명
     */
    private String makeFileName(String oriFileName) {
        StringBuilder sb = new StringBuilder();
        String name = UUID.randomUUID().toString();
        String extension = name
            .substring(name.lastIndexOf(".") > 0 ? (name.lastIndexOf(".") + 1) : name.length(),
                name.length());
        return sb.append(name).append(extension).toString();
    }
}
