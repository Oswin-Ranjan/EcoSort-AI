package com.ecosort.backend.service;

import com.ecosort.backend.dto.QueryResponse;
import com.ecosort.backend.dto.RetrievalResult;
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

        int confidence =
                retrievalResult.getConfidence();

        String answer;

        if (confidence <= 40) {

            answer =
                    "I could not find a reliable answer in the sustainability knowledge base.\n\n" +
                            "Try asking about:\n" +
                            "• Batteries\n" +
                            "• Plastic Recycling\n" +
                            "• E-Waste\n" +
                            "• Composting\n" +
                            "• Waste Segregation";

        } else {

            answer =
                    graniteService.generateAnswer(
                            question,
                            context);
        }

        return new QueryResponse(
                answer,
                sourceDocument,
                confidence
        );
    }
}