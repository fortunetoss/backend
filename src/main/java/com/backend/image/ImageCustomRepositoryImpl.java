package com.backend.image;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.image.QUploadFile.uploadFile;

@Repository
@RequiredArgsConstructor
public class ImageCustomRepositoryImpl implements ImageCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    /*
     * 키워드 이미지URL 모두 가져오기
     * card:표지, paper:덕담지, pouch:복주머니
     * 키워드가 존재하는 이미지는 모두 가져온다.
     * */
    @Override
    public List<String> findUrlsByS3KeyContaining(String keyword) {
        return jpaQueryFactory
                .select(uploadFile.url)
                .from(uploadFile)
                .where(uploadFile.s3Key.containsIgnoreCase(keyword))
                .fetch();
    }
}
