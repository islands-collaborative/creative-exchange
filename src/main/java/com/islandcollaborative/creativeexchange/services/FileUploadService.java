package com.islandcollaborative.creativeexchange.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
public class FileUploadService {
    @Autowired
    private AmazonS3 amazonS3;

    @Value("aws.bucketName") private String bucketName;

    private void upload( String fileName,
                       InputStream inputStream
                       ) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        try {
            amazonS3.putObject(bucketName, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file");
        }
    }

}
