package com.cathy.shopping.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class FileService {

    private final String BASE_PATH = "product";

    private final String THUMBNAIL_PATH = "thumbnail";

    private final int WIDTH = 300;

    private final int HEIGHT = 300;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public String uploadProductPhoto(MultipartFile file) throws IOException {
//        String filePath = "";
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(file.getContentType());
//        metadata.setContentLength(file.getSize());
//        filePath = "product/" + file.getOriginalFilename();
//        PutObjectResult result = s3Client.putObject(bucketName, filePath, file.getInputStream(), metadata);
//        System.out.println("S3 upload result: " + result.getExpirationTime() + "/" + result.getContentMd5());
        return uploadFile("product", file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getInputStream());
    }

    public String uploadFile(String folder, String fileName, String contentType, long contentLength, InputStream inputStream) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);
        String filePath = folder + "/" + fileName;
        PutObjectResult result = s3Client.putObject(bucketName, filePath, inputStream, metadata);
        System.out.println("S3 upload result: " + result.getExpirationTime() + "/" + result.getContentMd5());
        return filePath;
    }

    public String createThumbnail(MultipartFile file) throws IOException {
        BufferedImage originImage = ImageIO.read(file.getInputStream());

        int width = originImage.getWidth();
        int height = originImage.getHeight();
        int squareSize = Math.min(width, height);
        int x = (width - squareSize) / 2;
        int y = (height - squareSize) / 2;

        BufferedImage croppedImage = originImage.getSubimage(x, y, squareSize, squareSize);

        BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, originImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(croppedImage, 0, 0, WIDTH, HEIGHT, null);
        g2d.dispose();

        File tempFile = File.createTempFile("resized_", ".jpg");
        tempFile.deleteOnExit();
        ImageIO.write(resizedImage, "jpg", tempFile);

        tempFile.length();
        System.out.println("圖片已儲存於臨時檔案: " + tempFile.getAbsolutePath());

//        File newFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        ImageIO.write(resizedImage, "png", newFile);
//        System.out.println("圖片已成功處理並輸出至 " + newFile.getAbsolutePath());
        return uploadFile("product/thumbnail", file.getOriginalFilename(), file.getContentType(), tempFile.length(), new FileInputStream(tempFile));
    }
}
