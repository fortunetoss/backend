package com.backend.luckypouch;

import com.backend.shape.Shape;
import com.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LuckyPouchRepository extends JpaRepository<LuckyPouch, Long> , LuckyPouchCustomRepository{

    Optional<LuckyPouch> findByUserAndShapeAndQuestionCustomIsNull(User user, Shape shape);
}
