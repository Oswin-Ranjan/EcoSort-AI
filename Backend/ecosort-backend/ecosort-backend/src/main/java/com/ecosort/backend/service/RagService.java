package com.ecosort.backend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RagService {

    public String getRelevantDocument(String question) {

        question = question.toLowerCase();

        Map<String, Integer> scores = new HashMap<>();

        scores.put(
                "Battery_Disposal_Guide.txt",
                calculateScore(
                        question,
                        new String[]{
                                "battery",
                                "cell",
                                "lithium",
                                "aa",
                                "aaa",
                                "rechargeable",
                                "alkaline",
                                "power bank"
                        }
                )
        );

        scores.put(
                "Plastic_Recycling_Guide.txt",
                calculateScore(
                        question,
                        new String[]{
                                "plastic",
                                "bottle",
                                "container",
                                "packaging",
                                "wrapper",
                                "bag",
                                "polymer"
                        }
                )
        );

        scores.put(
                "E_Waste_Management.txt",
                calculateScore(
                        question,
                        new String[]{
                                "charger",
                                "phone",
                                "mobile",
                                "laptop",
                                "computer",
                                "electronic",
                                "e-waste",
                                "earphones",
                                "keyboard",
                                "mouse"
                        }
                )
        );

        scores.put(
                "Composting_Guide.txt",
                calculateScore(
                        question,
                        new String[]{
                                "food",
                                "organic",
                                "compost",
                                "fruit",
                                "vegetable",
                                "garden",
                                "leaf",
                                "kitchen waste"
                        }
                )
        );

        String bestDocument =
                "Waste_Segregation_Guide.txt";

        int highestScore = 0;

        for (Map.Entry<String, Integer> entry
                : scores.entrySet()) {

            if (entry.getValue() > highestScore) {

                highestScore =
                        entry.getValue();

                bestDocument =
                        entry.getKey();
            }
        }

        return bestDocument;
    }

    private int calculateScore(
            String question,
            String[] keywords) {

        int score = 0;

        for (String keyword : keywords) {

            if (question.contains(keyword)) {

                score++;
            }
        }

        return score;
    }
}