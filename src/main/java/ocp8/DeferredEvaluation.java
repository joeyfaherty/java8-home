package ocp8;

import java.util.Iterator;

public class DeferredEvaluation {

    private Iterator<?> nested;

    public static void main(String[] args) {


        generate().infiniteList(1).map(times(2)).take(5).evaluate();


    }

    private void evaluate() {
        while (nested.hasNext()) {
            System.out.print(" ");
            System.out.print(nested.next());
        }
        System.out.println();
    }

    private DeferredEvaluation take(int i) {
        nested = new Take(i, nested);
        return this;
    }

    private <In, Out> DeferredEvaluation map(Lambda<In, Out> function) {
        nested = new MapFunction(nested, function);
        return this;
    }

    private static Lambda<Integer, Integer> times(final Integer m) {
        return new Lambda<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return m * integer;
            }
        };
    }


    private static DeferredEvaluation generate() {
        return new DeferredEvaluation();
    }

    private DeferredEvaluation infiniteList(int start) {
        nested = new ToInfinity(start);
        return this;
    }


    static class Take<I> implements Iterator<I> {

        int remaining;
        final Iterator<I> from;

        Take(int limit, Iterator<I> from) {
            this.remaining = limit;
            this.from = from;
        }

        @Override
        public boolean hasNext() {
            if (remaining > 0) {
                return from.hasNext();
            }
            return false;
        }

        @Override
        public I next() {
            remaining--;
            return from.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    static class MapFunction<In, Out> implements Iterator<Out> {

        final Iterator<In> in;
        final Lambda<In, Out> function;

        MapFunction(Iterator<In> in, Lambda<In, Out> function) {
            this.in = in;
            this.function = function;
        }


        @Override
        public boolean hasNext() {
            return in.hasNext();
        }

        @Override
        public Out next() {
            In inout = in.next();
            return function.call(inout);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    static class ToInfinity implements Iterator<Integer> {


        Integer count;

        ToInfinity(Integer count) {
            this.count = count;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return count++;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private static interface Lambda<Input, Output> {

        Output call(Input input);

    }


}
