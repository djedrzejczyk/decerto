package com.github.djedrzejczyk.decerto.numbers.source.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;

@Getter
@AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = PACKAGE)
@JsonIgnoreProperties(ignoreUnknown = true)
class RandomOrgResponse {

    @Getter
    @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = PACKAGE)
    static class Result {

        private final Data random;

        private final Long bitsUsed;

        private final Long bitsLeft;

        private final Integer requestsLeft;

        private final Integer advisoryDelay;
    }

    @Getter
    @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = PACKAGE)
    static class Data {

        private final List<Integer> data;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssz")
        private final ZonedDateTime completionTime;
    }

    @Getter
    @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = PACKAGE)
    static class Error {

        private final int code;

        private final String message;
    }

    private final Result result;

    private final Error error;

    private final Integer id;
}