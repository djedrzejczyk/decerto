package com.github.djedrzejczyk.decerto.numbers.source.api

import spock.lang.Specification
import spock.lang.Subject

class RandomOrgPropertiesSpec extends Specification {

    @Subject
    RandomOrgProperties properties

    void 'should throw IllegalArgumentException when max is lower than min'() {
        given:
            int min = 12
            int max = 10
        when:
            new RandomOrgProperties(null, max, min, null, null, null, null, null, null, null)
        then:
            IllegalArgumentException exception = thrown()
        and:
            exception.message == 'Incorrect configuration of random.org properties - min value higher than max.'
    }

    void 'should set default values when null in constructor'() {
        when:
            properties = new RandomOrgProperties(null, 10, 1, null, null, null, null, null, null, null)
        then:
            with(properties) {
                bitsLimit == 0
                requestsLimit == 0
                limitAlertThreshold == 80
                bitsLimitAlertThreshold == 0
                requestsLimitAlertThreshold == 0
            }
    }

    void 'should calculate bitsLimitAlertThreshold and requestsLimitAlertThreshold when null in constructor'() {
        given:
            long bitsLimit = 1000
            int requestsLimit = 100
            int alertThreshold = 70
        when:
            properties = new RandomOrgProperties(null, 10, 1, null, null, bitsLimit, requestsLimit, alertThreshold, null, null)
        then:
            with(properties) {
                it.bitsLimit == bitsLimit
                it.requestsLimit == requestsLimit
                limitAlertThreshold == alertThreshold
                bitsLimitAlertThreshold == 700
                requestsLimitAlertThreshold == 70
            }
    }

    void 'should set bitsLimitAlertThreshold and requestsLimitAlertThreshold when given in constructor'() {
        given:
            long bitsLimit = 1000
            int requestsLimit = 100
            int alertThreshold = 70
            long bitsAlertThreshold = 300
            int requestsAlertThreshold = 20
        when:
            properties = new RandomOrgProperties(null, 10, 1, null, null, bitsLimit, requestsLimit, alertThreshold, bitsAlertThreshold, requestsAlertThreshold)
        then:
            with(properties) {
                it.bitsLimit == bitsLimit
                it.requestsLimit == requestsLimit
                limitAlertThreshold == alertThreshold
                bitsLimitAlertThreshold == bitsAlertThreshold
                requestsLimitAlertThreshold == requestsAlertThreshold
            }
    }

    void 'should set values given in constructor'() {
        given:
            String url = 'http://test.url/api'
            int max = 10
            int min = 1
            String apiKey = 'api key'
            int numberPerRequest = 5
        when:
            properties = new RandomOrgProperties(url, max, min, apiKey, numberPerRequest, null, null, null, null, null)
        then:
            with(properties) {
                it.url == url
                it.max == max
                it.min == min
                it.apiKey == apiKey
                it.numbersPerRequest == numberPerRequest
            }
    }

}
