package com.savicsoft.carpooling.googlecloudstorage;

import com.savicsoft.carpooling.googlecloudstorage.controller.GoogleCloudStorageController;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.Blob;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class GoogleCloudStorageControllerTest {
    private final GoogleCloudStorageService storageService = mock(GoogleCloudStorageService.class);
    private final GoogleCloudStorageController storageController = new GoogleCloudStorageController(storageService);

    @Test
    void testUploadFile() throws IOException {
        MultipartFile mockFile = new MockMultipartFile("file", "test-file.jpg", "image/jpeg", "Test file content".getBytes());
        UUID userUUID = UUID.randomUUID();

        when(storageService.uploadFile(any(MultipartFile.class), eq("test-file.jpg"), eq(userUUID))).thenReturn(mock(Blob.class));

        ResponseEntity<String> response = storageController.uploadFile(mockFile, "test-file.jpg", userUUID);

        assertEquals("File uploaded successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(storageService, times(1)).uploadFile(any(MultipartFile.class), eq("test-file.jpg"), eq(userUUID));
    }

    @Test
    void testUploadFileFailure() throws IOException {
        MultipartFile mockFile = new MockMultipartFile("file", "test-file.jpg", "image/jpeg", "Test file content".getBytes());
        UUID userUUID = UUID.randomUUID();

        when(storageService.uploadFile(any(MultipartFile.class), eq("test-file.jpg"), eq(userUUID)))
                .thenThrow(new RuntimeException("Upload failed"));

        ResponseEntity<String> response = storageController.uploadFile(mockFile, "test-file.jpg", userUUID);

        assertEquals("Failed to upload the file: Upload failed", response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(storageService, times(1)).uploadFile(any(MultipartFile.class), eq("test-file.jpg"), eq(userUUID));
    }

    @Test
    void testDeleteFile() {
        String fileName = "test-file.jpg";

        when(storageService.deleteFile(fileName)).thenReturn(true);

        ResponseEntity<String> response = storageController.deleteFile(fileName);

        assertEquals("File deleted successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(storageService, times(1)).deleteFile(fileName);
    }

    @Test
    void testDeleteFileFailure() {
        String fileName = "test-file.jpg";

        when(storageService.deleteFile(fileName)).thenReturn(false);

        ResponseEntity<String> response = storageController.deleteFile(fileName);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(storageService, times(1)).deleteFile(fileName);
    }

    @Test
    void testDownloadFile() throws IOException {
        String fileName = "test-file.jpg";
        MultipartFile mockFile = new MockMultipartFile("file", fileName, "image/jpeg", "Test file content".getBytes());
        UUID uuid = UUID.randomUUID();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("uuid", uuid.toString());

        Blob mockBlob = Mockito.mock(Blob.class);
        when(mockBlob.getContent()).thenReturn("Test file content".getBytes());
        when(mockBlob.getContentType()).thenReturn("image/jpeg");
        when(mockBlob.getMetadata()).thenReturn(metadata);
        when(storageService.downloadFile(fileName)).thenReturn(mockBlob);

        ResponseEntity<byte[]> response = storageController.downloadFile(fileName);

        assertNotNull(response.getBody());
        assertEquals("image/jpeg", response.getHeaders().getContentType().toString());
        assertEquals("attachment; filename=" + fileName, response.getHeaders().get("Content-Disposition").get(0));
        assertEquals(uuid.toString(), response.getHeaders().get("uuid").get(0));
        assertArrayEquals("Test file content".getBytes(), response.getBody());

        verify(storageService, times(1)).downloadFile(fileName);
    }

    @Test
    void testDownloadFileNotFound() {
        String fileName = "test-file.jpg";

        when(storageService.downloadFile(fileName)).thenReturn(null);

        ResponseEntity<byte[]> response = storageController.downloadFile(fileName);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(storageService, times(1)).downloadFile(fileName);
    }
}
