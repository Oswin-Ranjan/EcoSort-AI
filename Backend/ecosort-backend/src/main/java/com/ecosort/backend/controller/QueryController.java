package com.ecosort.backend.controller;

import com.ecosort.backend.dto.QueryRequest;
import com.ecosort.backend.dto.QueryResponse;
import com.ecosort.backend.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @PostMapping
    public QueryResponse askQuestion(
            @RequestBody QueryRequest request) {

        return queryService.processQuestion(
                request.getQuestion()
        );
    }
}