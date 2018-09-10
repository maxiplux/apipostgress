package com.domain.apipostgress.repository;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 4:20
 */

import com.domain.apipostgress.model.Answer;
import com.domain.apipostgress.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {


}
