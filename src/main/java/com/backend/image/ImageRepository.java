package com.backend.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<UploadFile, Long>, ImageCustomRepository {
    List<UploadFile> findByS3KeyIn(List<String> existingKeys);

    UploadFile findByS3Key(String s3Key);
}
