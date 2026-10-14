# Spring Boot OpenAI RAG with PGVector

A small RAG demo using Java 21, Spring Boot, Spring AI, OpenAI and PostgreSQL pgvector.

## Run

Copy `.env.example` to `.env`, set `OPENAI_API_KEY`, then run:

```bash
docker compose up --build -d
```

Add a document:

```bash
curl -X POST http://localhost:8094/api/documents -H "Content-Type: application/json" -d '{"source":"return-policy","title":"Return Policy","content":"Customers may return unopened products within 30 days with the original receipt."}'
```

Ask a question:

```bash
curl -X POST http://localhost:8094/api/questions -H "Content-Type: application/json" -d '{"question":"How many days do I have to return an unopened product?"}'
```

The response contains the generated answer and the retrieved sources.
