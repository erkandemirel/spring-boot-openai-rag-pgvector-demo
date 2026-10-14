package com.example.rag;

import jakarta.validation.constraints.NotBlank;

record DocumentRequest(
        @NotBlank String source,
        @NotBlank String title,
        @NotBlank String content
) {
}
