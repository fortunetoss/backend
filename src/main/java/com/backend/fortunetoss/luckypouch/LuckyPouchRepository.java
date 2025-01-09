package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.shape.Shape;
import com.backend.fortunetoss.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LuckyPouchRepository extends JpaRepository<LuckyPouch, Long> {

    Optional<LuckyPouch> findByUserAndShapeAndQuestionCustomIsNull(User user, Shape shape);
}
