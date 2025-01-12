package com.backend.image;

import com.backend.image.dto.ImageResponseDto;

import java.util.List;

public interface ImageCustomRepository {

    List<ImageResponseDto> findUrlsByS3KeyContaining(String keyword);

}

