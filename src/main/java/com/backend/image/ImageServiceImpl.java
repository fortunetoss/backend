package com.backend.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public UploadFile uploadCardImage(MultipartFile file) throws IOException {
        String s3Key = file.getOriginalFilename();

        // S3 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        amazonS3.putObject(bucket, s3Key, file.getInputStream(), metadata);

        // URL 생성
        String url = amazonS3.getUrl(bucket, s3Key).toString();

        // DB 저장
        UploadFile uploadFile = new UploadFile();
        uploadFile.setName(file.getOriginalFilename());
        uploadFile.setUrl(url);
        uploadFile.setS3Key(s3Key);
        return imageRepository.save(uploadFile);
    }

    public void deleteCardImage(Long id) {
        UploadFile uploadFile = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // S3에서 삭제
        amazonS3.deleteObject(bucket, uploadFile.getS3Key());

        // DB에서 삭제
        imageRepository.delete(uploadFile);
    }

    /*
    * 카드 이미지 가져오기
    * */
    public List<UploadFile> getImages() {

        List<UploadFile> allFiles = imageRepository.findAll();

        // S3에서 존재하는 파일의 키 목록
        List<String> existingKeys = allFiles.stream()
                .map(UploadFile::getS3Key)
                .filter(s3Key -> amazonS3.doesObjectExist(bucket, s3Key))
                .collect(Collectors.toList());

        // 데이터베이스에서 존재하는 파일의 데이터만 다시 조회
        return imageRepository.findByS3KeyIn(existingKeys);
    }

    @Override
    public UploadFile getImageByS3Key(String s3Key) {
        log.info("Fetching from repository with S3 Key: {}", s3Key);
        UploadFile file = imageRepository.findByS3Key(s3Key);
        if (file == null) {
            log.warn("No file found for S3 Key: {}", s3Key);
            throw new IllegalArgumentException("File not found for S3 Key: " + s3Key);
        }
        return file;
    }
}
