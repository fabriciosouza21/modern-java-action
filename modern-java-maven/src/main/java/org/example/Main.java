package org.example;

import org.example.ForkJoin.JoinFork;
import org.example.dsl.DSL;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        long sum = JoinFork.forkJoinSum(3L);
        System.out.println("Soma: " + sum);

        DSL.exec();
    }
}