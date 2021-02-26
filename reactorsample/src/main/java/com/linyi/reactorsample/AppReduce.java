package com.linyi.reactorsample;

import reactor.core.publisher.Flux;

public class AppReduce {

	public static void main(String[] args) {
		
		Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
		Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
		
	}
}
