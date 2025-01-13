package com.backend.image;


import com.backend.image.dto.ImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    UploadFile uploadCardImage(MultipartFile file)throws IOException;

    void deleteCardImage(Long id);

    List<UploadFile> getImages();

    ImageResponseDto getImageByS3Key(String s3Key);

    List<ImageResponseDto> getUrlsByKeyword(String keyword);
}
