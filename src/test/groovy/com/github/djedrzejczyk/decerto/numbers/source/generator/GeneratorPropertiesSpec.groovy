package com.github.djedrzejczyk.decerto.numbers.source.generator


import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Unroll
class GeneratorPropertiesSpec extends Specification {

    @Subject
    GeneratorProperties properties

    void 'should create new instance when min is #min and max is #max'() {
        when:
            properties = new GeneratorProperties(max, min)
        then:
            noExceptionThrown()
        where:
            min  | max
            -100 | 100
            0    | 0
            1    | 2
    }

    void 'should throw IllegalArgumentException when min is #min and max is #max'() {
        when:
            properties = new GeneratorProperties(max, min)
        then:
            IllegalArgumentException exception = thrown()
            exception.message == 'Incorrect configuration of generator properties - min value higher than max.'
        where:
            min | max
            100 | -100
            2   | 1
    }

}
