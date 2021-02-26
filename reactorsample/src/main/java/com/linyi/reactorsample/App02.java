package com.linyi.reactorsample;

import java.util.ArrayList;
import java.util.Random;

import reactor.core.publisher.Flux;

public class App02 {
	
	public static void main( String[] args ) {
		
		Flux.generate(sink -> {
		    sink.next("Hello");
		    sink.complete();
		}).subscribe(System.out::println);
		 
		 
		final Random random = new Random();
		Flux.generate(ArrayList::new, (list, sink) -> {
		    int value = random.nextInt(100);
		    list.add(value);
		    sink.next(value);
		    if (list.size() == 10) {
		        sink.complete();
		    }
		    return list;
		}).subscribe(System.out::println);
		
		Flux.create(sink -> {
		     for (int i = 0; i < 10; i++) {
		         sink.next(i);
		     }
		     sink.complete();
		}).subscribe(System.out::println);
    }
	
}
