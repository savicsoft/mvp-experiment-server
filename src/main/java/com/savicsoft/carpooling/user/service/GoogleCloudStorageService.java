package com.savicsoft.carpooling.user.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {
    private final Storage storage;
    private final String bucketName;

    public GoogleCloudStorageService(@Value("${GCS_API_KEY}") String apiKey, @Value("${GCS_BUCKET_NAME}") String bucketName) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(apiKey.getBytes()));

        if (credentials instanceof ServiceAccountCredentials) {
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();
            this.storage = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .setProjectId(projectId)
                    .build().getService();
        } else {
            throw new IllegalArgumentException("Invalid credentials type. Expected ServiceAccountCredentials.");
        }

        this.bucketName = bucketName;
    }

    /**
     * Uploads a file to Google Cloud Storage.
     *
     * @param file       The file to upload.
     * @param fileName The name of the object in the bucket.
     * @return The UUID metadata of the uploaded file (so it can be stored in database and linked with user that uploaded file).
     * @throws IOException if an error occurs during the upload.
     */
    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        if (file == null || file.isEmpty() || fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File and fileName cannot be null or empty.");
        }

        BlobId blobId = BlobId.of(bucketName, fileName);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("uuid", UUID.randomUUID().toString());

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setMetadata(metadata)
                .build();

        storage.create(blobInfo, file.getBytes());

        return blobInfo.getMetadata().get("uuid");
    }

    public void deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty.");
        }

        storage.delete(bucketName, fileName);
    }

    public Blob downloadFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty.");
        }

        return storage.get(bucketName, fileName);
    }
}