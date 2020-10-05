package com.github.djedrzejczyk.decerto.numbers.source.generator;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@ConfigurationProperties(prefix = "source.generator")
@ConstructorBinding
@Validated
class GeneratorProperties {

    /**
     * Specifies the maximum value of generated numbers.
     */
    @NotNull
    private final Integer max;

    /**
     * Specifies the minimum value of generated numbers.
     */
    @NotNull
    private final Integer min;

    GeneratorProperties(Integer max, Integer min) {
        if (max < min) {
            throw new IllegalArgumentException("Incorrect configuration of generator properties - min value higher than max.");
        }

        this.max = max;
        this.min = min;
    }
}
