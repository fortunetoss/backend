package com.backend.fortunetoss.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<CustomQuestion, Long>, QuestionRepositoryCustom {


}
