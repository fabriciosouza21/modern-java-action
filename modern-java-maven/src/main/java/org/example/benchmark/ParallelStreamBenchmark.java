package org.example.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime)          // Mede o tempo médio necessário para o método de benchmark
@OutputTimeUnit(TimeUnit.MILLISECONDS)    // Imprime os resultados do benchmark usando milissegundos como unidade de tempo
@Fork(value = 2, jvmArgs={"-Xms4G", "-Xmx4G"})   // Executa o benchmark 2 vezes para aumentar a confiabilidade dos resultados, com 4Gb de heap space
@State(Scope.Benchmark)
public class ParallelStreamBenchmark {
    private static final long N = 10_000_000L;

    @Benchmark                             // O método a ser testado no benchmark
    public long sequentialSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N)
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long iterativeSum() {
        long result = 0;
        for (long i = 1L; i <= N; i++) {
            result += i;
        }
        return result;
    }

    @Benchmark
    public long rangedSum() {
        return LongStream.rangeClosed(1, N)
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelRangedSum() {
        return LongStream.rangeClosed(1, N)
                .parallel()
                .reduce(0L, Long::sum);
    }


    @TearDown(Level.Invocation)           // Tenta executar o garbage collector após cada iteração do benchmark
    public void tearDown() {
        System.gc();
    }
}