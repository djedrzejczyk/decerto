package com.github.djedrzejczyk.decerto.numbers.integers

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class IntegerSumStrategySpec extends Specification {

    @Subject
    IntegerSumStrategy strategy = new IntegerSumStrategy()

    @Unroll
    void 'should return sum (#sum) of all ints in list #list'() {
        expect:
            strategy.join(list) == sum
        where:
            list                   || sum
            [123]                  || 123
            [54, -12]              || 42
            [60, 12, 0, 5, -8, 11] || 80
    }

    void 'should return 0 when empty list'() {
        expect:
            strategy.join([]) == 0
    }

    void 'should throw NullPointerException when null parameter'() {
        when:
            strategy.join(null)
        then:
            thrown(NullPointerException)
    }
}
