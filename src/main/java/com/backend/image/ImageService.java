package com.backend.image;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    UploadFile uploadCardImage(MultipartFile file)throws IOException;

    void deleteCardImage(Long id);

    List<UploadFile> getImages();

    UploadFile getImageByS3Key(String s3Key);
}
