package com.backend.image;

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

    @GetMapping("/images")
    public ResponseEntity<List<UploadFile>> getImages() {
        List<UploadFile> images = imageService.getImages();
        return ResponseEntity.ok(images);
    }
}
