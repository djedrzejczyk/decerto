package com.github.djedrzejczyk.decerto.numbers.integers;

import com.github.djedrzejczyk.decerto.numbers.domain.JoiningStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IntegerSumStrategy implements JoiningStrategy<Integer> {

    @Override
    public Integer join(List<Integer> values) {
        return values.stream().reduce(0, Integer::sum);
    }
}
