package com.backend.image;

import com.backend.image.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

   private final ImageService imageService;

    /*
    * 이미지 s3에 업로드
    * */
    @PostMapping("/image/upload")
    public ResponseEntity<UploadFile> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        UploadFile cardImage = imageService.uploadCardImage(file);
        return ResponseEntity.ok(cardImage);
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteCardImage(id);
        return ResponseEntity.noContent().build();
    }

    /*
    * 이미지 전체 조회
    * */
    @GetMapping("/images")
    public ResponseEntity<List<UploadFile>> getImages() {
        List<UploadFile> images = imageService.getImages();
        return ResponseEntity.ok(images);
    }

    /*
    * 이미지 단건 조회
     */
    @GetMapping("/images/{s3Key}")
    public ResponseEntity<UploadFile> getImageByS3Key(@PathVariable String s3Key) {
        UploadFile uploadFile = imageService.getImageByS3Key(s3Key);

        return ResponseEntity.ok(uploadFile);
    }

    /*
     * 키워드 이미지URL 모두 가져오기
     * card:표지, paper:덕담지, pouch:복주머니
     * */
    @GetMapping("/images/search")
    public ResponseEntity<List<ImageResponseDto>> getUrlsByKeyword(@RequestParam String keyword) {
        List<ImageResponseDto> result = imageService.getUrlsByKeyword(keyword);
        return ResponseEntity.ok(result);
    }


}
