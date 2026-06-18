package com.ecosort.backend.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.InputStream;

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

            PDDocument document =
                    Loader.loadPDF(
                            inputStream.readAllBytes()
                    );

            PDFTextStripper stripper =
                    new PDFTextStripper();

            String text =
                    stripper.getText(document);

            document.close();

            return text;

        } catch (Exception e) {

            e.printStackTrace();

            return "Error reading PDF knowledge base.";
        }
    }
}