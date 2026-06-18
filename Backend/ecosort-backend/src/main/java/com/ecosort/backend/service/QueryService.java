package com.ecosort.backend.service;

import com.ecosort.backend.dto.*;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    private final RagService ragService;
    private final FileReaderService fileReaderService;
    private final GraniteService graniteService;

    public QueryService(
            RagService ragService,
            FileReaderService fileReaderService,
            GraniteService graniteService) {

        this.ragService = ragService;
        this.fileReaderService = fileReaderService;
        this.graniteService = graniteService;
    }

    public QueryResponse processQuestion(
            String question) {

        RetrievalResult retrievalResult =
                ragService.getRelevantDocument(
                        question);

        String sourceDocument =
                retrievalResult.getDocument();

        String context =
                fileReaderService.readFile(
                        sourceDocument);

        String answer =
                graniteService.generateAnswer(
                        question,
                        context);

        return new QueryResponse(
                answer,
                sourceDocument,
                retrievalResult.getConfidence()
        );
    }
}