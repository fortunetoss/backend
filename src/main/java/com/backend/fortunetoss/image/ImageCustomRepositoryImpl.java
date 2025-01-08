package com.backend.fortunetoss.image;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.fortunetoss.image.QUploadFile.uploadFile;

@Repository
@RequiredArgsConstructor
public class ImageCustomRepositoryImpl implements ImageCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

}
