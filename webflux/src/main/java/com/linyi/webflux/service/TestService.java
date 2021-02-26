package com.linyi.webflux.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TestService {
    private final Map<String, TestUser> data = new ConcurrentHashMap<>();

    Flux<TestUser> list() {
        return Flux.fromIterable(this.data.values());
    }

    Flux<TestUser> getById(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    Mono<TestUser> getById(final String id) {
        return Mono.justOrEmpty(this.data.get(id))
                .switchIfEmpty(Mono.error(new Exception()));
    }

    Mono<TestUser> createOrUpdate(final TestUser user) {
        this.data.put(user.getId(), user);
        return Mono.just(user);
    }

    Mono<TestUser> delete(final String id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }
}