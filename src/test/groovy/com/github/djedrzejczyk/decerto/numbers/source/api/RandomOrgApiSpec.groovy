package com.github.djedrzejczyk.decerto.numbers.source.api

import feign.FeignException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

// It would be useful to add some integration tests with wiremock
// to test application behavior when api not available/slow/unstable, etc.
@Unroll
class RandomOrgApiSpec extends Specification {

    private static final String API_KEY = 'API KEY'
    private static final String URL = 'http://test.url/api'
    private static final int MIN = 1
    private static final int MAX = 100
    private static final int COUNT = 5

    @Shared
    List<Integer> randomNumbers = [1, 6, 5, 3, 5]

    @Shared
    RandomOrgResponse.Result defaultResult = new RandomOrgResponse.Result(
            new RandomOrgResponse.Data(randomNumbers, null),
            20,
            100523,
            967,
            980)

    RandomOrgResponse response = new RandomOrgResponse(defaultResult, null, null)

    RandomOrgProperties properties = new RandomOrgProperties(
            URL,
            MAX,
            MIN,
            API_KEY,
            COUNT,
            200000,
            1000,
            null,
            null,
            null)

    RandomOrgClient client = Mock()

    @Subject
    RandomOrgApi api = new RandomOrgApi(client, properties)

    void 'should call api when first time executed'() {
        when:
            api.get()
        then:
            1 * client.generate({
                it.params.apiKey == API_KEY
                it.params.n == COUNT
                it.params.min == MIN
                it.params.max == MAX
            } as RandomOrgRequest) >> response
    }

    void 'should return #count times value without calling api again'() {
        given:
            RandomOrgResponse.Result result = new RandomOrgResponse.Result(
                    new RandomOrgResponse.Data(data, null),
                    20,
                    100523,
                    967,
                    980)
        and:
            response = new RandomOrgResponse(result, null, null)
        when:
            (1..count).forEach { api.get() }
        then:
            1 * client.generate(_) >> response
        where:
            data               | count
            [1]                | 1
            [11, 53]           | 2
            [10, 12, 15, 1, 5] | 5
    }

    void 'should return all values returned by api'() {
        given:
            client.generate(_ as RandomOrgRequest) >> response
        when:
            List<Integer> list = (1..COUNT).collect { api.get() }
        then:
            list == randomNumbers
    }

    void 'should call api again when all values from last call returned'() {
        when:
            (1..COUNT + 1).collect { api.get() }
        then:
            2 * client.generate(_) >> response
    }

    void 'should return 0 when api returned errors'() {
        given:
            String errorMessage = 'error message'
            response = new RandomOrgResponse(null, new RandomOrgResponse.Error(1000, errorMessage), null)
        and:
            client.generate(_ as RandomOrgRequest) >> response
        expect:
            api.get() == 0
    }

    void 'should return 0 when FeignException thrown'() {
        client.generate(_ as RandomOrgRequest) >> { throw new FeignException(500, 'Internal server error') }
        expect:
            api.get() == 0
    }

    void 'should return api response even when limits reached'() {
        given:
            List<Integer> data = [10, 12, 15, 1, 5]
            RandomOrgResponse.Result result = new RandomOrgResponse.Result(
                    new RandomOrgResponse.Data(data, null),
                    20,
                    0,
                    0,
                    980)
        and:
            response = new RandomOrgResponse(result, null, null)
        and:
            client.generate(_ as RandomOrgRequest) >> response
        when:
            List<Integer> list = (1..5).collect { api.get() }
        then:
            list == data
    }
}
