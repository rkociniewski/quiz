package org.powermilk.quiz.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Size(min = 1, max = 50, message = "The answer should be less than 50 characters")
    @NotNull(message = "No answer text provided.")
    val text: String,

    @JsonIgnore
    @ManyToOne
    val question: Question,
)
