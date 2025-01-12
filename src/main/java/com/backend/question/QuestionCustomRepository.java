package com.backend.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCustomRepository extends JpaRepository<QuestionCustom, Long>, QuestionCustomInterRepository {
}
