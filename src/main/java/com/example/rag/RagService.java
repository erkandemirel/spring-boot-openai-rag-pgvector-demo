package com.example.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RagService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    RagService(VectorStore vectorStore, ChatClient chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    RagResponse ask(String question) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder().query(question).topK(4).similarityThreshold(0.35).build());

        String context = documents.stream()
                .map(document -> "Source: %s\nTitle: %s\n%s".formatted(
                        document.getMetadata().get("source"),
                        document.getMetadata().get("title"),
                        document.getText()))
                .reduce((left, right) -> left + "\n\n---\n\n" + right)
                .orElse("No relevant context was found.");

        String answer = chatClient.prompt()
                .user(user -> user.text("""
                        Context:
                        {context}

                        Question: {question}
                        """).param("context", context).param("question", question))
                .call()
                .content();

        List<RagResponse.SourceReference> sources = documents.stream()
                .map(document -> new RagResponse.SourceReference(
                        String.valueOf(document.getMetadata().get("source")),
                        String.valueOf(document.getMetadata().get("title")),
                        document.getScore()))
                .distinct()
                .toList();
        return new RagResponse(answer, sources);
    }
}
