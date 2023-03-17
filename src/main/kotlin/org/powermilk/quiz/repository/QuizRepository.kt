package org.powermilk.quiz.repository

import org.powermilk.quiz.model.QuizForm
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository : JpaRepository<QuizForm, Long>