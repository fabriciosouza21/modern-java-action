# Refactoring, Testing & Debugging - Capítulo 9

## 🎯 Objetivos
- Refatorar código para usar expressões lambda
- Compreender o impacto das lambdas em design patterns OOP
- Testar expressões lambda
- Fazer debugging de código com lambdas e Streams API

---

## 1. Refatoração para Melhor Legibilidade

### 1.1 De Expressões Lambda para Method References

Expressões lambda tornam o código mais conciso e legível comparado a classes anônimas, especialmente para interfaces funcionais com um único method abstrato. A conversão reduz significativamente a verbosidade do código:

```java
// Antes: classe anônima verbosa
Runnable r1 = new Runnable() {
    public void run(){
        System.out.println("Hello");
    }
};

// Depois: expressão lambda concisa
Runnable r2 = () -> System.out.println("Hello");
```

#### ⚠️ Cuidados na Conversão

**1. Escopo do `this` e `super`**
- Classes anônimas: `this` refere-se à própria classe
- Lambdas: `this` refere-se à classe envolvente

**2. Shadow de variáveis**
- Classes anônimas: ✅ Podem fazer shadow
- Lambdas: ❌ Causam erro de compilação

**3. Ambiguidade em overloading**
- Múltiplos methods com interfaces funcionais compatíveis
- Solução: Cast explícito `(Task)() -> ...`

```java
int a = 10;
Runnable r1 = () -> {
    int a = 2; // ❌ Compile error em lambda
    System.out.println(a);
};

Runnable r2 = new Runnable(){
    public void run(){
        int a = 2; // ✅ Funciona em classe anônima
        System.out.println(a);
    }
};
```

**3. Ambiguidade em overloading:** Quando múltiplos methods têm interfaces funcionais compatíveis, lambdas podem causar ambiguidade:

```java
interface Task {
    public void execute();
}

public static void doSomething(Runnable r){ r.run(); }
public static void doSomething(Task a){ a.execute(); }

// ✅ Classe anônima - sem ambiguidade
doSomething(new Task() {
    public void execute() {
        System.out.println("Danger danger!!");
    }
});

// ❌ Lambda - ambígua (Runnable ou Task?)
doSomething(() -> System.out.println("Danger danger!!"));

// ✅ Lambda com cast explícito - resolve ambiguidade
doSomething((Task)() -> System.out.println("Danger danger!!"));
```

**💡 Dica:** IDEs modernas fazem essa conversão automaticamente e previnem problemas.

### 1.2 De Expressões Lambda para Method References

#### 🎯 Benefícios dos Method References
- **Legibilidade**: Nome do método declara a intenção
- **Expressividade**: Código mais autodocumentado
- **Manutenibilidade**: Mais fácil de entender

#### 📋 Estratégias de Refatoração

**1. Extrair lógica complexa para methods**
```java
// ❌ Lógica inline complexa
groupingBy(dish -> {
    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
    // ...
});

// ✅ Method reference
groupingBy(Dish::getCaloricLevel);
```

**2. Usar methods helper built-in**
```java
// ❌ Lambda verbosa
inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));

// ✅ Method reference
inventory.sort(comparing(Apple::getWeight));
```

**3. Collectors especializados**
```java
// ❌ Reduce manual
menu.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1 + c2);

// ✅ Collector especializado
menu.stream().collect(summingInt(Dish::getCalories));
```

### 1.3 De Processamento Imperativo para Streams

#### 🔄 Por que migrar para Streams?
- ✅ **Clareza**: Expressa melhor a intenção
- ✅ **Otimização**: Short-circuiting, laziness automática
- ✅ **Paralelização**: Fácil aproveitamento multicore

```java
// ❌ Código imperativo - padrões misturados
List<String> dishNames = new ArrayList<>();
for(Dish dish: menu) {
    if(dish.getCalories() > 300){
        dishNames.add(dish.getName());
    }
}
```

**Solução com Streams:** O código lê como a declaração do problema e pode ser facilmente paralelizado:

