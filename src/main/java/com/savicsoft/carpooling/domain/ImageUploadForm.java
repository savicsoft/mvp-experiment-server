package com.savicsoft.carpooling.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class ImageUploadForm {
    private String fileName;
    private MultipartFile picture;
}
