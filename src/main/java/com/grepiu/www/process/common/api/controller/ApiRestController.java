package com.grepiu.www.process.common.api.controller;

import com.grepiu.www.process.common.api.entity.Files;
import com.grepiu.www.process.common.api.service.BaseServiceImpl;
import com.grepiu.www.process.common.helper.FileHelper;
import com.grepiu.www.process.common.utils.QrCodeUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * API 컨트롤러 이다.
 */
@RequestMapping("/api")
@RestController
@Slf4j
public class ApiRestController {

    private final FileHelper fileHelper;

    private final BaseServiceImpl baseService;

    public ApiRestController(FileHelper fileHelper,
                             BaseServiceImpl baseService) {
        this.fileHelper = fileHelper;
        this.baseService = baseService;
    }

    @Operation(description = "단일 파일업로드")
    @PostMapping(path = "/{ver}/upload/file")
    public @ResponseBody
    ResponseEntity<Files> fileUpload(@PathVariable("ver") String ver, @RequestParam(required = true, name = "file") MultipartFile file) throws Exception {
        return new ResponseEntity<Files>(fileHelper.uploadFile(file), HttpStatus.OK);
    }

    @Operation(description = "단일 파일업로드")
    @PostMapping(path = "/{ver}/upload/file/multiple")
    public @ResponseBody
    ResponseEntity<List<Files>> multipleFileUpload(@PathVariable("ver") String ver, @RequestParam(required = true, name = "files") MultipartFile[] files) throws Exception {
        return new ResponseEntity<List<Files>>(fileHelper.uploadFiles(files), HttpStatus.OK);
    }

    /**
     *
     * Qr 코드를 생성한다.
     *
     * @param ver
     * @param text
     * @return
     * @throws Exception
     */
    @Operation(description = "QR 코드를 생성한다.")
    @GetMapping(value = "/{ver}/qrcode/{text}", produces = "image/png")
    public BufferedImage qrCode(
            @PathVariable("ver") String ver,
            @PathVariable("text") String text
    ) throws Exception {
        return QrCodeUtils.generateQRCodeImage(text);
    }

    @Operation(description = "파일 업로드 리스트")
    @GetMapping("/upload/file")
    public @ResponseBody
    ResponseEntity<Object> fileList() {
        return new ResponseEntity<>(baseService.getUploadFileList(), HttpStatus.OK);
    }

}
