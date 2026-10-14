package com.example.rag;

import jakarta.validation.constraints.NotBlank;

record QuestionRequest(@NotBlank String question) {
}
