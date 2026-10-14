package com.example.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AiConfiguration {
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                You are an enterprise knowledge assistant.
                Answer only from the supplied context.
                If the context does not contain the answer, say that you do not know.
                Keep the answer concise and do not invent facts.
                """).build();
    }
}
