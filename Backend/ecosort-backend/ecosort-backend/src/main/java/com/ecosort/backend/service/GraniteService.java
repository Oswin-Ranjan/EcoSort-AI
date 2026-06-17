package com.ecosort.backend.service;

import org.springframework.stereotype.Service;

@Service
public class GraniteService {

    public String generateAnswer(
            String question,
            String context) {

        String[] lines = context.split("\n");

        StringBuilder answer = new StringBuilder();

        int count = 0;

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.toLowerCase().contains("guide")) {
                continue;
            }

            answer.append("• ")
                    .append(line)
                    .append("\n\n");

            count++;

            if (count >= 3) {
                break;
            }
        }

        return answer.toString();
    }
}