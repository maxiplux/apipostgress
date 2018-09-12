package com.domain.apipostgress.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 10:17
 */
public class Maper {
    Question question;
    List<Answer> questions = new ArrayList<>();

    public Maper(Question question, List<Answer> questions) {
        this.question = question;
        this.questions = questions;
    }

    public Maper() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Answer> questions) {
        this.questions = questions;
    }
}
