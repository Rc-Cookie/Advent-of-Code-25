package de.rccookie.aoc.aoc25.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

import de.rccookie.util.Arguments;
import de.rccookie.util.IterableIterator;
import de.rccookie.util.ListStream;
import de.rccookie.util.LongWrapper;
import de.rccookie.util.Utils;

public final class Streams {

    private Streams() { }



    public static <R> Gatherer<R,?,R> dropLast() {
        return dropLast(1);
    }

    public static <R> Gatherer<R,?,R> dropLast(int count) {
        Arguments.checkRange(count, 0, null);
        class DropLast {
            final Object[] buf = new Object[count];
            int pos = 0, len = 0;

            @SuppressWarnings("unchecked")
            boolean integrate(R element, Gatherer.Downstream<? super R> downstream) {
                if(len < count) {
                    buf[pos] = element;
                    pos = (pos + 1) % count;
                    len++;
                    return true;
                }
                R out = (R) buf[pos];
                buf[pos] = element;
                pos = (pos + 1) % count;
                return downstream.push(out);
            }
        }
        return Gatherer.ofSequential(DropLast::new, Gatherer.Integrator.<DropLast,R,R>ofGreedy(DropLast::integrate));
    }

    public static <R> Gatherer<R,?, ListStream<R>> split(R delimiter) {
        return split(x -> Objects.equals(delimiter, x));
    }

    public static <R> Gatherer<R,?,ListStream<R>> split(Predicate<? super R> delimiter) {
        Arguments.checkNull(delimiter, "delimiter");
        class Split {
            List<R> current = null;

            boolean integrate(R element, Gatherer.Downstream<? super ListStream<R>> downstream) {
                boolean split = delimiter.test(element);
                if(split) {
                    boolean res = downstream.push(current != null ? ListStream.of(current) : ListStream.empty());
                    current = null;
                    return res;
                }
                else {
                    if(current == null)
                        current = new ArrayList<>();
                    current.add(element);
                    return true;
                }
            }

            void finish(Gatherer.Downstream<? super ListStream<R>> downstream) {
                if(current != null && !downstream.isRejecting())
                    downstream.push(ListStream.of(current));
            }
        }
        return Gatherer.<R, Split, ListStream<R>>ofSequential(
                Split::new,
                Gatherer.Integrator.<Split,R,ListStream<R>>ofGreedy(Split::integrate),
                Split::finish
        );
    }



    public static <A,B,R> Gatherer<A,?,R> zip(B[] otherSource, BiFunction<? super A, ? super B, ? extends R> zipper) {
        return zip(Utils.iterator(otherSource), null, zipper);
    }

    public static <A,B,R> Gatherer<A,?,R> zip(Stream<? extends B> otherSource, BiFunction<? super A, ? super B, ? extends R> zipper) {
        return zip(otherSource.iterator(), otherSource::close, zipper);
    }

    public static <A,B,R> Gatherer<A,?,R> zip(IterableIterator<? extends B> otherSource, BiFunction<? super A, ? super B, ? extends R> zipper) {
        return zip(otherSource, null, zipper);
    }

    public static <A,B,R> Gatherer<A,?,R> zip(Iterable<? extends B> otherSource, BiFunction<? super A, ? super B, ? extends R> zipper) {
        return zip(otherSource.iterator(), null, zipper);
    }

    public static <A,B,R> Gatherer<A,?,R> zip(Iterator<? extends B> otherSource, BiFunction<? super A, ? super B, ? extends R> zipper) {
        return zip(otherSource, null, zipper);
    }

    private static <A,B,R> Gatherer<A,?,R> zip(Iterator<? extends B> otherSource, Runnable closeOther, BiFunction<? super A, ? super B, ? extends R> zipper) {
        Arguments.checkNull(otherSource, "otherSource");
        Arguments.checkNull(zipper, "zipper");
        class Zip {
            int hasNext = -1;

            boolean integrate(A element, Gatherer.Downstream<? super R> downstream) {
                if(hasNext < 0)
                    hasNext = otherSource.hasNext() ? 1 : 0;
                if(hasNext == 0)
                    return false;
                hasNext = -1;
                return downstream.push(zipper.apply(element, otherSource.next()));
            }

            void finish(Gatherer.Downstream<? super R> downstream) {
                if(closeOther != null)
                    closeOther.run();
            }
        }
        return Gatherer.<A, Zip, R>ofSequential(
                Zip::new,
                Gatherer.Integrator.<Zip, A, R>ofGreedy(Zip::integrate),
                Zip::finish
        );
    }

    private static final Collector<Long, LongWrapper,Long> SUM = Collector.of(
            LongWrapper::new,
            (a,b) -> a.value += b,
            (a,b) -> { a.value += b.value; return a; },
            LongWrapper::get
    );

    private static final Collector<Long,LongWrapper,Long> PRODUCT = Collector.of(
            () -> new LongWrapper(1),
            (a,b) -> a.value *= b,
            (a,b) -> { a.value *= b.value; return a; },
            LongWrapper::get
    );

    public static Collector<Long,LongWrapper,Long> sum() {
        return SUM;
    }

    public static Collector<Long,LongWrapper,Long> product() {
        return PRODUCT;
    }
}
