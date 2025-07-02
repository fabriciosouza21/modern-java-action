# Guia Completo Java 8 - Funcionalidades e Conceitos

## Introdução

O Java 8 fornece uma nova API (chamada Streams) que suporta muitas operações paralelas para processar dados e se assemelha à forma como você pode pensar em linguagens de consulta de banco de dados.

## 1. Principais Funcionalidades

### 1.1 Stream Processing

Uma stream é uma sequência de itens de dados que são conceitualmente produzidos um de cada vez.

### 1.2 Behavior Parameterization

O conceito de programação adicionado ao Java 8 é a capacidade de passar um pedaço de código para uma API através da parametrização de comportamento com methods.

### 1.3 Paralelismo e Dados Mutáveis Compartilhados

Você deve fornecer um comportamento que seja seguro para executar simultaneamente em diferentes partes da entrada. Tipicamente, isso significa escrever código que não acessa dados mutáveis compartilhados para fazer seu trabalho.

As streams do Java 8 exploram o paralelismo mais facilmente do que a API Threads existente do Java. Embora seja possível usar `synchronized` para quebrar a regra de não-dados-mutáveis-compartilhados, isso é lutar contra o sistema, pois está abusando de uma abstração otimizada em torno dessa regra.

### 1.4 Necessidade de Evolução do Java

Para perdurar, o Java tem que evoluir adicionando novas funcionalidades. Esta evolução será inútil a menos que as novas funcionalidades sejam usadas, então ao usar o Java 8 você está protegendo seu modo de vida como programador Java.

## 2. Functions em Java

O termo functions em Java é frequentemente usado para refletir methods estáticos sem side effects.

### 2.1 Contexto Histórico

No Java tradicional, antes da versão 8, existia uma limitação importante: enquanto valores como números, strings e objetos eram considerados cidadãos de primeira classe, methods e classes eram relegados ao status de segunda classe. Isso significava que os programadores não podiam passar um método como parâmetro para outro método de forma direta e natural, o que limitava a flexibilidade da linguagem para certos tipos de programação.

### 2.2 A Inovação do Java 8

A grande inovação do Java 8 foi justamente promover methods para o status de primeira classe, permitindo que eles fossem tratados como valores manipuláveis. Essa mudança tornou possível que os desenvolvedores passassem methods como parâmetros, os armazenassem em variáveis e os utilizassem de forma mais flexível, abrindo caminho para recursos como lambdas, method references e a programação funcional.

### 2.3 Methods e Lambdas como Cidadãos de Primeira Classe

Os designers do Java 8 decidiram permitir que methods fossem valores—para tornar mais fácil a programação. Além disso, a funcionalidade do Java 8 de methods como valores forma a base de várias outras funcionalidades do Java 8 (como Streams).

#### Method References

```java
File[] hiddenFiles = new File(".").listFiles(File::isHidden);
```

#### Lambdas: Anonymous Functions

A nova sintaxe lambda é mais concisa para casos onde não se tem um method e classe convenientes disponíveis.

> "Escrevendo programas que passam functions como valores de primeira classe."

## 3. Exemplo Prático: Passando Código

Podemos utilizar a funcionalidade de passar predicados para evitar repetição de código:

```java
// Classe Apple com seus atributos e métodos
public class Apple {
    private String color;
    private int weight;

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }
}

// Métodos que definem diferentes critérios de filtro
public static boolean isGreenApple(Apple apple) {
    return "GREEN".equals(apple.getColor());
}

public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150; // maçãs acima de 150g são consideradas pesadas
}

public static boolean isRedApple(Apple apple) {
    return "RED".equals(apple.getColor());
}

// Interface funcional que representa um teste/condição
public interface Predicate<T> {
    boolean test(T t); // método que recebe um objeto e retorna true/false
}

// Método genérico que filtra maçãs baseado em qualquer critério
static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate) {
    List<Apple> filteredApples = new ArrayList<>();

    // Percorre cada maçã do inventário
    for (Apple apple : inventory) {
        // Aplica o critério de filtro (predicate) na maçã atual
        if (predicate.test(apple)) {
            filteredApples.add(apple); // Adiciona à lista se passou no teste
        }
    }

    return filteredApples;
}

// Exemplo de uso com method references
public static void main(String[] args) {
    // Criando um inventário de maçãs
    List<Apple> inventory = Arrays.asList(
        new Apple("GREEN", 80),
        new Apple("RED", 160),
        new Apple("GREEN", 200),
        new Apple("RED", 90)
    );

    // Filtrando maçãs verdes usando method reference
    List<Apple> greenApples = filterApples(inventory, Apple::isGreenApple);
    System.out.println("Maçãs verdes encontradas: " + greenApples.size());

    // Filtrando maçãs pesadas usando method reference
    List<Apple> heavyApples = filterApples(inventory, Apple::isHeavyApple);
    System.out.println("Maçãs pesadas encontradas: " + heavyApples.size());

    // Filtrando maçãs vermelhas usando method reference
    List<Apple> redApples = filterApples(inventory, Apple::isRedApple);
    System.out.println("Maçãs vermelhas encontradas: " + redApples.size());

    // Usando lambda expression para um critério customizado
    List<Apple> lightApples = filterApples(inventory,
        apple -> apple.getWeight() < 100); // maçãs leves (menos de 100g)
    System.out.println("Maçãs leves encontradas: " + lightApples.size());
}
```

### 3.1 De Methods para Lambdas

