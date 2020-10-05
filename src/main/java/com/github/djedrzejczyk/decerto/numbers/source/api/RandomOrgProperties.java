package com.github.djedrzejczyk.decerto.numbers.source.api;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@ConfigurationProperties(prefix = "source.api.random-org")
@ConstructorBinding
@Validated
class RandomOrgProperties {

    /**
     * Url to the random.org api.
     * Value is MANDATORY.
     *
     * @see FeignClient#url()
     */
    @NotBlank
    private final String url;

    /**
     * Specifies the maximum value of generated numbers.
     * Correct values are between -1 000 000 000 and 1 000 000 000.
     * Value is MANDATORY.
     */
    @NotNull
    @Range(min = -1_000_000_000, max = 1_000_000_000)
    private final Integer max;

    /**
     * Specifies the minimum value of generated numbers.
     * Correct values are between -1 000 000 000 and 1 000 000 000.
     * Value is MANDATORY.
     */
    @NotNull
    @Range(min = -1_000_000_000, max = 1_000_000_000)
    private final Integer min;

    /**
     * Specifies API key to the random.org api.
     * Value is MANDATORY.
     */
    @NotBlank
    private final String apiKey;

    /**
     * Specifies length of generated numbers list in the single request to api.
     * Value is MANDATORY.
     */
    @NotNull
    @Range(min = 1, max = 10_000)
    private final Integer numbersPerRequest;

    /**
     * Specifies daily limit of bits (default: 0, no limit).
     */
    @Min(0)
    private final long bitsLimit;

    /**
     * Specifies daily limit of requests (default: 0, no limit).
     */
    @Min(0)
    private final int requestsLimit;

    /**
     * Specifies alert threshold percent (default: 80).
     * When system will consume more or equal percent of available daily limits,
     * warning will be logged in every next call of the API.
     */
    @Range(min = 0, max = 100)
    private final int limitAlertThreshold;

    /**
     * Specifies daily limit of bits that will trigger warn logging.
     * If not specified, limitAlertThreshold value will be used to calculate this value.
     */
    @Min(0)
    private final Long bitsLimitAlertThreshold;

    /**
     * Specifies daily limit of requests that will trigger warn logging.
     * If not specified, limitAlertThreshold value will be used to calculate this value.
     */
    @Min(0)
    private final Integer requestsLimitAlertThreshold;

    RandomOrgProperties(String url, Integer max, Integer min, String apiKey, Integer numbersPerRequest,
                        Long bitsLimit, Integer requestsLimit, Integer limitAlertThreshold, Long bitsLimitAlertThreshold, Integer requestsLimitAlertThreshold) {
        if (max < min) {
            throw new IllegalArgumentException("Incorrect configuration of random.org properties - min value higher than max.");
        }

        this.url = url;
        this.max = max;
        this.min = min;
        this.apiKey = apiKey;
        this.numbersPerRequest = numbersPerRequest;

        this.limitAlertThreshold = Optional.ofNullable(limitAlertThreshold).orElse(80);
        this.bitsLimit = Optional.ofNullable(bitsLimit).orElse(0L);
        this.requestsLimit = Optional.ofNullable(requestsLimit).orElse(0);
        this.bitsLimitAlertThreshold = Optional.ofNullable(bitsLimitAlertThreshold).orElse(this.bitsLimit * this.limitAlertThreshold / 100);
        this.requestsLimitAlertThreshold = Optional.ofNullable(requestsLimitAlertThreshold).orElse(this.requestsLimit * this.limitAlertThreshold / 100);
    }
}
