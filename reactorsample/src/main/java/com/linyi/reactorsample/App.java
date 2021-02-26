package com.linyi.reactorsample;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import reactor.core.publisher.Flux;

public class App {
	
	public static void main( String[] args ) {
		
		Flux.just("Hello", "World").subscribe(System.out::println);
		Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
		Flux.empty().subscribe(System.out::println);
		Flux.range(1, 10).subscribe(System.out::println);
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }
	
}
