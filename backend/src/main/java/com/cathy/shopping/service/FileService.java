package com.cathy.shopping.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.cathy.shopping.dto.UploadImageResponse;
import jakarta.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

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

    public UploadImageResponse uploadProductPhoto(MultipartFile file) {
        UploadImageResponse response = new UploadImageResponse();
        try {
            String fileExtension = getFileExtension(file);
            String fileName = getMd5(file) + "." +fileExtension;
            // upload origin size image
            response.setPhotoPath(uploadFile(BASE_PATH, fileName, file.getContentType(), file.getSize(), file.getInputStream()));
            // upload thumbnail
            File thumbnail = createThumbnail(file.getInputStream(), fileExtension);
            try (FileInputStream is = new FileInputStream(thumbnail)){
                response.setThumbnailPath(uploadFile(BASE_PATH + "/" + THUMBNAIL_PATH, "thumbnail-" + fileName, file.getContentType(), thumbnail.length(), is));
            }
            response.setUploadResult(true);
        } catch (IOException e) {
            response.setMessage(e.getMessage());
        }

        return response;
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

    public File createThumbnail(InputStream is, String fileExtension) throws IOException {
        BufferedImage originImage = ImageIO.read(is);
        BufferedImage croppedImage = getSquareImage(originImage);

        BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, originImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(croppedImage, 0, 0, WIDTH, HEIGHT, null);
        g2d.dispose();

        File tempFile = File.createTempFile("resized_", "." + fileExtension);
        tempFile.deleteOnExit();
        ImageIO.write(resizedImage, fileExtension, tempFile);

        return tempFile;
    }

    public BufferedImage getSquareImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int squareSize = Math.min(width, height);
        int x = (width - squareSize) / 2;
        int y = (height - squareSize) / 2;
        return image.getSubimage(x, y, squareSize, squareSize);
    }

    public String getFileExtension(MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return fileExtension.equals("jpeg") ? "jpg" : fileExtension;
    }

    public String getMd5(MultipartFile file) throws IOException {
        return DigestUtils.md5Hex(file.getInputStream());
    }
}
