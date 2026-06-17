package com.ecosort.backend.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class FileReaderService {

    public String readFile(String fileName) {

        try {

            InputStream inputStream =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(
                                    "knowledgebase/" + fileName);

            if (inputStream == null) {
                return "Knowledge file not found.";
            }

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(inputStream));

            return reader.lines()
                    .collect(Collectors.joining("\n"));

        } catch (Exception e) {

            return "Error reading knowledge base.";
        }
    }
}