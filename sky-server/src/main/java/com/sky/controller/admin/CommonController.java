package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@Api(tags = "文件上传接口")
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file)  {
        log.info("上传文件{}",file);
        try {
            String originalFilename = file.getOriginalFilename();
            String extension=originalFilename.substring(originalFilename.lastIndexOf('.'));
            String fileName = UUID.randomUUID().toString()+extension;
            String upload = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(upload);
        } catch (IOException e) {
            log.info("文件上传失败");
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
