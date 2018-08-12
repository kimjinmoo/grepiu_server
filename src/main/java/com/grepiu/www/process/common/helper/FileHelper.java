package com.grepiu.www.process.common.helper;

import com.google.common.collect.Lists;
import com.grepiu.www.process.api.domain.FileVO;

import java.io.File;
import java.util.List;

import com.grepiu.www.process.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관리
 */
@Slf4j
@Component
public class FileHelper {

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
    public FileVO uploadFile(MultipartFile file) throws Exception {
        // 기본 정보 Set
        FileVO fileVO = new FileVO();
        fileVO.setFileName(file.getOriginalFilename());
        fileVO.setOriginalFileName(file.getOriginalFilename());
        fileVO.setSize(file.getSize());
        fileVO.setRootPath(rootPath);
        fileVO.setPath(makeDatePath());

        // 파일 업로드 진행
        this.FileUploadProcess(fileVO, file);

        return fileVO;
    }

    /**
     * 다중 파일 업로드
     *
     * @param files
     * @return
     */
    public List<FileVO> uploadFiles(MultipartFile[] files) throws Exception {
        List<FileVO> fileVOList = Lists.newArrayList();

        for (MultipartFile file : files) {
            // set FIle 정보
            FileVO fileVO = new FileVO();
            fileVO.setOriginalFileName(file.getOriginalFilename());
            fileVO.setRootPath(rootPath);
            fileVO.setPath(makeDatePath());
            fileVO.setFileName(file.getOriginalFilename());
            fileVO.setSize(file.getSize());
            fileVOList.add(fileVO);

            this.FileUploadProcess(fileVO, file);
        }
        return fileVOList;
    }

    /**
     * 파일 업로드 처리한다.
     *
     * @param fileVO
     * @param file
     * @throws Exception
     */
    private void FileUploadProcess(FileVO fileVO, MultipartFile file) throws Exception {
        // 파일 생성
        File currentFile = new File(fileVO.getFullFilePath());
        // 파일 등록
        FileUtils.writeByteArrayToFile(currentFile, file.getBytes());
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
        return sb.append(DateUtil.getYear()).append("/").append(DateUtil.getMonth()).append("/").append(DateUtil.getDay()).append("/").toString();
    }
}