```java
// ✅ Streams - intenção clara e paralelizável
menu.parallelStream()
    .filter(d -> d.getCalories() > 300)
    .map(Dish::getName)
    .collect(toList());
```

**Desafio:** Converter código imperativo para Streams pode ser difícil devido às declarações de controle de fluxo (break, continue, return) que precisam ser mapeadas para as operações de stream corretas. **Solução:** Existem ferramentas como LambdaFicator que podem auxiliar nessa conversão automática.

## Improving code flexibility


Expressões lambda encorajam o estilo de parametrização de comportamento, permitindo representar múltiplos comportamentos com diferentes lambdas que podem ser passadas para execução. Este estilo facilita lidar com mudanças de requisitos, como criar múltiplas formas de filtrar com Predicate ou comparar com Comparator.

### Adotando Interfaces Funcionais

Para usar expressões lambda, você deve introduzir interfaces funcionais em sua base de código. Dois padrões comuns que podem ser refatorados para aproveitar lambdas são: **conditional deferred execution** e **execute around**.

### Conditional Deferred Execution

É comum ver declarações de controle de fluxo misturadas no código de lógica de negócio, especialmente em verificações de segurança e logging. **Problema comum:** código que expõe estado interno e executa operações caras desnecessariamente:

```java
// ❌ Problemático - estado exposto e operação sempre executada
if (logger.isLoggable(Log.FINER)) {
    logger.finer("Problem: " + generateDiagnostic());
}

// ❌ Melhor mas ainda problemático - generateDiagnostic() sempre executado
logger.log(Level.FINER, "Problem: " + generateDiagnostic());
```

**Solução com lambda:** Use Supplier para executar apenas quando necessário:

```java
// ✅ Solução elegante - execução adiada
logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());

// Implementação interna
public void log(Level level, Supplier<String> msgSupplier) {
    if(logger.isLoggable(level)){
        log(level, msgSupplier.get()); // Executa lambda apenas se necessário
    }
}
```

**Lição:** Quando você consulta o estado de um object muitas vezes no código cliente apenas para chamar methods condicionalmente, introduza um novo method que aceita lambda/method reference e verifica o estado internamente. Isso torna o código mais legível e bem encapsulado.

### Execute Around

Quando você cerca código diferente com as mesmas fases de preparação e limpeza, pode extrair essa lógica para uma lambda, reduzindo duplicação de código:

```java
// Reutiliza lógica de abrir/fechar arquivo com diferentes processamentos
String oneLine = processFile((BufferedReader b) -> b.readLine());
String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());

public static String processFile(BufferedReaderProcessor p) throws IOException {
    try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
        return p.process(br); // Executa o processamento parametrizado
    }
}

public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
```

---

## 2. Melhorando Flexibilidade do Código

### 2.1 Conditional Deferred Execution
**Problema**: Operações caras executadas desnecessariamente
```java
// ❌ Problemático - operação sempre executada
logger.log(Level.FINER, "Problem: " + generateDiagnostic());

// ✅ Solução com lambda - execução adiada
logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());
```

### 2.2 Execute Around Pattern
**Benefício**: Reutiliza lógica de setup/cleanup
```java
// Diferentes processamentos com mesmo padrão de abertura/fechamento
String oneLine = processFile(br -> br.readLine());
String twoLines = processFile(br -> br.readLine() + br.readLine());
```

---

## 3. Refatoração de Design Patterns com Lambdas

### 📋 Patterns Simplificados
- **Strategy**: Interface funcional + lambdas
- **Template Method**: Parâmetros funcionais vs herança
- **Observer**: Lambdas como observers
- **Chain of Responsibility**: Composição de funções
- **Factory**: Map com method references

### 3.1 Strategy Pattern

#### 🔄 Transformação
```java
// ❌ Tradicional - muito boilerplate
public class IsNumeric implements ValidationStrategy {
    public boolean execute(String s) { return s.matches("\\d+"); }
}

// ✅ Com lambda - conciso
Validator validator = new Validator(s -> s.matches("\\d+"));
```

