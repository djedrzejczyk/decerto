package com.github.djedrzejczyk.decerto.numbers.integers;

import com.github.djedrzejczyk.decerto.numbers.domain.DataSource;
import com.github.djedrzejczyk.decerto.numbers.domain.JoiningStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegersJoiningService {

    List<DataSource<Integer>> sources;

    JoiningStrategy<Integer> strategy;

    IntegersJoiningService(List<DataSource<Integer>> sources, JoiningStrategy<Integer> strategy) {
        this.sources = sources;
        this.strategy = strategy;
    }

    public Integer getNext() {
        List<Integer> values = sources.stream().map(DataSource::get).collect(Collectors.toList());
        return strategy.join(values);
    }
}
