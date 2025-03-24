package com.example.spring_reactive_mongo_jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


		List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");

		Map<String, Long> sortedWordCount = words.stream()
						.collect(Collectors.groupingBy(s -> s, Collectors.counting()))
						.entrySet().stream()
						.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
						.collect(Collectors
								.toMap(Map.Entry::getKey,
										Map.Entry::getValue,
										(e1, e2) -> e1, LinkedHashMap::new));

		System.out.println("sortedWordCount = " + sortedWordCount);

//		@Data
		@AllArgsConstructor
		@ToString
		@Getter
		class Employee {
			String id;
			String name;
		}
		List<Employee> employees = Arrays.asList(
				new Employee("1", "Alice"),
				new Employee("2", "Bob"),
				new Employee("1", "Alice"), // Duplicate
				new Employee("3", "Charlie")
		);

		List<Employee> uniqueEmployees = employees.stream()
				.distinct().collect(Collectors.toList());
		System.out.println(uniqueEmployees);

		List<Employee> uniqueEmps = employees.stream()
				.collect(Collectors.toMap(Employee::getId, e -> e, (ex, nw) -> ex))
//				.entrySet().stream()
//				.map(Map.Entry::getValue)
				.values().stream()
				.collect(Collectors.toList());

		System.out.println(uniqueEmps);

		@Getter
		@ToString
		@AllArgsConstructor
		class Staff {
			int id;
			String name;
		}

		List<Staff> staffs = Arrays.asList(
				new Staff(101, "Alice"),
				new Staff(102, "Bob"),
				new Staff(103, "Charlie"),
				new Staff(104, "David"),
				new Staff(105, "Eleanor"),
				new Staff(106, "Franklin")
		);

		Map<Integer, String> filtStaffs = staffs.stream()
				.filter(s -> s.name.length() >= 5)
				.collect(Collectors.toMap((s) -> s.id-100, Staff::getName, (e, r) -> e));
		System.out.println(filtStaffs);

		Map<Integer, String> filteredStaffs = IntStream.range(0, staffs.size())
				.filter(i -> staffs.get(i).getName().length() >= 5)
//				.peek(System.out::println)
				.boxed()
				.collect(Collectors.toMap(i -> i + 1, i-> staffs.get(i).getName()));
		System.out.println(filteredStaffs);

		// Stream API approach with correctly incrementing keys

//		Map<Integer, String> filteredStaffMap = IntStream.range(0, staffs.size())
//				.mapToObj(i -> staffs.get(i))
//				.filter(staff -> staff.getName().length() >= 5)
//				.collect(Collectors.toMap(
//						new java.util.concurrent.atomic.AtomicInteger(1)::getAndIncrement,
//						Employee::getName
//				));
//

		// AtomicInteger to generate consecutive keys
		AtomicInteger counter = new AtomicInteger(1);

		Map<Integer, String> filtStaffsMap = staffs.stream()
				.filter(staff -> staff.getName().length() >= 5) // Filter names with length >= 5
				.collect(Collectors.toMap(
						staff -> counter.getAndIncrement(), // Generates consecutive keys (1,2,3...)
						Staff::getName,
						(existing, replacement) -> existing, // Merge function (not needed, but required by toMap)
						LinkedHashMap::new // Maintain insertion order
				));

		System.out.println(filtStaffsMap);

		List<Integer> nums = Arrays.asList(3, 4, 6, 7, 4, 8, 6, 2, 9);
		int target = 10;
		Set<Integer> seen = new HashSet<>();
		Set<List<Integer>> result = new HashSet<>();

		nums.stream()
				.forEach(num -> {
					int complement = target -  num;
					if (seen.contains(complement)) {
						List<Integer> uniqPair = Arrays.asList(Math.min(num, complement), Math.max(num, complement));
						result.add(uniqPair);
					}
					seen.add(num);
				});


		@Data
		@AllArgsConstructor
		class Person {
			String name;
			int age;
			double salary;
		}
		List<Person> people = List.of(
				new Person("Raghav", 25, 50000),
				new Person("Shashi", 30, 60000),
				new Person("Sachin", 25, 55000),
				new Person("Maven", 35, 70000),
				new Person("Java", 30, 65000)
		);
		Map<Integer, Double> avgSalByAge = people.stream()
				.collect(Collectors.groupingBy(Person::getAge,
						Collectors.averagingDouble(Person::getSalary)))
				.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getKey))
//				.sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e, n) -> e, LinkedHashMap::new))
				;

		avgSalByAge.forEach((age, avgSalary) ->
				System.out.println("Age: " + age + ", Avg Salary: " + avgSalary)
		);

		String s = reverseString("Hello World!");
		System.out.println(s);
//		SpringApplication.run(SpringReactiveMongoJwtApplication.class, args);


	}

	static int i = 0;
	private static String reverseString(String s) {
//		String finalStr = s;
//		System.out.println(finalStr);
//		if (i++ < s.length()/2) {
//			finalStr = reverseString(s.substring(0, i-1) + s.charAt(s.length() - i -1) + s.substring(i+1, s.length() - i -2)  + s.charAt(i) + s.substring(s.length() - i));
//		}

		if (s == null || s.length() <= 1) return s;

		return reverseString(s.substring(1)) + s.charAt(0);
	}
}
