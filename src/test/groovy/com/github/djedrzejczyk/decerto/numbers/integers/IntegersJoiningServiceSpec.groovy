package com.github.djedrzejczyk.decerto.numbers.integers

import com.github.djedrzejczyk.decerto.numbers.domain.DataSource
import com.github.djedrzejczyk.decerto.numbers.domain.JoiningStrategy
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Unroll
class IntegersJoiningServiceSpec extends Specification {

    JoiningStrategy<Integer> strategy = Mock()

    @Subject
    IntegersJoiningService service

    void 'should execute JoiningStrategy on all data source results'() {
        given:
            service = new IntegersJoiningService(sources, strategy)
        when:
            service.getNext()
        then:
            1 * strategy.join(sourcesOutput)
        where:
            sourcesOutput   | sources
            []              | []
            [100]           | [{ 100 } as DataSource<Integer>]
            [101, 202, 303] | [{ 101 } as DataSource<Integer>, { 202 } as DataSource<Integer>, { 303 } as DataSource<Integer>]
    }

    void 'should propagate exception thrown by DataSource'() {
        given:
            NullPointerException exception = new NullPointerException("Test message")
            List<DataSource<Integer>> sources = [{ 1 } as DataSource<Integer>, { throw exception } as DataSource<Integer>]
        and:
            service = new IntegersJoiningService(sources, strategy)
        when:
            service.getNext()
        then:
            NullPointerException thrownException = thrown()
            thrownException == exception
    }

    void 'should propagate exception thrown by JoiningStrategy'() {
        given:
            NullPointerException exception = new NullPointerException("Test message")
            strategy.join([]) >> { throw exception }
        and:
            service = new IntegersJoiningService([], strategy)
        when:
            service.getNext()
        then:
            NullPointerException thrownException = thrown()
            thrownException == exception
    }
}
