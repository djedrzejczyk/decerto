package com.github.djedrzejczyk.decerto.numbers.source.api;

import com.github.djedrzejczyk.decerto.numbers.domain.DataSource;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
@Service
class RandomOrgApi implements DataSource<Integer> {

    private final RandomOrgClient client;

    private final RandomOrgProperties properties;

    private final Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    private final AtomicLong nextId = new AtomicLong(1L);

    RandomOrgApi(RandomOrgClient client, RandomOrgProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public Integer get() {
        if (queue.isEmpty()) {
            fillQueue();
        }
        return queue.poll();
    }

    private void fillQueue() {
        // Possible improvement:
        // we can make this operation every time when queue size is lower than some number of elements
        // that will optimize access time
        try {
            RandomOrgRequest request = new RandomOrgRequest(
                    nextId.getAndIncrement(),
                    properties.getApiKey(),
                    properties.getNumbersPerRequest(),
                    properties.getMin(),
                    properties.getMax());

            RandomOrgResponse response = client.generate(request);

            if (response.getError() == null) {
                checkLimits(response);
                queue.addAll(response.getResult().getRandom().getData());
            } else {
                log.error("Problem with random.org API: {}", response.getError().getMessage());
                queue.add(0);
            }

        } catch (FeignException exception) {
            // What if service is unavailable?
            log.error("Problem with random.org API - status {}", exception.status(), exception);
            queue.add(0); // i will add just single 0 but it should be business decision
        }
    }

    private void checkLimits(RandomOrgResponse response) {
        long bitsLimit = properties.getBitsLimit();
        Long bitsLeft = response.getResult().getBitsLeft();
        if (bitsLimit > 0 && bitsLeft <= bitsLimit - properties.getBitsLimitAlertThreshold()) {
            log.warn("System reaches API limits - bits left {}", bitsLeft);
        }

        int requestsLimit = properties.getRequestsLimit();
        Integer requestsLeft = response.getResult().getRequestsLeft();
        if (requestsLimit > 0 && requestsLeft <= requestsLimit - properties.getRequestsLimitAlertThreshold()) {
            log.warn("System reaches API limits - requests left {}", requestsLeft);
        }
    }
}
