package com.domain.apipostgress.controller;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 4:19
 */

import com.domain.apipostgress.exceptions.ResourceNotFoundException;
import com.domain.apipostgress.model.Answer;
import com.domain.apipostgress.model.Maper;
import com.domain.apipostgress.model.Pair;
import com.domain.apipostgress.model.Question;
import com.domain.apipostgress.repository.AnswerRepository;
import com.domain.apipostgress.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/questions")
    public Page<Question> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }


    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {

        return questionRepository.save(question);

    }

    @GetMapping("/questions/{questionId}")
    public Question getQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));

    }


    @PostMapping("/questions/bulk")
    public List<Question> createQuestion(@Valid @RequestBody HashMap<Question, List<Answer>> questions) {


        return questions.entrySet().stream().map(question ->
        {
            Question questionFinal = questionRepository.save(question.getKey());
            List<Answer> answers = question.getValue();
            answers.stream().map(answer -> {
                answer.setQuestion(questionFinal);
                return answerRepository.save(answer);
            }).collect(Collectors.toList());
            return questionFinal;
        }).collect(Collectors.toList());

    }

    @PostMapping("/questions/bulk2")
    public Question createQuestion(@Valid @RequestBody Maper maper) {


        Question questionFinal = questionRepository.save(maper.getQuestion());
        this.questionRepository.save(questionFinal);
        List<Answer> answers = maper.getQuestions();
        answers.stream().map(answer -> {
            answer.setQuestion(questionFinal);
            return answerRepository.save(answer);
        });
        return questionFinal;


    }

    @PostMapping("/questions/bulk3")
    public List<Question> createQuestion(@Valid @RequestBody List<Pair<Question, List<Answer>>> questions) {


        return questions.stream().map(question -> {
            Question questionaFinal = this.questionRepository.save(question.getKey());
            question.getValue().stream().map(answer -> answer.setQuestion(questionaFinal)).map(answer -> this.answerRepository.save(answer));
            return questionaFinal;
        }).collect(Collectors.toList());


    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }


    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }
}
