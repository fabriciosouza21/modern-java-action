# Guia Completo - Behavior Parameterization em Java

## 1. Introdução

> Um problema bem conhecido na engenharia de software é que não importa o que você faça, os requisitos do usuário vão mudar.

**Behavior parameterization** é um padrão de desenvolvimento de software que permite lidar com mudanças frequentes de requisitos. Em resumo, significa pegar um bloco de código e disponibilizá-lo sem executá-lo. Este bloco de código pode ser chamado mais tarde por outras partes de seus programas, o que significa que você pode adiar a execução daquele bloco de código.

### Exemplos de uso do padrão:
- Pode fazer "algo" para cada elemento de uma lista
- Pode fazer "algo mais" quando você terminar de processar a lista
- Pode fazer "ainda algo mais" se você encontrar um erro

## 2. Lidando com Requisitos em Mudança

Escrever código que pode lidar com requisitos em mudança é difícil. Vamos passar por um exemplo que melhoraremos gradualmente, mostrando algumas melhores práticas para tornar seu código mais flexível.

## 3. Implementando Behavior Parameterization

Para lidar com múltiplos atributos como cor ou peso, podemos criar uma interface que define um método de test, utilizando o conceito de predicados.

### 3.1 Definindo a Interface Predicado

```java
public interface ApplePredicate {
    boolean test(Apple apple);
}
```

### 3.2 Implementações Concretas

```java
public class AppleHeavyWeightPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return apple.getWeight() > 150; // Selects only heavy apples
    }
}

public class AppleGreenColorPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor()); // Selects only green apples
    }
}
```

### 3.3 Método de Filtro Genérico

```java
public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
    List<Apple> result = new ArrayList<>();
    for(Apple apple: inventory) {
        if(p.test(apple)) {
            result.add(apple);
        }
    }
    return result;
}
```

## 4. Enfrentando a Verbosidade

### 4.1 Anonymous Classes

**Anonymous classes** são como as classes locais (uma classe definida em um bloco) que você já está familiarizado no Java. Mas **anonymous classes** não têm um nome. Elas permitem que você declare e instancie uma classe ao mesmo tempo. Em resumo, elas permitem que você crie implementações ad hoc.

#### Exemplo usando Anonymous Class

```java
List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
    public boolean test(Apple apple){
        return RED.equals(apple.getColor());
    }
});
```

### 4.2 O Problema da Verbosidade

Verbosidade em geral é ruim; ela desencoraja o uso de uma funcionalidade da linguagem porque leva muito tempo para escrever e manter código verboso, e não é agradável de ler! Código bom deveria ser fácil de compreender rapidamente.

### 4.3 Usando Lambda Expressions

```java
List<Apple> result =
    filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```

### 4.4 Abstraindo para Tipos Genéricos

```java
public interface Predicate<T> {
    boolean test(T t);
}

public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for(T e: list) {
        if(p.test(e)) {
            result.add(e);
        }
    }
    return result;
}
```

## 5. Exemplos do Mundo Real

### 5.1 Retornando um Resultado usando Callable

**Exemplo sem lambda:**

```java
ExecutorService executorService = Executors.newCachedThreadPool();
Future<String> threadName = executorService.submit(new Callable<String>() {
    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
});
```

**Exemplo com lambda:**

```java
ExecutorService executorService = Executors.newCachedThreadPool();
Future<String> threadName = executorService.submit(
    () -> Thread.currentThread().getName()
);
```

### 5.2 Outros Exemplos Comuns

- **Ordenação**: `Collections.sort(list, comparator)`
- **Manipulação de eventos GUI**: `button.setOnAction(event -> { ... })`
- **Processamento de streams**: `stream.filter(predicate).map(function)`

## 6. Resumo

• **Behavior parameterization** é a capacidade de um method receber múltiplos comportamentos diferentes como parâmetros e usá-los internamente para realizar comportamentos diferentes.

• **Behavior parameterization** permite que você torne seu código mais adaptativo a requisitos em mudança e economiza esforços de engenharia no futuro.

• Passar código é uma forma de dar novos comportamentos como argumentos para um method. Mas é verboso antes do Java 8. **Anonymous classes** ajudaram um pouco antes do Java 8 para se livrar da verbosidade associada com declarar múltiplas classes concretas para uma interface que são necessárias apenas uma vez.

• A **API Java** contém muitos methods que podem ser parametrizados com comportamentos diferentes, que incluem ordenação, threads, e manipulação de GUI.

## 7. Benefícios do Padrão

### 7.1 Flexibilidade
- Código mais adaptável a mudanças de requisitos
- Redução da duplicação de código
- Maior reutilização de components

### 7.2 Manutenibilidade
- Código mais limpo e legível
- Separação clara de responsabilidades
- Facilita testes unitários

### 7.3 Expressividade
- Código mais próximo da linguagem de domínio
- Intenção mais clara do programador
- Menor gap entre problema e solução

---

*Este guia demonstra como o behavior parameterization é fundamental para escrever código Java flexível e manutenível, especialmente com as melhorias introduzidas no Java 8 através de lambda expressions.*
