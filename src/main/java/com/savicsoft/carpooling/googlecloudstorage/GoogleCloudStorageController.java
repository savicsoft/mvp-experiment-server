package com.savicsoft.carpooling.googlecloudstorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;

import java.util.UUID;

@RestController
@RequestMapping("/api/storage")
public class GoogleCloudStorageController {
    private final GoogleCloudStorageService storageService;

    @Autowired
    public GoogleCloudStorageController(GoogleCloudStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, @RequestParam("userUUID") UUID userUUID) {
        try {
            storageService.uploadFile(file, fileName, userUUID);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload the file: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        try {
            storageService.deleteFile(fileName);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete the file: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        try {
            Blob blob = storageService.downloadFile(fileName);
            byte[] fileContent = blob.getContent();
            return ResponseEntity.ok()
                    .header("Content-Type", blob.getContentType())
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .header("UUID", blob.getMetadata().get("uuid"))
                    .body(fileContent);
        } catch (Exception e) {
            String errorMessage = "Failed to download the file: " + e.getMessage();

            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(errorMessage.getBytes());
        }
    }
}