package org.powermilk.quiz.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
data class Answer(
    private val text: String,

    @ManyToOne
    @JsonIgnore
    private val question: Question
)
