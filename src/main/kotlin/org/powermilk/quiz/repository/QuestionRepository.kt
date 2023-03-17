package org.powermilk.quiz.repository

import org.powermilk.quiz.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long>