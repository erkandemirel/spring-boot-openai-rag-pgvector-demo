package com.example.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
class DocumentService {
    private final VectorStore vectorStore;
    private final TokenTextSplitter splitter = TokenTextSplitter.builder().build();

    DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    int add(DocumentRequest request) {
        Document document = new Document(
                request.content(),
                Map.of("source", request.source(), "title", request.title()));
        List<Document> chunks = splitter.apply(List.of(document));
        vectorStore.add(chunks);
        return chunks.size();
    }
}
