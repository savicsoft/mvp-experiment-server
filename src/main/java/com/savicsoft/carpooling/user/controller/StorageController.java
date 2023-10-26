package com.savicsoft.carpooling.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;

import com.savicsoft.carpooling.user.service.GoogleCloudStorageService;

@RestController
@RequestMapping("/api/storage")
public class StorageController {
    private final GoogleCloudStorageService storageService;

    @Autowired
    public StorageController(GoogleCloudStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
        try {
            String uuid = storageService.uploadFile(file, fileName);
            return ResponseEntity.ok(uuid);
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