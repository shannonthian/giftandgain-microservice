package com.giftandgain.report;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component
public class AmazonAws {
    String bucketName = System.getenv("S3_BUCKET_NAME");
    String region = System.getenv("S3_REGION");
    
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
    
    public boolean doesBucketExist() {
        List<Bucket> buckets = s3.listBuckets();
        return buckets.size() != 0;
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
}