#### 💡 Vantagem
- Elimina classes separadas
- Reduz código boilerplate
- Maior flexibilidade

### 3.2 Template Method Pattern

#### 🔄 Transformação
```java
// ❌ Tradicional - herança obrigatória
abstract class OnlineBanking {
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c); // Método abstrato
    }
    abstract void makeCustomerHappy(Customer c);
}

// ✅ Com lambda - composição flexível
public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy.accept(c); // Lambda injetada
}
```

#### 💡 Vantagem
- Elimina herança
- Composição flexível
- Testes mais fáceis

### 3.3 Observer Pattern

#### 🔄 Transformação
```java
// ❌ Tradicional - classes separadas
class NYTimes implements Observer {
    public void notify(String tweet) {
        if (tweet.contains("money")) {
            System.out.println("Breaking news in NY! " + tweet);
        }
    }
}

// ✅ Com lambda - direto no registro
feed.registerObserver(tweet -> {
    if (tweet.contains("money")) {
        System.out.println("Breaking news in NY! " + tweet);
    }
});
```

#### 💡 Vantagem
- Interface funcional (String -> void)
- Sem boilerplate de classes
- Comportamento inline
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("wine")){
            System.out.println("Today cheese, wine and news! " + tweet);
        }
    }
}
```

Defina a interface Subject e implemente a classe Feed:

```java
interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}

class Feed implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    
    public void registerObserver(Observer o) {
        this.observers.add(o); // Adiciona observer à lista interna
    }
    
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet)); // Chama notify() em cada observer
    }
}
```

**Uso tradicional:**
```java
Feed f = new Feed();
f.registerObserver(new NYTimes());
f.registerObserver(new Guardian());
f.registerObserver(new LeMonde());
f.notifyObservers("The queen said her favourite book is Modern Java in Action!");
// The Guardian picks up this tweet
```

## Usando Expressões Lambda

Note que a interface `Observer` é uma **interface funcional** (um único método abstrato). Isso significa que lambdas podem ser automaticamente convertidas para `Observer` graças ao **function descriptor** e **type inference**.

**Ponto importante:** O método `registerObserver(Observer o)` **não precisa ser alterado**! O compilador automaticamente converte lambdas para a interface `Observer`:

```java
// ✅ A mesma implementação Feed funciona com lambdas!
class Feed implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    
    // Método INALTERADO - aceita Observer (classe ou lambda)
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }
    
    // Método INALTERADO - chama notify() normalmente
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }
}
```

**Uso com lambdas - mesma interface, sem alterações:**
```java
Feed f = new Feed();

// ✅ Lambda automaticamente convertida para Observer
// Compilador infere: (String tweet) -> {...} corresponde ao descriptor de notify()
f.registerObserver((String tweet) -> {
    if(tweet != null && tweet.contains("money")){
        System.out.println("Breaking news in NY! " + tweet);
    }
});

f.registerObserver((String tweet) -> {
    if(tweet != null && tweet.contains("queen")){
        System.out.println("Yet more news from London... " + tweet);
    }
});

f.registerObserver((String tweet) -> {
    if(tweet != null && tweet.contains("wine")){
        System.out.println("Today cheese, wine and news! " + tweet);
    }
});

// Funciona exatamente igual! Chama notify() em cada lambda
f.notifyObservers("The queen said her favourite book is Modern Java in Action!");
```

### 3.4 Factory Pattern

#### 🔄 Transformação
```java
// ❌ Tradicional - switch/case
public static Product createProduct(String name) {
    switch(name) {
        case "loan": return new Loan();
        case "stock": return new Stock();
        // ...
    }
}

// ✅ Com lambda - Map com method references
final static Map<String, Supplier<Product>> map = Map.of(
    "loan", Loan::new,
    "stock", Stock::new,
    "bond", Bond::new
);

