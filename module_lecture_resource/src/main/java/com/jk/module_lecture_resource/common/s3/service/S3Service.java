package com.jk.module_lecture_resource.common.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(String fileName, MultipartFile file) throws IOException {
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), null);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }
}