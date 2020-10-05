package com.github.djedrzejczyk.decerto.numbers.source.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${source.api.random-org.url}", name = "randomOrgClient")
interface RandomOrgClient {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    RandomOrgResponse generate(RandomOrgRequest request);
}