public static Product createProduct(String name) {
    return map.get(name).get();
}
```

#### 💡 Vantagem
- Elimina switch/case
- Method references elegantes
- Facilita extensibilidade

#### ⚠️ Limitação
- Não escala bem para constructors com múltiplos parâmetros
- Requer interfaces funcionais customizadas

---

## 4. Testando Lambdas

### 4.1 Estratégias de Teste

#### 🎯 Princípio Fundamental
**Lambdas são detalhes de implementação** - teste o comportamento do método, não a lambda isoladamente.

#### 📋 Abordagens

**1. Testar comportamento do método**
```java
// ❌ Não teste a lambda diretamente
p -> new Point(p.getX() + x, p.getY())

// ✅ Teste o método que usa a lambda
@Test
public void testMoveAllPointsRightBy() {
    List<Point> points = Arrays.asList(new Point(5, 5));
    List<Point> result = Point.moveAllPointsRightBy(points, 10);
    assertEquals(new Point(15, 5), result.get(0));
}
```

**2. Extrair lambdas complexas**
```java
// ❌ Lambda complexa difícil de testar
.map(product -> {
    // Lógica complexa de cálculo
    return newProduct;
})

// ✅ Extrair para method testável
.map(this::calculateDiscountedPrice)

// Teste específico
@Test
public void testCalculateDiscountedPrice() {
    Product result = calculator.calculateDiscountedPrice(laptop);
    assertEquals(1275.0, result.getPrice(), 0.01);
}
```

**3. Testar funções de alta ordem**
```java
// Teste com diferentes lambdas
@Test
public void testFilter() {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
    List<Integer> even = filter(numbers, i -> i % 2 == 0);
    assertEquals(Arrays.asList(2, 4), even);
}
```

---

## 5. Debugging

### 5.1 Problemas com Stack Traces

#### ⚠️ Desafios
- Lambdas não têm nomes → nomes gerados (`lambda$main$0`)
- Stack traces crípticos
- Dificulta identificação do problema

#### 🔧 Soluções
```java
// ❌ Stack trace confuso
points.stream().map(p -> p.getX()).forEach(System.out::println);
// → Debugging.lambda$main$0(Debugging.java:6)

// ✅ Method reference da mesma classe
points.stream().map(Debugging::getX).forEach(System.out::println);
// → Debugging.getX(Debugging.java:10)
```

### 5.2 Logging com peek()

#### 🔍 Debugging de Streams
```java
// ❌ forEach consome o stream
numbers.forEach(System.out::println); // Só mostra resultado final

// ✅ peek permite inspecionar sem consumir
List<Integer> result = numbers.stream()
    .peek(x -> System.out.println("from stream: " + x))
    .map(x -> x + 17)
    .peek(x -> System.out.println("after map: " + x))
    .filter(x -> x % 2 == 0)
    .peek(x -> System.out.println("after filter: " + x))
    .collect(toList());
