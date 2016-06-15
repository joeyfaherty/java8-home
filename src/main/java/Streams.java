import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by joey on 6/15/16.
 */
public class Streams {

    public static void main(String[] args) {
        manipulateList();
        manipulateListUsingStreamDirectly();
        averageOfIntList();
        averageOfIntListParseFromString();
        processingOrder();
        testFlatMap();
        testFlatMapNeater();
    }

    /**
     *  all functions must be non-interfering and stateless
     *
     *  non-interfering: never modify the source of the stream
     *
     *  stateless: no lambda within the function depends on any mutable
     *  variables or states from the outer scope which may change during execution
     */
    private static void manipulateList() {
        List<String> myList = Arrays.asList("a1", "a2", "b1", "c4", "c1");
        // intermediate operations return a stream so we can chain operations without a semi-colon
        myList
                .stream()
                /* intermediate operations return a stream so we can chain multiple intermediate operations */
                // filter is a intermediate operation
                .filter(s -> s.startsWith("c"))
                // map is a intermediate operation
                .map(String::toUpperCase)
                // sorted is a intermediate operation
                .sorted()
                /* terminal operations return void or non-stream result */
                // forEach is a terminal operation
                .forEach(System.out::println); // result: C1 C4
    }

    /**
     * Same as above method but using Steam.of()
     *
     * Use Stream.of() to create a stream from a bunch of object references
     */
    private static void manipulateListUsingStreamDirectly() {
        Stream.of("a1", "a2", "b1", "c4", "c1")
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println); // result: C1 C4
    }

    /**
     * Use IntStream to get average of int stream
     */
    private static void averageOfIntList() {
        IntStream.of(5, 10, 75)
                .map(n -> 2 * n)
                .average()
                .ifPresent(System.out::println); // result: 60.0
    }

    /**
     * Same as above method but parsing Strings to int first using mapToInt
     */
    private static void averageOfIntListParseFromString() {
        Stream.of("5", "10", "75")
                .mapToInt(Integer::parseInt)
                .average()
                .ifPresent(System.out::println); // result: 30.0
    }

    /**
     * Stream processing order
     */
    private static void processingOrder() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
        // result: map: d2 anyMatch: D2 map: a2 anyMatch: A2
    }

    /**
     * Use map to map one object to exactly one other object
     *
     * flatMap can be used to transform one object into multiple others or none at all
     *
     * FlatMap transforms each element of the stream into a stream of other objects.
     * So each object will be transformed into zero, one or multiple other objects backed by streams
     */
    private static void testFlatMap() {
        List<Foo> foos = new ArrayList<>();
        // create foos
        IntStream
                .range(1, 4)
                .forEach(i -> foos.add(new Foo("Foo" + i)));
        // create bars
        foos.forEach(f ->
                IntStream
                        .range(1, 4)
                        .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));
        // transform the stream of three foo objects into a stream of nine bar objects.
        foos.stream()
                .flatMap(foo -> foo.bars.stream())
                .forEach(bar -> System.out.println(bar.name));
    }

    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    /**
     * Same as above method ^^^
     */
    private static void testFlatMapNeater() {
        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
                        .forEach(f.bars::add))
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
    }


}
