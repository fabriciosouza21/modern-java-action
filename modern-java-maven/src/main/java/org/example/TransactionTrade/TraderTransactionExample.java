package org.example.TransactionTrade;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TraderTransactionExample {

    public static void main(String[] args) {

        long inicio = System.nanoTime();
        // Criando traders
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        // Criando lista de transações
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        transactions.stream().filter(transaction -> transaction.isYear(2011)).sorted(Comparator.comparing(Transaction::getValue)).forEach(
                System.out::println
        );




                transactions
                        .stream()
                        .map(Transaction::getCity)
                        .distinct().forEach(
                                System.out::println
                        );

         transactions.stream().filter(transaction -> transaction.isCity("Cambridge")).map(
                Transaction::getTrader
        ).sorted(Comparator.comparing(Trader::getName)).forEach(
                 System.out::println
        );

        transactions.stream().map(Transaction::getTrader).sorted(
                Comparator.comparing(Trader::getName)
        ).forEach(System.out::println);


        boolean isMilan = transactions.stream().anyMatch(transaction -> transaction.isCity("Milan"));
        System.out.println("Is there any trader based in Milan? " + isMilan);

        transactions.stream().filter(transaction -> transaction.isCity("Cambridge")).map(Transaction::getValue).forEach(
                x -> System.out.println("Transaction value in Cambridge: " + x)
        );

        transactions.stream().max(Comparator.comparing(Transaction::getValue)).ifPresentOrElse(
                maxValue -> System.out.println("Maximum transaction value: " + maxValue),
                () -> System.out.println("No transactions found.")
        );

        transactions.stream().min(Comparator.comparing(Transaction::getValue)).ifPresentOrElse(
                minValue -> System.out.println("Minimum transaction value: " + minValue),
                () -> System.out.println("No transactions found.")
        );

        long fim = System.nanoTime();
        long tempoExecucao = (fim - inicio) / 1_000_000; // converte para millisegundos
        System.out.println("Tempo de execução: " + tempoExecucao + " ms");
    }
}