Passar methods como valores é claramente útil, mas é irritante ter que escrever uma definição para methods curtos como `isHeavyApple` e `isGreenApple` quando eles são usados talvez apenas uma ou duas vezes.

```java
filterApples(inventory, (Apple a) -> a.getWeight() < 80 || RED.equals(a.getColor()));
```

## 4. Streams

Quase toda aplicação Java cria e processa collections. Mas trabalhar com collections nem sempre é ideal. Por exemplo, digamos que você precise filtrar transações caras de uma lista e então agrupá-las por moeda. Você precisaria escrever muito código boilerplate para implementar essa consulta de processamento de dados.

### 4.1 Abordagem Tradicional (Problemática)

```java
Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();
for (Transaction transaction : transactions) {
    if(transaction.getPrice() > 1000){
        Currency currency = transaction.getCurrency();
        List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
        if (transactionsForCurrency == null) {
            transactionsForCurrency = new ArrayList<>();
            transactionsByCurrencies.put(currency, transactionsForCurrency);
        }
        transactionsForCurrency.add(transaction);
    }
}
```

### 4.2 Abordagem com Streams (Elegante)

```java
import static java.util.stream.Collectors.groupingBy;

Map<Currency, List<Transaction>> transactionsByCurrencies =
    transactions.stream()
               .filter((Transaction t) -> t.getPrice() > 1000)
               .collect(groupingBy(Transaction::getCurrency));
```

### 4.3 Diferenças entre APIs

Por enquanto, vale a pena notar que a API Streams fornece uma maneira diferente de processar dados em comparação com a API Collections.

## 5. Paralelismo e Multithreading

### 5.1 Dificuldades do Multithreading

O problema é que explorar paralelismo escrevendo código multithread (usando a API Threads de versões anteriores do Java) é difícil. É preciso pensar de forma diferente: threads podem acessar e atualizar variáveis compartilhadas ao mesmo tempo. Como resultado, dados podem mudar inesperadamente se não coordenados adequadamente. Este modelo é mais difícil de pensar do que um modelo sequencial passo a passo.

### 5.2 Motivações para Streams

1. **Primeira motivação**: Podemos utilizar a API Streams para processar dados de forma agregada como SQL
2. **Segunda motivação**: O processamento de forma paralela já está presente de forma intuitiva na API Streams

Novamente, vamos apenas dizer "paralelismo quase de graça".

### 5.3 Exemplo de Paralelismo com Streams

```java
import static java.util.stream.Collectors.toList;

List<Apple> heavyApples =
    inventory.parallelStream()
             .filter((Apple a) -> a.getWeight() > 150)
             .collect(toList());
```

## 6. Default Methods e Módulos Java

### 6.1 Sistema de Módulos (Java 9)

O Java 9 fornece um sistema de módulos que oferece sintaxe para definir módulos contendo coleções de packages—e manter um controle muito melhor sobre visibilidade e namespaces.

### 6.2 Default Methods (Java 8)

A solução do Java 8 é quebrar o último elo: uma interface agora pode conter assinaturas de métodos para as quais uma classe implementadora não fornece uma implementação. Então quem as implementa? Os corpos de métodos ausentes são dados como parte da interface (daí implementações default) ao invés de na classe implementadora.

## 7. Outras Ideias da Programação Funcional

### 7.1 O Problema do Null

Linguagens funcionais como SML, OCaml e Haskell não têm null. Ao invés disso, utilizam uma abstração para representar a ausência de um valor.

> "Eu chamo isso de meu erro de um bilhão de dólares. Foi a invenção da referência null em 1965... Eu não consegui resistir à tentação de colocar uma referência null, simplesmente porque era tão fácil de implementar."
>
> *(Tony Hoare, QCon London 2009)*

### 7.2 Optional<T>

O Java 8 introduziu a classe `Optional<T>` que, se usada consistentemente, pode ajudar a evitar null-pointer exceptions. É um objeto container que pode ou não conter um valor.

## 8. Resumo - Java 8

### 8.1 Evolução da Linguagem
- Mantenha em mente a ideia do **ecossistema da linguagem** e a consequente pressão de **evoluir-ou-definhar** sobre as linguagens
- Embora o **Java** possa estar supremamente saudável no momento, podemos lembrar de outras linguagens saudáveis como **COBOL** que falharam em evoluir

### 8.2 Principais Adições do Java 8
- As adições centrais ao **Java 8** fornecem conceitos novos e empolgantes e funcionalidade para facilitar a escrita de programas que são tanto **efetivos** quanto **concisos**
- Processadores **multicore** não são totalmente atendidos pela prática de programação **pré-Java-8**

### 8.3 Functions como Valores de Primeira Classe
- **Functions** são valores de primeira classe
- Lembre-se de como **methods** podem ser passados como valores funcionais
- **Functions** anônimas (**lambdas**) podem ser escritas de forma concisa

### 8.4 Streams API
- O conceito de **streams** do **Java 8** generaliza muitos aspectos das **collections**
- **Streams** frequentemente permitem código mais legível
- Permite que elementos de um **stream** sejam processados em **paralelo**

### 8.5 Programação Baseada em Componentes
- Programação baseada em componentes de larga escala e evolução das **interfaces** de um sistema não eram historicamente bem atendidas pelo **Java**
- Agora é possível especificar **módulos** para estruturar sistemas no **Java 9**
- Usar **default methods** para permitir que uma **interface** seja aprimorada sem alterar todas as **classes** que a implementam

### 8.6 Programação Funcional
- Outras ideias interessantes da programação funcional foram incorporadas ao **Java 8**
