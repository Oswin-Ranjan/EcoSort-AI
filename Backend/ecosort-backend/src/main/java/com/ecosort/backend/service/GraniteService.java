package com.ecosort.backend.service;

import com.ecosort.backend.dto.HFMessage;
import com.ecosort.backend.dto.HFRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GraniteService {

    @Value("${hf.token}")
    private String token;

    @Value("${hf.model}")
    private String model;

    private final RestTemplate restTemplate =
            new RestTemplate();

    public String generateAnswer(
            String question,
            String context) {

        try {

            if(context.length() > 1500) {

                context =
                        context.substring(
                                0,
                                2500
                        );
            }

            String prompt =
                    """
                    You are EcoSort AI, a sustainability assistant.

                    Context:
                    %s

                    Question:
                    %s

                    Instructions:
                    - Use only the supplied context.
                    - Give a concise answer.
                    - Mention environmental safety when relevant.
                    - Limit answer to 4 sentences.
                    """
                            .formatted(
                                    context,
                                    question
                            );

            HFRequest request =
                    new HFRequest(
                            model,
                            List.of(
                                    new HFMessage(
                                            "user",
                                            prompt
                                    )
                            ),
                            0.3,
                            120
                    );

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setBearerAuth(
                    token
            );

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            HttpEntity<HFRequest> entity =
                    new HttpEntity<>(
                            request,
                            headers
                    );

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            "https://router.huggingface.co/v1/chat/completions",
                            entity,
                            Map.class
                    );

            Map choice =
                    (Map)((List)
                            response.getBody()
                                    .get("choices"))
                            .get(0);

            Map message =
                    (Map) choice.get("message");

            return message
                    .get("content")
                    .toString();

        } catch (Exception e) {

            e.printStackTrace();

            return "Unable to generate AI response.";
        }
    }
}