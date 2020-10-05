package com.github.djedrzejczyk.decerto.numbers.domain;

import java.util.List;

public interface JoiningStrategy<T> {

    T join(List<T> values);
}