```

#### 💡 Vantagem do peek()
- Observa elementos passando
- Não consome o stream
- Permite análise step-by-step

---

## 📚 Resumo Executivo

### ✅ Boas Práticas
1. **Refatoração progressiva**: Classes anônimas → Lambdas → Method references
2. **Simplicidade**: Use lambdas para comportamentos simples
3. **Testabilidade**: Extraia lógica complexa para métodos
4. **Debugging**: Prefira method references para melhor stack trace

### 🎯 Benefícios das Lambdas
- **Legibilidade**: Código mais expressivo
- **Flexibilidade**: Parametrização de comportamento
- **Manutenibilidade**: Menos boilerplate
- **Funcionalidade**: Simplificação de design patterns

## 🚀 Ações Práticas para Aplicar

### 🔧 Checklist de Refatoração
- [ ] Converter classes anônimas para lambdas (cuidado com `this` e shadowing)
- [ ] Substituir lambdas por method references quando possível
- [ ] Migrar código imperativo para Streams
- [ ] Simplificar design patterns com lambdas
- [ ] Extrair lambdas complexas para métodos testáveis
- [ ] Usar `peek()` para debugging de streams

### 📊 Prioridades
1. **Alta**: Method references para clareza
2. **Média**: Refatoração de patterns simples
3. **Baixa**: Debugging e otimizações avançadas

### 🎯 Quando Usar Cada Abordagem
- **Lambdas**: Comportamentos simples e inline
- **Method References**: Operações existentes e clareza
- **Classes Tradicionais**: Comportamentos complexos com estado
- **Streams**: Processamento de collections

---

## 🔍 Pontos de Revisão Rápida

### 🟢 Vantagens das Lambdas
- Código mais conciso e legível
- Eliminação de boilerplate
- Flexibilidade na parametrização
- Simplificação de design patterns

### 🟡 Cuidados Importantes
- Diferenças semânticas (`this`, shadowing)
- Ambiguidade em overloading
- Stack traces menos claros
- Complexidade em debugging

### 🔴 Limitações
- Não adequadas para lógica complexa
- Problemas com múltiplos parâmetros em Factory
- Podem obscurecer intenção se mal usadas

---

## 🎓 Conceitos-Chave para Lembrar

1. **Lambda = Função anônima**: Código como dados
2. **Method Reference = Referência a método**: Ainda mais conciso
3. **Interface Funcional**: Um método abstrato, compatível com lambdas
4. **Design Patterns**: Muitos simplificados com lambdas
5. **Teste o comportamento**: Não a implementação lambda
6. **peek() para debugging**: Observa sem consumir o stream

### 💡 Dica Final
**Progressão natural**: Classe anônima → Lambda → Method reference → Stream API

Usar Map com method references aos constructors, eliminando switch/case:

```java
// Map com constructors como Suppliers
final static Map<String, Supplier<Product>> map = new HashMap<>();
static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
}

public static Product createProduct(String name){
    Supplier<Product> p = map.get(name);
    if(p != null) return p.get();
    throw new IllegalArgumentException("No such product " + name);
}
```

**Vantagem:** Técnica elegante que usa method references (`Loan::new`) para alcançar a mesma intenção do factory pattern, mas de forma mais funcional.

## Limitações com Múltiplos Argumentos

**Problema:** Não escala bem para constructors com múltiplos parâmetros. Requer interfaces funcionais customizadas:

```java
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}

Map<String, TriFunction<Integer, Integer, String, Product>> map = new HashMap<>();
// Assinatura torna-se complexa para cada número de argumentos
```

**Conclusão:** Lambdas simplificam factory pattern para casos simples (constructors sem argumentos), mas para constructors complexos com múltiplos parâmetros, a abordagem tradicional pode ser mais prática.


# Testing lambdas 

## Testing the behavior of a visible lambda


``` java
public class Point {
public final static Comparator<Point> compareByXAndThenY =
    comparing(Point::getX).thenComparing(Point::getY);
}
```



``` java
@Test
public void testComparingTwoPoints() throws Exception {
    Point p1 = new Point(10, 15);
    Point p2 = new Point(10, 20);
    int result = Point.compareByXAndThenY.compare(p1 , p2);
    assertTrue(result < 0);
}
```


## Focusing on the behavior of the method using a lambda


Lambdas devem ser tratadas como **detalhes de implementação**, não como unidades testáveis independentes. O propósito das lambdas é encapsular comportamento único para uso interno por outros methods.

**Abordagem incorreta:** Tentar testar a lambda isoladamente
```java
// ❌ Não teste a lambda diretamente
p -> new Point(p.getX() + x, p.getY()) // É apenas detalhe de implementação
```

**Abordagem correta:** Testar o method que usa a lambda, focando no comportamento final:
```java
public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
    return points.stream()
        .map(p -> new Point(p.getX() + x, p.getY())) // Lambda como detalhe interno
        .collect(toList());
}

