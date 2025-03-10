package com.example.spring_reactive_mongo_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringReactiveMongoJwtApplication {

	public static void main(String[] args) {
		 List<Integer> primes = new ArrayList<>();
		 primes.add(5);
		 primes.add(2);
		 primes.add(5);
		 primes.add(3);
		 primes.add(5);
		 primes.add(11);
		 primes.add(7);
		 primes.add(7);
		 primes.add(3);
		 primes.add(11);

//		 List<Integer> uniqPrimes = primes.stream()
//		                             .sorted()
				 					//.distinct()
//				 					.collect(Collectors.toList());
		Map<Integer, Long> countMap = primes.stream()
		.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		 System.out.println(countMap);


		List<String> memberNames = new ArrayList<>();
		memberNames.add("Amitabh");
		memberNames.add("Shekhar");
		memberNames.add("Aman");
		memberNames.add("Rahul");
		memberNames.add("Shahrukh");
		memberNames.add("Salman");
		memberNames.add("Yana");
		memberNames.add("Lokesh");

//		List<String> filteredNames = memberNames.stream()
//				.filter(s -> s.length() > 5)
//				.collect(Collectors.toList());
		String joinedNames = memberNames.stream().collect(Collectors.joining());

//		System.out.println("filteredNames = "  + filteredNames);
		System.out.println("joinedNames = "  + joinedNames);


		SpringApplication.run(SpringReactiveMongoJwtApplication.class, args);


	}

}
