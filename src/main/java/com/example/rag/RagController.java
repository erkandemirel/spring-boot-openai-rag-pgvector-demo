package com.example.rag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
class RagController {
    private final DocumentService documentService;
    private final RagService ragService;

    RagController(DocumentService documentService, RagService ragService) {
        this.documentService = documentService;
        this.ragService = ragService;
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    Map<String, Object> addDocument(@Valid @RequestBody DocumentRequest request) {
        return Map.of("source", request.source(), "chunksCreated", documentService.add(request));
    }

    @PostMapping("/questions")
    RagResponse ask(@Valid @RequestBody QuestionRequest request) {
        return ragService.ask(request.question());
    }
}
