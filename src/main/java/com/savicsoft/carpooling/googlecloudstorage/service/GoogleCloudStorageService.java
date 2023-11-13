package com.savicsoft.carpooling.googlecloudstorage.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;

import com.savicsoft.carpooling.googlecloudstorage.adapter.MultipartFileAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GoogleCloudStorageService {
    private final Storage storage;
    private final String bucketName;

    public GoogleCloudStorageService(@Value("${GCS_BUCKET_NAME}") String bucketName) throws IOException {
        InputStream serviceAccount = getClass().getResourceAsStream("/google_key.json");
        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);

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

    public Blob uploadFile(MultipartFile file, String fileName, UUID userUUID) throws IOException {
        if (file == null || file.isEmpty() || fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File and fileName cannot be null or empty.");
        }

        BlobId blobId = BlobId.of(bucketName, fileName);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("uuid", userUUID.toString());

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setMetadata(metadata)
                .build();

        return storage.create(blobInfo, file.getBytes());
    }

    public boolean deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty.");
        }

        return storage.delete(bucketName, fileName);
    }

    public Blob downloadFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty.");
        }

        return storage.get(bucketName, fileName);
    }

    public List<MultipartFile> downloadFiles(String prefix) {
        Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(prefix)).iterateAll();
        return StreamSupport.stream(blobs.spliterator(), false)
                .map(MultipartFileAdapter::new)
                .collect(Collectors.toList());
    }
}