@Test
public void testMoveAllPointsRightBy() throws Exception {
    List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));
    List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));
    
    List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
    assertEquals(expectedPoints, newPoints); // Testa o comportamento final
}
```

**Pontos importantes:**
- Lambdas não devem ser disponibilizadas publicamente
- Teste o comportamento do method, não a implementação lambda
- Classes usadas em testes devem implementar `equals()` adequadamente
- Foque na **correção do resultado** ao invés da **elegância da implementação**


## Pulling complex lambdas into separate methods


Quando você encontra lambdas realmente complicadas com muita lógica (como algoritmos técnicos de precificação com casos especiais), não é possível testá-las diretamente. **Solução:** converter a lambda para method reference declarando um novo method regular, que pode ser testado como qualquer method normal.

**Problema:** Lambda complexa difícil de testar
```java
public List<Product> calculatePrices(List<Product> products) {
    return products.stream()
        .map(product -> {
            // ❌ Lambda complexa com lógica de negócio extensa
            double basePrice = product.getPrice();
            double discount = 0.0;
            if (product.getCategory().equals("ELECTRONICS")) {
                discount = basePrice > 1000 ? 0.15 : 0.05;
            } else if (product.getCategory().equals("CLOTHING")) {
                discount = product.getSeason().equals("WINTER") ? 0.20 : 0.10;
            }
            return new Product(product.getName(), basePrice * (1 - discount), product.getCategory());
        })
        .collect(toList());
}
```

**Solução:** Extrair para method separado e usar method reference
```java
public List<Product> calculatePrices(List<Product> products) {
    return products.stream()
        .map(this::calculateDiscountedPrice) // ✅ Method reference testável
        .collect(toList());
}

// ✅ Method separado que pode ser testado independentemente
private Product calculateDiscountedPrice(Product product) {
    double basePrice = product.getPrice();
    double discount = calculateDiscount(product);
    double finalPrice = basePrice * (1 - discount);
    return new Product(product.getName(), finalPrice, product.getCategory());
}
```

**Testes específicos possíveis:**
```java
@Test
public void testCalculateDiscountedPrice_ExpensiveElectronics() {
    Product laptop = new Product("Laptop", 1500.0, "ELECTRONICS");
    Product result = calculator.calculateDiscountedPrice(laptop);
    assertEquals(1275.0, result.getPrice(), 0.01); // 15% discount
}
```

**Vantagens:** Testabilidade independente, reutilização de código, melhor legibilidade, debugging mais fácil e manutenção localizada.

## Testing high-order functions

Funções de alta ordem (methods que recebem functions como argumento ou retornam functions) requerem estratégias específicas de teste para verificar seu comportamento adequadamente.

## Testando Methods que Recebem Lambdas

**Estratégia:** Teste o method com **diferentes lambdas** para verificar se processa corretamente variados comportamentos:

```java
@Test
public void testFilter() throws Exception {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
    List<Integer> even = filter(numbers, i -> i % 2 == 0);
    List<Integer> smallerThanThree = filter(numbers, i -> i < 3);
    
    assertEquals(Arrays.asList(2, 4), even);
    assertEquals(Arrays.asList(1, 2), smallerThanThree);
}
```

## Testando Methods que Retornam Functions

**Estratégia:** Trate a function retornada como instância de interface funcional e teste seu comportamento:

```java
// Method que retorna function
public static Predicate<String> createLengthFilter(int minLength) {
    return s -> s.length() >= minLength;
}

@Test
public void testCreateLengthFilter() {
    Predicate<String> filter = createLengthFilter(5);
    
    assertTrue(filter.test("hello"));     // 5 caracteres
    assertFalse(filter.test("hi"));       // 2 caracteres
}
```

## Abordagens Principais

1. **Methods que recebem functions:** Teste com diferentes implementations
2. **Methods que retornam functions:** Teste a function retornada como objeto funcional
3. **Edge cases:** Teste valores nulos, functions compostas, casos extremos

**Resultado:** Permite verificar tanto o comportamento do method principal quanto das functions que ele manipula, garantindo correção em cenários de programação funcional.


# Debugging

## Examining the stack trace

Stack traces são essenciais para identificar onde e como falhas ocorrem no programa, mostrando a sequência de chamadas de methods até o ponto de falha. **Porém, lambdas complicam o debugging** porque não têm nomes, forçando o compilador a gerar nomes crípticos.

### Problemas com Stack Traces de Lambda

**Código problemático:**
```java
List<Point> points = Arrays.asList(new Point(12, 2), null);
points.stream().map(p -> p.getX()).forEach(System.out::println);
```

**Stack trace confuso:**
```
Exception in thread "main" java.lang.NullPointerException
    at Debugging.lambda$main$0(Debugging.java:6)           // Nome gerado automaticamente
    at Debugging$$Lambda$5/284720968.apply(Unknown Source) // Linha críptica
