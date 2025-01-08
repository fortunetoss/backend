package com.backend.fortunetoss.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<UploadFile, Long>, ImageCustomRepository {
    List<UploadFile> findByS3KeyIn(List<String> existingKeys);
}
