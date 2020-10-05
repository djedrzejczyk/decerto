package com.github.djedrzejczyk.decerto.numbers.source.generator;

import com.github.djedrzejczyk.decerto.numbers.domain.DataSource;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNumberGenerator implements DataSource<Integer> {

    private final Random random = new Random();

    private final int min;

    private final int max;

    public RandomNumberGenerator(GeneratorProperties properties) {
        this.min = properties.getMin();
        this.max = properties.getMax();
    }

    @Override
    public Integer get() {
        return random.nextInt(max - min + 1) + min;
    }
}
