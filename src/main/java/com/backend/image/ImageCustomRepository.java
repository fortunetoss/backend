package com.backend.image;

import java.util.List;

public interface ImageCustomRepository {

    List<String> findUrlsByS3KeyContaining(String keyword);

}

