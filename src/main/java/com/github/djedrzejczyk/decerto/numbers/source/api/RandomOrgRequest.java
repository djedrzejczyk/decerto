package com.github.djedrzejczyk.decerto.numbers.source.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
final class RandomOrgRequest {

    @Getter
    @AllArgsConstructor
    private static class Params {

        private final String apiKey;

        private final int n;

        private final int min;

        private final int max;
    }

    private final String jsonrpc = "2.0";

    private final String method = "generateIntegers";

    private final Params params;

    private final long id;

    RandomOrgRequest(long id, String apiKey, int n, int min, int max) {
        this.id = id;
        this.params = new Params(apiKey, n, min, max);
    }
}