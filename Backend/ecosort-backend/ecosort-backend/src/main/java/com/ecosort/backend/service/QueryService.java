package com.ecosort.backend.service;

import com.ecosort.backend.dto.QueryResponse;
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

        String sourceDocument =
                ragService.getRelevantDocument(
                        question);

        String context =
                fileReaderService.readFile(
                        sourceDocument);

        String answer =
                graniteService.generateAnswer(
                        question,
                        context);

        return new QueryResponse(
                answer,
                sourceDocument);
    }
}