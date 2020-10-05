package com.github.djedrzejczyk.decerto.numbers.api;

import com.github.djedrzejczyk.decerto.numbers.integers.IntegersJoiningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/random")
public class RandomIntegerSumResource {

    private final IntegersJoiningService service;

    public RandomIntegerSumResource(IntegersJoiningService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getList() {
        return ResponseEntity.ok(service.getNext());
    }
}
