package org.example.ForkJoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;

    private final int inicio;

    private final int fim;

    private static final int LIMITE = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }


    ForkJoinSumCalculator(long[] numbers, int inicio, int fim) {
        this.numbers = numbers;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    protected Long compute() {

        int tamanho = fim - inicio;

        if(tamanho <= LIMITE){

            long soma = 0;
            for (int i = inicio; i < fim; i++) {
                soma += numbers[i];
            }
            return soma;
        }

        //calcula o meio do array
        int meio = inicio + tamanho / 2;

        //divide o array em duas partes
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, inicio, meio);
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, meio, fim);
        leftTask.fork();
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        //combina os resultados


        return leftResult + rightResult;
    }
}
