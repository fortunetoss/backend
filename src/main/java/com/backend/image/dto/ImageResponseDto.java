package com.backend.image.dto;

import com.backend.image.ImageController;
import com.backend.image.ImageRepository;
import com.backend.image.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {

    private String url;
    private String s3Key;

    public static ImageResponseDto fromEntity(UploadFile uploadFile) {
        return new ImageResponseDto(
                uploadFile.getUrl(),
                uploadFile.getS3Key()
        );
    }
}