package com.domain.apipostgress.repository;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 4:18
 */

import com.domain.apipostgress.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}