package com.cgi.boat.interview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

class PeopleProcessor {
    /**
     * Returns a {@link Map} where keys are first names and values lists of all last names
     * of people from the input list who have the first name
     * equal to the key.
     *
     * Example:
     * For input: ["John Doe", "John Silver", "Peter Doe"]
     * Expected result would be:
     * {
     *  "John" -> ["Doe", "Silver"]
     *  "Peter" -> ["Doe"]
     * }
     */
    static Map<String, List<String>> lastnamesByFirstname(List<Person> people) {
        return createMap(people, Person::getFirstName, Person::getLastName);
    }


    /**
     * Same as {@link PeopleProcessor#lastnamesByFirstname} except that the mapping
     * returned is lastname -> firstnames
     *
     * Example:
     * For input: ["John Doe", "John Silver", "Peter Doe"]
     * Expected result would be:
     * {
     *  "Doe" -> ["John", "Peter"]
     *  "Silver" -> ["John"]
     *
     */
    static Map<String, List<String>> firstnamesByLastname(List<Person> people) {
        return createMap(people, Person::getLastName, Person::getFirstName);
    }

    private static Map<String, List<String>> createMap(List<Person> people,
                                                       Function<Person, String> keyFunction,
                                                       Function<Person, String> valueFunction) {
        if (people == null || people.isEmpty()) {
            return new HashMap<>();
        }

        return people.stream()
                .filter(Objects::nonNull)
                .collect(groupingBy(person -> ofNullable(keyFunction.apply(person))
                                .orElse("N/A").toUpperCase(),
                        mapping(person -> ofNullable(valueFunction.apply(person))
                                .orElse("N/A").toUpperCase(), toList())));
    }
}
