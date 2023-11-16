package com.savicsoft.carpooling.googlecloudstorage.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;

import com.savicsoft.carpooling.exception.NotFoundException;
import com.savicsoft.carpooling.exception.CouldNotCreateException;
import com.savicsoft.carpooling.exception.CouldNotDeleteException;
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

    public Blob uploadFile(MultipartFile file, String fileName, UUID userUUID) {
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
        try {
            Blob blob = storage.create(blobInfo, file.getBytes());
            return blob;
        } catch (StorageException | IOException e) {
            throw new CouldNotCreateException("Internal Error. Could not upload file.");
        }
    }

    public boolean deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty.");
        }
        try {
            Boolean deleted = storage.delete(bucketName, fileName);
            if (!deleted)
                throw new NotFoundException("File with specified name: " + fileName + "does not exist.");
            return deleted;
        } catch (StorageException e) {
            throw new CouldNotDeleteException("Internal Error. Could not delete the file");
        }
    }

    public Blob downloadFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty.");
        }

        return storage.get(bucketName, fileName);
    }

    public List<MultipartFile> downloadFiles(String prefix) {
        Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(prefix)).iterateAll();
        if (!blobs.iterator().hasNext())
            throw new IllegalArgumentException("Specified path: " + prefix + "does not exist.");
        return StreamSupport.stream(blobs.spliterator(), false)
                .map(MultipartFileAdapter::new)
                .collect(Collectors.toList());
    }
}
