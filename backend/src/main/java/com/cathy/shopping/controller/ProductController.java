package com.cathy.shopping.controller;

import com.cathy.shopping.dto.UploadImageResponse;
import com.cathy.shopping.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadImageResponse> uploadFile(@RequestPart("file") MultipartFile file) {
        String msg = "";
        if(file.isEmpty()) {
            UploadImageResponse response = new UploadImageResponse();
            response.setMessage("No file found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(fileService.uploadProductPhoto(file));
    }
}
