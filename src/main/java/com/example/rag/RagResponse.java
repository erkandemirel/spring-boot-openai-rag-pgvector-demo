package com.example.rag;

import java.util.List;

record RagResponse(String answer, List<SourceReference> sources) {
    record SourceReference(String source, String title, Double score) {
    }
}
