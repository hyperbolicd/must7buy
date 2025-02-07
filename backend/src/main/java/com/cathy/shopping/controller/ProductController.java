package com.cathy.shopping.controller;

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
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String msg = "";
        try {
            msg +=  fileService.uploadProductPhoto(file);
            msg += ", ";
            msg += fileService.createThumbnail(file);
//            byte[] photoBytes = null;
//            if(photoFile != null)
//                photoBytes = photoFile.getBytes();
//            Employee createdEmployee = employeeService.createEmployee(employee, photoBytes);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.ok("FAIL:" + e.getMessage());
        }
        return ResponseEntity.ok("SUCCESS: " + msg);
    }
}
