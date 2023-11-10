package com.savicsoft.carpooling.googlecloudstorage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class GoogleCloudStorageServiceTest {
    @Mock
    private Storage storage;
    @Value("${GCS_BUCKET_NAME}")
    private String bucketName;
    @InjectMocks
    private GoogleCloudStorageService storageService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        storageService = new GoogleCloudStorageService(bucketName);
    }

    @Test
    void testUploadFile() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(false);
        Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");
        Mockito.when(mockFile.getBytes()).thenReturn("test-file-content".getBytes());

        Blob uploadBlob = storageService.uploadFile(mockFile, "test-file.jpg", UUID.randomUUID());

        assertEquals(uploadBlob.getContentType(), mockFile.getContentType());
        assertArrayEquals(uploadBlob.getContent(), mockFile.getBytes());
    }

    @Test
    void testUploadFileWithEmptyFile() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(true);
        UUID userUUID = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> storageService.uploadFile(mockFile, "test-file.jpg", userUUID));
    }

    @Test
    void testDownloadFile() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(false);
        UUID userUUID = UUID.randomUUID();

        Blob uploadedBlob = storageService.uploadFile(mockFile, "test-file.jpg", userUUID);
        Blob downloadedBlob = storageService.downloadFile("test-file.jpg");

        assertEquals(uploadedBlob.getContentType(), downloadedBlob.getContentType());
        assertArrayEquals(uploadedBlob.getContent(), downloadedBlob.getContent());
        assertEquals(userUUID.toString(), downloadedBlob.getMetadata().get("uuid"));
    }

    @Test
    void testDownloadFileWithEmptyFileName() {
        assertThrows(IllegalArgumentException.class, () -> storageService.downloadFile(""));
    }

    @Test
    void testDeleteFile() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        storageService.uploadFile(mockFile, "test-file.jpg", UUID.randomUUID());

        assertEquals(false, storageService.deleteFile("some-file.jpg"));
        assertEquals(true, storageService.deleteFile("test-file.jpg"));
    }

    @Test
    void testDeleteFileWithEmptyFileName() {
        assertThrows(IllegalArgumentException.class, () -> storageService.deleteFile(""));
    }
}
