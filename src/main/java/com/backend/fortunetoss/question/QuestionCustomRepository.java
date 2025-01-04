package com.backend.fortunetoss.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCustomRepository extends JpaRepository<CustomQuestion, Long> {
}
