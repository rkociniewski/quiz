package org.powermilk.quiz.repository

import org.powermilk.quiz.model.Answer
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<Answer, Long>