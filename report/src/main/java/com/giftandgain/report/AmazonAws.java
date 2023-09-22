package com.giftandgain.report;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class AmazonAws {
    String bucketName = System.getenv("S3_BUCKET_NAME");
    String region = System.getenv("S3_REGION");

    AWSCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();
    
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withCredentials(credentialsProvider)
        .withRegion(region).build();
    
    public List<String> listBuckets() {
        List<Bucket> buckets = s3.listBuckets();
        List<String> bucketNamesList = new ArrayList<>();

        for (Bucket b : buckets) {
            bucketNamesList.add(b.getName());
        }

        return bucketNamesList;
    }

    public boolean doesBucketExist() {
        List<String> bucketNamesList = listBuckets();
        return bucketNamesList.contains(bucketName);
    }

    public List<String> listObjects() {
        List<String> objectKeysList = new ArrayList<>();
        ListObjectsV2Result result = s3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            objectKeysList.add(os.getKey());
        }
        return objectKeysList;
    }

    public void uploadFile(String objectKey, byte[] csvData) {
        // Create metadata for the S3 object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(csvData.length);

        try {
            s3.putObject(bucketName, objectKey, new ByteArrayInputStream(csvData), metadata);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public String getObjectUrl(String objectKey) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucketName, objectKey);
        URL objectUrl = s3.generatePresignedUrl(generatePresignedUrlRequest);
        return objectUrl.toString();
    }
}
