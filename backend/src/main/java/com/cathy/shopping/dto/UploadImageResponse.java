package com.cathy.shopping.dto;

public class UploadImageResponse {

    private String photoPath;
    private String thumbnailPath;
    private boolean uploadResult;
    private String message;

    public UploadImageResponse() {
        this.uploadResult = false;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public boolean isUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(boolean uploadResult) {
        this.uploadResult = uploadResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
