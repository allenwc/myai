package net.ryian.flow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import net.ryian.flow.integration.ServiceFactory;

/**
 * @author allenwc
 * @date 2024/5/10 09:14
 */
@RestController
@Slf4j
public class FileController {

    private ServiceFactory serviceFactory;

    @Autowired
    public FileController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostMapping("/api/upload")
    public String upload(MultipartHttpServletRequest request) {
        String fileName = String.valueOf(System.currentTimeMillis());
        request.getFileMap().forEach((k,v) -> {
            try {
                PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(serviceFactory.getSetting().get("minio_bucket").asText())
                    .object(fileName)
                    .stream(v.getInputStream(), v.getSize(), -1)
                    .contentType(v.getContentType())
                    .build();
                serviceFactory.getMinioClient().putObject(args);
            } catch (Exception e) {
                log.error("upload file error", e);
            }
        });
        return fileName;
    }


}
