package com.github.djedrzejczyk.decerto.numbers.source.generator

import spock.lang.Specification
import spock.lang.Subject

class RandomNumberGeneratorSpec extends Specification {

    @Subject
    RandomNumberGenerator generator

    void 'should generate random values in specific range'() {
        given:
            generator = new RandomNumberGenerator(new GeneratorProperties(max, min))
        when:
            List<Integer> generated = (0..30).collect { generator.get() }
        then:
            generated.min() == min
            generated.max() == max
        where:
            min | max
            0   | 5
            0   | 1
            1   | 1
    }
}
