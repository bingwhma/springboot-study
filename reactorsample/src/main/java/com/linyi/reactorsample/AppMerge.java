package com.linyi.reactorsample;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import reactor.core.publisher.Flux;

public class AppMerge {

	public static void main( String[] args ) {
		Flux.merge(Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(5), Flux.interval(Duration.of(50, ChronoUnit.MILLIS)).take(5))
        .toStream()
        .forEach(System.out::println);
		
		Flux.mergeSequential(Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(5), Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(5))
        .toStream()
        .forEach(System.out::println);
	}
}