```

**Problema:** O compilador gera nomes como `lambda$main$0` para lambdas, tornando difícil identificar qual lambda específica causou o erro, especialmente em classes com múltiplas lambdas.

### Method References Também Podem Ser Problemáticas

```java
points.stream().map(Point::getX).forEach(System.out::println);
// Ainda produz: Debugging$$Lambda$5/284720968.apply(Unknown Source)
```

**Exceção:** Method references para methods **da mesma classe** aparecem corretamente no stack trace:

```java
numbers.stream().map(Debugging::divideByZero).forEach(System.out::println);
// Produz: at Debugging.divideByZero(Debugging.java:10) ✅
```

### Estratégias para Melhor Debugging

- **Extraia lambdas complexas** para methods nomeados na mesma classe
- **Use method references** quando possível (especialmente da mesma classe)
- **Prepare-se para interpretar nomes gerados** como `lambda$main$0`
- **Use breakpoints** e debugging step-by-step para rastrear problemas em streams

**Limitação atual:** Esta é uma área onde o compilador Java pode ser melhorado em versões futuras.


##  Logging information


Para debugar pipelines de stream, usar `forEach` consome todo o stream, impedindo análise das etapas intermediárias. **Solução:** a operação `peek()` permite inspecionar valores em cada etapa sem consumir o stream.

**Problema com forEach:**
```java
numbers.stream()
    .map(x -> x + 17)
    .filter(x -> x % 2 == 0)
    .limit(3)
    .forEach(System.out::println); // ❌ Consome o stream, só mostra resultado final
// Output: 20, 22
```



**Solução com peek:**
```java
List<Integer> result = numbers.stream()
    .peek(x -> System.out.println("from stream: " + x))      // Elemento original
    .map(x -> x + 17)
    .peek(x -> System.out.println("after map: " + x))       // Após map
    .filter(x -> x % 2 == 0)
    .peek(x -> System.out.println("after filter: " + x))    // Após filter
    .limit(3)
    .peek(x -> System.out.println("after limit: " + x))     // Após limit
    .collect(toList()); // ✅ Stream ainda disponível para operação terminal
```

**Saída detalhada:** Mostra cada elemento passando por todas as etapas (`from stream: 2`, `after map: 19`, etc.), permitindo entender exatamente como o pipeline processa os dados e onde problemas podem estar ocorrendo.

**Vantagem:** peek **não consome** o stream - apenas observa elementos passando, mantendo o pipeline funcionando normalmente para operações terminais.


## Summary

- **Expressões lambda** podem tornar seu código mais legível e flexível.
- Considere converter classes anônimas para expressões lambda, mas tenha cuidado com diferenças semânticas sutis como o significado da palavra-chave `this` e shadowing de variáveis.
- **Method references** podem tornar seu código mais legível comparado com expressões lambda.
- Considere converter processamento iterativo de collections para usar a API Streams.
- **Expressões lambda** podem remover código boilerplate associado com vários design patterns orientados a objetos, como strategy, template method, observer, chain of responsibility, e factory.
- **Expressões lambda podem ser testadas** unitariamente, mas em geral, você deve focar em testar o comportamento dos methods nos quais as expressões lambda aparecem.
- Considere **extrair expressões lambda complexas** para methods regulares.
- **Expressões lambda podem tornar stack traces menos legíveis**.
- O **method peek** de um stream é útil para registrar valores intermediários conforme eles fluem através de certos pontos de um pipeline de stream.