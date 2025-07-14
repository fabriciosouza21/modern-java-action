# Domain-Specific Languages (DSLs) com Lambdas

## 🎯 Conceito Fundamental

> **"Programas devem ser escritos para pessoas lerem e apenas incidentalmente para máquinas executarem."** - Harold Abelson

### O que é uma DSL?

**Domain-Specific Language (DSL)** é uma linguagem customizada projetada para resolver problemas específicos de um domínio de negócio. Pense em uma DSL como uma **API especializada** que faz interface com um domínio específico.

### ✅ Características de uma DSL

- **Específica:** Não é uma linguagem de propósito geral
- **Restrita:** Limita operações e vocabulário ao domínio
- **Focada:** Permite concentrar-se no problema de negócio

### ❌ O que NÃO é uma DSL

- **Não é inglês simples**
- **Não permite** especialistas implementarem lógica de baixo nível
- **Não substitui** linguagens de programação completas

### 🎯 Principais Motivações

| **Motivação** | **Descrição** |
|---------------|---------------|
| **Comunicação** | Código compreensível por não-programadores para validação de requisitos |
| **Manutenibilidade** | Código lido muitas vezes deve ser legível e agradável de manter |

---

## 📋 Resumo Executivo

### 🎯 Conceitos-Chave

| **Conceito** | **Definição** | **Importância** |
|-------------|---------------|------------------|
| **DSL** | Linguagem customizada para domínio específico | Ponte entre desenvolvedores e especialistas |
| **Comunicação** | Código compreensível por não-programadores | Validação de requisitos de negócio |
| **Lambdas** | Ferramenta poderosa para reduzir verbosidade | Torna Java viável para DSLs elegantes |

### ⚖️ Análise Custo-Benefício

#### ✅ Benefícios das DSLs
- **Concisão:** Menos código repetitivo
- **Legibilidade:** Vocabulário do domínio 
- **Manutenibilidade:** Código mais fácil de modificar
- **Abstração:** Esconde detalhes irrelevantes
- **Foco:** Concentração na lógica de negócio
- **Separação:** Isola regras de negócio da infraestrutura

#### ❌ Custos das DSLs
- **Design complexo:** Difícil capturar conhecimento do domínio
- **Investimento inicial:** Alto custo de desenvolvimento
- **Indireção:** Camada adicional com possível impacto na performance
- **Curva de aprendizado:** Mais uma linguagem para aprender
- **Limitações:** Sintaxe restrita pela linguagem hospedeira

### 🏗️ Tipos de DSL na JVM

#### 📊 Comparação Estratégica

| **Tipo** | **Esforço** | **Flexibilidade** | **Integração** | **Quando Usar** |
|----------|-------------|-------------------|----------------|-----------------|
| **Interna** | Baixo | Limitada | Excelente | Usuários técnicos, integração simples |
| **Poliglota** | Médio | Alta | Boa | DSLs complexas, equipe experiente |
| **Externa** | Alto | Máxima | Complexa | Usuários não-técnicos, máxima flexibilidade |

### 🛠️ Padrões de Implementação

#### 🎯 Padrões e Casos de Uso

| **Padrão** | **Melhor Para** | **Exemplo** |
|------------|-----------------|-------------|
| **Method Chaining** | APIs fluentes, sequência específica | `builder.setName().setAge().build()` |
| **Nested Functions** | Estruturas hierárquicas | `order("client", buy(100, stock("IBM")))` |
| **Lambda Sequencing** | Configuração flexível | `order(o -> { o.client("x"); o.buy(...); })` |
| **Method References** | Operações similares sequenciais | `calculator.with(Tax::regional).with(Tax::surcharge)` |
| **Híbrido** | DSLs complexas | Combinação inteligente dos padrões |

### 🌟 APIs Java Nativas como DSL

#### 📊 Exemplos Práticos

| **API** | **Padrão** | **Vantagem** | **Uso** |
|---------|------------|--------------|---------|
| **Stream** | Fluente | Pipeline intuitivo | Processamento de dados |
| **Comparator** | Fluente | Composição natural | Ordenação complexa |
| **Collectors** | Aninhado | Execução técnica clara | Agrupamento multinível |

### 🌍 Casos Reais de Sucesso

#### 🏆 Frameworks com DSLs

| **Framework** | **Domínio** | **Padrão** | **Benefício Principal** |
|---------------|-------------|------------|-------------------------|
| **jOOQ** | SQL/Database | Method Chaining | Type-safety + Fluência |
| **Cucumber** | Testing/BDD | Externa + Interna | Documentação Executável |
| **Spring Integration** | Enterprise Integration | Chaining + Lambdas | Configuração Simplificada |

### 📈 Impacto do Java 8+

#### 🔄 Antes vs Depois

**Antes do Java 8:**
- Classes anônimas verbosas
- DSLs limitadas e deselegantes
- Alternativas JVM necessárias

**Java 8+:**
- Lambdas e method references
- DSLs concisas e elegantes
- Java viável para DSLs internas

### 🎯 Diretrizes para Decisão

#### ✅ Quando Implementar DSL

1. **Lógica de negócio complexa** e frequentemente alterada
2. **Necessidade de comunicação** com especialistas do domínio
3. **Código repetitivo** e verboso
4. **Benefícios superam custos** de desenvolvimento

#### ❌ Quando Evitar DSL

1. **Lógica simples** e estável
2. **Equipe pequena** sem experiência
3. **Prazo apertado** para entrega
4. **Performance crítica** sem tolerância à overhead

### 🔧 Checklist de Implementação

#### 📋 Passos Essenciais

1. **Análise do domínio** e identificação de padrões
2. **Seleção do tipo** de DSL apropriado
3. **Escolha dos padrões** de implementação
4. **Desenvolvimento iterativo** com feedback
5. **Testes extensivos** e documentação
6. **Treinamento da equipe** e usuários

### 💡 Lições Aprendidas

#### 🎯 Principais Insights

1. **Combinação de padrões** é comum e eficaz
2. **Type-safety** é crucial para DSLs de produção
3. **Integração com APIs existentes** potencializa DSLs
4. **Lambdas** reduzem significativamente verbosidade
5. **Documentação executável** é benefício valioso

#### 🚀 Tendências Futuras

- **DSLs híbridas** combinando múltiplos padrões
- **Integração com IA** para geração de DSLs
- **Tooling aprimorado** para desenvolvimento
- **Performance otimizada** com compilação especializada

### 🏁 Conclusão Final

**DSLs bem projetadas** são ferramentas poderosas que podem transformar a produtividade e legibilidade do código. O **Java 8+** com lambdas e method references tornou viável criar DSLs internas elegantes, reduzindo a necessidade de linguagens alternativas.

**Sucesso depende de:**
- Análise cuidadosa do domínio
- Seleção apropriada de padrões
- Implementação iterativa
- Balanceamento entre benefícios e custos

**O futuro das DSLs** está na combinação inteligente de padrões e integração com ferramentas modernas de desenvolvimento, sempre priorizando a **comunicação clara** e **manutenibilidade** do código.

---

## ⚖️ Prós e Contras das DSLs

### ✅ Benefícios das DSLs

| **Benefício** | **Descrição** | **Impacto** |
|---------------|---------------|-------------|
| **Concisão** | Encapsula lógica de negócio, evitando repetição | Código menos verboso |
| **Legibilidade** | Vocabulário do domínio compreensível por não-especialistas | Conhecimento compartilhado |
| **Manutenibilidade** | Código mais fácil de modificar | Especialmente importante para lógica de negócio |
| **Abstração** | Operações no nível do domínio | Esconde detalhes irrelevantes |
| **Foco** | Linguagem dedicada às regras de negócio | Maior produtividade |
| **Separação** | Lógica de negócio isolada da infraestrutura | Código mais organizado |

### ❌ Desvantagens das DSLs

| **Desvantagem** | **Descrição** | **Impacto** |
|-----------------|---------------|-------------|
| **Complexidade do Design** | Difícil capturar conhecimento do domínio | Requer experiência em design |
| **Custo de Desenvolvimento** | Investimento de longo prazo com alto custo inicial | Pode atrasar projetos |
| **Indireção** | Camada adicional sobre o modelo de domínio | Possível impacto na performance |
| **Curva de Aprendizado** | Mais uma linguagem para a equipe aprender | Overhead de treinamento |
| **Limitações da Linguagem** | Sintaxe restrita pela linguagem hospedeira | Java é particularmente verboso |

### 🎯 Quando Vale a Pena?

**✅ Use DSL quando:**
- Lógica de negócio complexa e frequentemente alterada
- Necessidade de comunicação com especialistas do domínio
- Código repetitivo e verboso
- Benefícios superam custos de desenvolvimento

**❌ Evite DSL quando:**
- Lógica simples e estável
- Equipe pequena ou sem experiência
- Prazo apertado
- Performance crítica

## 🏗️ Tipos de DSLs na JVM

### 📊 Comparação dos Tipos

| **Tipo** | **Implementação** | **Linguagem** | **Esforço** | **Flexibilidade** |
|----------|------------------|---------------|-------------|------------------|
| **Interna** | Sobre linguagem hospedeira | Java | Baixo | Limitada |
| **Poliglota** | Outras linguagens JVM | Scala, Groovy, Kotlin | Médio | Alta |
| **Externa** | Linguagem independente | Custom + Parser | Alto | Máxima |

---

### 1️⃣ DSL Interna (Embedded)

**Conceito:** Implementada na mesma linguagem da aplicação (Java).

#### ✅ Vantagens

- **Baixo esforço** de aprendizado
- **Compilação integrada** com o resto do código
- **Sem ferramentas externas** necessárias
- **Suporte completo da IDE** (autocompletar, refactoring)
- **Composição fácil** de múltiplas DSLs

#### ❌ Desvantagens

- **Limitada pela sintaxe** da linguagem hospedeira
- **Verbosidade** do Java
- **Menor flexibilidade** sintática

#### 🎯 Quando Usar

- Usuários tecnicamente orientados
- Equipe já familiarizada com Java
- Integração com código existente
- Múltiplas DSLs no mesmo projeto

---

### 2️⃣ DSL Poliglota (Alternativas JVM)

**Conceito:** Usa outras linguagens que rodam na JVM (100+ disponíveis).

#### 🌟 Exemplo Scala: Função `times`

```scala
// Evolução da implementação
times(3, println("Hello World"))           // 1. Básica - verbosa

times(3) {                                 // 2. Com currying
    println("Hello World")
}

3 times {                                  // 3. DSL elegante
    println("Hello World")
}
```

**"Mágica" do Scala:** Conversão implícita transforma `3` em objeto com método `times`.

#### ✅ Vantagens

- **Sintaxe menos verbosa** que Java
- **Recursos avançados** (currying, conversões implícitas)
- **DSLs mais naturais** e elegantes
- **Interoperabilidade** com Java

#### ❌ Desvantagens

- **Nova linguagem** para aprender
- **Build mais complexo** (múltiplos compiladores)
- **Interoperabilidade imperfeita** (collections diferentes)
- **Possível perda de performance** nas conversões
- **Necessidade de especialistas** ou treinamento

#### 🎯 Quando Usar

- Equipe experiente ou disposta a aprender
- DSL muito complexa que se beneficia da flexibilidade
- Performance não é crítica
- Benefícios superam complexidade adicional

---

### 3️⃣ DSL Externa (Standalone)

**Conceito:** Linguagem completamente nova, independente da hospedeira.

#### 🛠️ Implementação

1. **Projetar** nova linguagem com sintaxe própria
2. **Configurar** infraestrutura de parsing
3. **Analisar** saída do parser
4. **Gerar** código executável

**Ferramenta recomendada:** ANTLR (gerador de parser)

#### ✅ Vantagens

- **Máxima flexibilidade** sintática
- **Sintaxe otimizada** para o domínio
- **Independência** da linguagem hospedeira
- **Expressividade total** do domínio

#### ❌ Desvantagens

- **Muito trabalho** de implementação
- **Habilidades especializadas** necessárias
- **Ferramentas complexas** (parsers, geradores)
- **Manutenção adicional** da linguagem
- **Integração complexa** com sistema existente

#### 🎯 Quando Usar

- Domínio muito específico e complexo
- Equipe com expertise em compiladores
- Investimento de longo prazo justificado
- Usuários finais não-técnicos

---

### 🎯 Guia de Decisão

```
Precisa de máxima flexibilidade sintática?
├── SIM → DSL Externa
└── NÃO → Continue

Equipe tem experiência com outras linguagens JVM?
├── SIM → DSL Poliglota (Scala/Groovy)
└── NÃO → Continue

Integração simples com Java é prioridade?
├── SIM → DSL Interna (Java)
└── NÃO → Reconsidere necessidade da DSL
```

**Conclusão:** Java 8+ com lambdas reduziu significativamente a lacuna, tornando DSLs internas mais viáveis e mantendo simplicidade de integração.


## 🔧 DSLs nas APIs Modernas do Java

### 💡 Impacto das Lambdas no Java 8+

Antes do Java 8, as APIs nativas já tinham interfaces funcionais, mas exigiam classes anônimas verbosas. **Lambdas e method references** revolucionaram o design de APIs Java.

### 🎯 Exemplo: Ordenação com DSL

```java
// Antes do Java 8 - verboso
Collections.sort(persons, new Comparator<Person>() {
    public int compare(Person p1, Person p2) {
        return p1.getAge() - p2.getAge();
    }
});

// Java 8+ - conciso e legível
persons.sort(comparing(Person::getAge).thenComparing(Person::getName));
```

---

### 📊 Stream API como DSL

A **Stream API** é um excelente exemplo de DSL interna para manipular coleções.

#### 🔍 Exemplo Prático: Processar Log de Erros

**Objetivo:** Ler arquivo de log e coletar primeiras 40 linhas que começam com "ERROR".

**❌ Estilo Imperativo - Verboso:**
```java
List<String> errors = new ArrayList<>();
int errorCount = 0;
BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
String line = bufferedReader.readLine();
while (errorCount < 40 && line != null) {
    if (line.startsWith("ERROR")) {
        errors.add(line);
        errorCount++;
    }
    line = bufferedReader.readLine();
}
```

**✅ Stream API - Conciso e Legível:**
```java
List<String> errors = Files.lines(Paths.get(fileName))
    .filter(line -> line.startsWith("ERROR"))
    .limit(40)
    .collect(toList());
```

#### 🎯 Características da Stream API

| **Aspecto** | **Descrição** |
|-------------|---------------|
| **Estilo Fluente** | Operações encadeadas em pipeline |
| **Lazy Evaluation** | Operações intermediárias são lazy |
| **Eager Execution** | Operação terminal dispara computação |
| **Legibilidade** | Código autodocumentado |

---

### 🗃️ Collectors como DSL de Agregação

**Collectors** funciona como DSL para agregação, mas usa **estilo aninhado** ao invés de fluente.

#### 📈 Comparação de Estilos

**Comparator (Fluente):**
```java
Comparator<Person> comparator = 
    comparing(Person::getAge).thenComparing(Person::getName);
// Ordem natural: primeiro idade, depois nome
```

**Collectors (Aninhado):**
```java
Map<String, Map<Color, List<Car>>> carsByBrandAndColor =
    cars.stream().collect(
        groupingBy(Car::getBrand,           // 1º agrupamento (externo)
            groupingBy(Car::getColor)       // 2º agrupamento (interno)
        )
    );
```

#### 🤔 Por que Aninhamento?

**Razão técnica:** Collector **interno executa primeiro**, mas **logicamente é o último agrupamento**.

**Tentativa fluente problemática:**
```java
// Ordem contraintuitiva
groupOn(Car::getColor).after(Car::getBrand).get()
// Você escreve na ordem reversa da execução!
```

**Design deliberado:** Aninhamento reflete execução real e evita confusão de ordem.

---

### 🎯 Lições das DSLs Java Nativas

| **API** | **Padrão** | **Vantagem** | **Caso de Uso** |
|---------|------------|--------------|-----------------|
| **Comparator** | Fluente | Ordem natural de leitura | Ordenação complexa |
| **Stream** | Fluente | Pipeline intuitivo | Processamento de dados |
| **Collectors** | Aninhado | Execução técnica clara | Agrupamento multinível |

**Conclusão:** Lambdas transformaram Java em plataforma viável para DSLs elegantes e funcionais.


## 🛠️ Padrões para Criar DSLs em Java

### 🎯 Domínio de Exemplo: Sistema de Trading

Para demonstrar os padrões, vamos usar um sistema de trading com estas classes:

#### 📊 Modelo de Domínio

```java
public class Stock {
    private String symbol;    // Símbolo da ação (IBM, GOOGLE)
    private String market;    // Mercado (NYSE, NASDAQ)
    // getters/setters...
}

public class Trade {
    public enum Type { BUY, SELL }
    
    private Type type;        // Tipo da operação
    private Stock stock;      // Ação negociada
    private int quantity;     // Quantidade
    private double price;     // Preço unitário
    // getters/setters...
    
    public double getValue() { 
        return quantity * price; 
    }
}

public class Order {
    private String customer;        // Cliente
    private List<Trade> trades;     // Lista de operações
    // getters/setters...
    
    public double getValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}
```

#### ❌ Código Tradicional - Verboso

```java
Order order = new Order();
order.setCustomer("BigBank");

Trade trade1 = new Trade();
trade1.setType(Trade.Type.BUY);
Stock stock1 = new Stock();
stock1.setSymbol("IBM");
stock1.setMarket("NYSE");
trade1.setStock(stock1);
trade1.setPrice(125.00);
trade1.setQuantity(80);
order.addTrade(trade1);

Trade trade2 = new Trade();
trade2.setType(Trade.Type.BUY);
Stock stock2 = new Stock();
stock2.setSymbol("GOOGLE");
stock2.setMarket("NASDAQ");
trade2.setStock(stock2);
trade2.setPrice(375.00);
trade2.setQuantity(50);
order.addTrade(trade2);
```

**Problema:** Código verboso e difícil de validar por especialistas do domínio.

---

### 🔗 Padrão 1: Method Chaining

#### 💡 Objetivo
Permitir definir order com uma cadeia fluente de métodos.

#### ✅ Resultado Esperado
```java
Order order = forCustomer("BigBank")
    .buy(80)
    .stock("IBM")
        .on("NYSE")
    .at(125.00)
    .sell(50)
    .stock("GOOGLE")
        .on("NASDAQ")
    .at(375.00)
    .end();
```

#### 🏗️ Implementação

**Builder Principal:**
```java
public class MethodChainingOrderBuilder {
    public final Order order = new Order();
    
    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }
    
    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }
    
    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }
    
    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }
    
    public MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade);
        return this; // Permite encadeamento
    }
    
    public Order end() {
        return order;
    }
}
```

**Builders Auxiliares:**
```java
public class TradeBuilder {
    private final MethodChainingOrderBuilder builder;
    public final Trade trade = new Trade();
    
    private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
        this.builder = builder;
        trade.setType(type);
        trade.setQuantity(quantity);
    }
    
    public StockBuilder stock(String symbol) {
        return new StockBuilder(builder, trade, symbol);
    }
}

public class StockBuilder {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();
    
    public TradeBuilderWithStock on(String market) {
        stock.setMarket(market);
        trade.setStock(stock);
        return new TradeBuilderWithStock(builder, trade);
    }
}

public class TradeBuilderWithStock {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    
    public MethodChainingOrderBuilder at(double price) {
        trade.setPrice(price);
        return builder.addTrade(trade);
    }
}
```

#### ✅ Vantagens
- **Argumentos nomeados** através de nomes de métodos
- **Ordem forçada** de chamadas
- **Uso mínimo** de métodos estáticos
- **Baixo ruído** sintático

#### ❌ Desvantagens
- **Implementação verbosa** (muitos builders)
- **Código de ligação** complexo
- **Indentação** apenas por convenção

---

### 📦 Padrão 2: Nested Functions

#### 💡 Objetivo
Usar funções aninhadas para refletir estrutura hierárquica.

#### ✅ Resultado Esperado
```java
Order order = order("BigBank",
    buy(80, stock("IBM", on("NYSE")), at(125.00)),
    sell(50, stock("GOOGLE", on("NASDAQ")), at(375.00))
);
```

#### 🏗️ Implementação

```java
public class NestedFunctionOrderBuilder {
    
    public static Order order(String customer, Trade... trades) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(trades).forEach(order::addTrade);
        return order;
    }
    
    public static Trade buy(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.BUY);
    }
    
    public static Trade sell(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.SELL);
    }
    
    private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type type) {
        Trade trade = new Trade();
        trade.setQuantity(quantity);
        trade.setType(type);
        trade.setStock(stock);
        trade.setPrice(price);
        return trade;
    }
    
    public static Stock stock(String symbol, String market) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setMarket(market);
        return stock;
    }
    
    public static double at(double price) {
        return price; // Método dummy para legibilidade
    }
    
    public static String on(String market) {
        return market; // Método dummy para legibilidade
    }
}
```

#### ✅ Vantagens
- **Implementação simples** (poucos builders)
- **Hierarquia clara** através do aninhamento
- **Estrutura natural** do domínio preservada

#### ❌ Desvantagens
- **Uso intenso** de métodos estáticos
- **Argumentos por posição** ao invés de nome
- **Sobrecarga necessária** para parâmetros opcionais

---

### 🔀 Padrão 3: Function Sequencing com Lambdas

#### 💡 Objetivo
Usar lambdas para configurar objects, combinando fluência e estrutura.

#### ✅ Resultado Esperado
```java
Order order = order(o -> {
    o.forCustomer("BigBank");
    o.buy(t -> {
        t.quantity(80);
        t.price(125.00);
        t.stock(s -> {
            s.symbol("IBM");
            s.market("NYSE");
        });
    });
    o.sell(t -> {
        t.quantity(50);
        t.price(375.00);
        t.stock(s -> {
            s.symbol("GOOGLE");
            s.market("NASDAQ");
        });
    });
});
```

#### 🏗️ Implementação

```java
public class LambdaOrderBuilder {
    private Order order = new Order();
    
    public static Order order(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder);
        return builder.order;
    }
    
    public void forCustomer(String customer) {
        order.setCustomer(customer);
    }
    
    public void buy(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.BUY);
    }
    
    public void sell(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.SELL);
    }
    
    private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade.setType(type);
        consumer.accept(builder);
        order.addTrade(builder.trade);
    }
}

public class TradeBuilder {
    private Trade trade = new Trade();
    
    public void quantity(int quantity) {
        trade.setQuantity(quantity);
    }
    
    public void price(double price) {
        trade.setPrice(price);
    }
    
    public void stock(Consumer<StockBuilder> consumer) {
        StockBuilder builder = new StockBuilder();
        consumer.accept(builder);
        trade.setStock(builder.stock);
    }
}

public class StockBuilder {
    private Stock stock = new Stock();
    
    public void symbol(String symbol) {
        stock.setSymbol(symbol);
    }
    
    public void market(String market) {
        stock.setMarket(market);
    }
}
```

#### ✅ Vantagens
- **Estilo fluente** como method chaining
- **Estrutura hierárquica** preservada
- **Flexibilidade** de configuração

#### ❌ Desvantagens
- **Código de configuração** verboso
- **Ruído sintático** das lambdas
- **Sintaxe Java** ainda visível

---

### 🎯 Comparação dos Padrões

| **Padrão** | **Legibilidade** | **Implementação** | **Flexibilidade** | **Uso Ideal** |
|------------|------------------|-------------------|------------------|---------------|
| **Method Chaining** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐ | Sequência específica |
| **Nested Functions** | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ | Estrutura hierárquica |
| **Lambda Sequencing** | ⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐⭐⭐ | Configuração flexível |

**Próximo:** Veremos como combinar os três padrões para máxima expressividade.


### 🔄 Padrão 4: Abordagem Mista (Híbrida)

#### 💡 Conceito
Combinar os três padrões anteriores em uma única DSL, aproveitando os pontos fortes de cada um conforme necessário.

#### ✅ Resultado Esperado
```java
Order order = forCustomer("BigBank",
    buy(t -> t.quantity(80)          // Lambda expression para criar trade
            .stock("IBM")            // Method chaining no corpo da lambda
            .on("NYSE")              // que popula o objeto trade
            .at(125.00)),
    sell(t -> t.quantity(50)         // Nested function para especificar
            .stock("GOOGLE")         // atributos da order de alto nível
            .on("NASDAQ")
            .at(375.00))
);
```

#### 🏗️ Como Funciona

**1. Nível superior:** Nested functions
```java
public static Order forCustomer(String customer, TradeBuilder... builders) {
    Order order = new Order();
    order.setCustomer(customer);
    Stream.of(builders).forEach(b -> order.addTrade(b.trade));
    return order;
}
```

**2. Nível intermediário:** Lambda expressions
```java
public static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
    return buildTrade(consumer, Trade.Type.BUY);
}
```

**3. Nível interno:** Method chaining
```java
public class TradeBuilder {
    public TradeBuilder quantity(int quantity) { 
        trade.setQuantity(quantity); 
        return this; 
    }
    
    public TradeBuilder at(double price) { 
        trade.setPrice(price); 
        return this; 
    }
    
    public StockBuilder stock(String symbol) { 
        return new StockBuilder(this, trade, symbol); 
    }
}
```

#### ✅ Vantagens
- **Combina pontos fortes** de cada padrão
- **Flexibilidade máxima** de expressão
- **Sintaxe compacta** onde mais importa
- **Diferentes níveis** de abstração apropriados

#### ❌ Desvantagens
- **DSL menos uniforme** que técnica única
- **Curva de aprendizado** maior para usuários
- **Mais complexidade** de implementação
- **Mais código** para manter

#### 🎯 Quando Usar
- DSL muito complexa com múltiplas necessidades
- Benefícios superam complexidade adicional
- Equipe experiente em DSL design
- Múltiplos tipos de usuários (iniciantes vs. avançados)

---

### 🔧 Padrão 5: Method References em DSL

#### 🚨 Problema: Calculadora de Impostos

**Implementação problemática com flags:**
```java
// ❌ Uso confuso - qual flag é qual?
double value = calculate(order, true, false, true);
```

**Builder tradicional verboso:**
```java
// ❌ Não escala - um método para cada imposto
double value = new TaxCalculator()
    .withTaxRegional()
    .withTaxSurcharge()
    .calculate(order);
```

#### 💡 Solução Funcional

**Classes de impostos:**
```java
public class Tax {
    public static double regional(double value) { return value * 1.1; }
    public static double general(double value) { return value * 1.3; }
    public static double surcharge(double value) { return value * 1.05; }
}
```

**Calculator funcional:**
```java
public class TaxCalculator {
    public DoubleUnaryOperator taxFunction = d -> d; // Função identidade
    
    public TaxCalculator with(DoubleUnaryOperator f) {
        taxFunction = taxFunction.andThen(f); // Composição de funções
        return this;
    }
    
    public double calculate(Order order) {
        return taxFunction.applyAsDouble(order.getValue());
    }
}
```

**✅ Uso elegante:**
```java
double value = new TaxCalculator()
    .with(Tax::regional)
    .with(Tax::surcharge)
    .calculate(order);
```

#### 🎯 Heurística para Aplicar o Padrão

### 🚨 Sinais de Alerta para Aplicar o Padrão

#### **1. Múltiplos Parâmetros Booleanos:**
```java
// ❌ Red flag - muitos booleans
calculate(order, true, false, true, false, true);
processData(input, false, true, true, false);
formatText(text, true, false, true, true);
```

#### **2. Listas Longas de Configurações:**
```java
// ❌ Verboso e não escalável
new ConfigBuilder()
    .withFeatureA()
    .withFeatureB() 
    .withFeatureC()
    .withFeatureD()
    .withFeatureE()
    .withFeatureF(); // Um método para cada feature
```

#### **3. Operações Sequenciais do Mesmo Tipo:**
```java
// ❌ Código repetitivo
data = transform1(data);
data = transform2(data);
data = transform3(data);
data = transform4(data);
```

#### **4. Configurações Opcionais Complexas:**
```java
// ❌ Difícil de entender e manter
if (useValidation1) validate1(input);
if (useValidation2) validate2(input);
if (useValidation3) validate3(input);
if (useValidation4) validate4(input);
```

### 📋 Template Base Reutilizável

```java
public class [Nome]Processor {
    private [TipoFuncao] processingFunction = [funcaoIdentidade];
    
    public [Nome]Processor with([TipoFuncao] operation) {
        processingFunction = processingFunction.[metodoComposicao](operation);
        return this;
    }
    
    public [TipoRetorno] process([TipoEntrada] input) {
        return processingFunction.[metodoExecucao](input);
    }
}
```

### 🔄 Tipos de Função Comuns

| **Tipo de Operação** | **Interface Funcional** | **Método Composição** | **Função Identidade** |
|---------------------|-------------------------|----------------------|----------------------|
| **Transformação** | `UnaryOperator<T>` | `.andThen()` | `UnaryOperator.identity()` |
| **Validação** | `Predicate<T>` | `.and()` | `t -> true` |
| **Cálculos Numéricos** | `DoubleUnaryOperator` | `.andThen()` | `d -> d` |
| **Filtros** | `Predicate<T>` | `.and()` | `t -> true` |

### 🛠️ Soluções Padrão por Categoria

#### **1. Transformação de Dados**
```java
public class DataTransformer {
    private UnaryOperator<String> pipeline = UnaryOperator.identity();
    
    public DataTransformer with(UnaryOperator<String> transformer) {
        pipeline = pipeline.andThen(transformer);
        return this;
    }
    
    public String transform(String input) {
        return pipeline.apply(input);
    }
}

// Uso:
String result = new DataTransformer()
    .with(String::trim)
    .with(String::toLowerCase)
    .with(s -> s.replace(" ", "_"))
    .transform("  Hello World  ");
```

#### **2. Validação**
```java
public class Validator {
    private Predicate<Object> validationChain = obj -> true;
    
    public Validator with(Predicate<Object> validation) {
        validationChain = validationChain.and(validation);
        return this;
    }
    
    public boolean validate(Object input) {
        return validationChain.test(input);
    }
}

// Uso:
boolean isValid = new Validator()
    .with(Objects::nonNull)
    .with(obj -> obj.toString().length() > 0)
    .with(obj -> !obj.toString().trim().isEmpty())
    .validate(input);
```

#### **3. Cálculos Numéricos**
```java
public class Calculator {
    private DoubleUnaryOperator operation = value -> value;
    
    public Calculator with(DoubleUnaryOperator calculation) {
        operation = operation.andThen(calculation);
        return this;
    }
    
    public double calculate(double input) {
        return operation.applyAsDouble(input);
    }
}

// Uso:
double result = new Calculator()
    .with(value -> value * 1.1)      // Taxa 1
    .with(value -> value * 1.05)     // Taxa 2
    .with(value -> value + 10)       // Taxa fixa
    .calculate(100.0);
```

#### **4. Filtros**
```java
public class Filter {
    private Predicate<Object> filterChain = obj -> true;
    
    public Filter with(Predicate<Object> filter) {
        filterChain = filterChain.and(filter);
        return this;
    }
    
    public List<Object> filter(List<Object> items) {
        return items.stream().filter(filterChain).collect(toList());
    }
}

// Uso:
List<String> filtered = new Filter()
    .with(Objects::nonNull)
    .with(s -> s.toString().startsWith("A"))
    .with(s -> s.toString().length() > 3)
    .filter(originalList);
```

### ✅ Checklist de Implementação

#### **Passos para Aplicar o Padrão:**

1. **Identifique o tipo de operação:**
   - Transformação → `UnaryOperator` + `andThen()`
   - Validação/Filtro → `Predicate` + `and()`
   - Cálculo → `DoubleUnaryOperator` + `andThen()`

2. **Crie a classe processadora:**
   - Campo com função identidade
   - Método `with()` para composição
   - Método de execução final

3. **Extraia operações para métodos estáticos:**
   ```java
   public class Operations {
       public static String normalize(String s) { 
           return s.trim().toLowerCase(); 
       }
       
       public static boolean isValid(Object o) { 
           return o != null && !o.toString().trim().isEmpty(); 
       }
       
       public static double applyTax(double d) { 
           return d * 1.1; 
       }
       
       public static boolean isPositive(Number n) {
           return n.doubleValue() > 0;
       }
   }
   ```

4. **Use com method references:**
   ```java
   // Transformação
   String result = new DataTransformer()
       .with(Operations::normalize)
       .with(s -> s.replace(" ", "_"))
       .transform(input);
   
   // Validação
   boolean isValid = new Validator()
       .with(Operations::isValid)
       .with(s -> s.length() > 5)
       .validate(input);
   
   // Cálculo
   double total = new Calculator()
       .with(Operations::applyTax)
       .with(value -> value * 0.9) // Desconto
       .calculate(baseValue);
   ```

### 🎯 Exemplo de Aplicação Rápida

#### **Problema identificado:**
```java
// ❌ Red flag detectado
formatText(text, true, false, true, true);
```

#### **Solução aplicada:**
```java
// ✅ Padrão aplicado
public class TextFormatter {
    private UnaryOperator<String> formatter = UnaryOperator.identity();
    
    public TextFormatter with(UnaryOperator<String> operation) {
        formatter = formatter.andThen(operation);
        return this;
    }
    
    public String format(String text) {
        return formatter.apply(text);
    }
}

// Operações específicas
public class TextOperations {
    public static String uppercase(String s) {
        return s.toUpperCase();
    }
    
    public static String addBrackets(String s) {
        return "[" + s + "]";
    }
    
    public static String removeSpaces(String s) {
        return s.replace(" ", "");
    }
    
    public static String addPrefix(String s) {
        return ">>> " + s;
    }
}

// Uso elegante
String result = new TextFormatter()
    .with(TextOperations::uppercase)
    .with(TextOperations::addBrackets)
    .with(TextOperations::removeSpaces)
    .with(TextOperations::addPrefix)
    .format("hello world");
// Resultado: ">>>[HELLOWORLD]"
```

### 📊 Indicadores de Sucesso

#### **✅ Sinais de que o Padrão está Funcionando:**

- **Legibilidade:** Código lê como linguagem natural
- **Flexibilidade:** Fácil adicionar novas operações
- **Reutilização:** Operações podem ser usadas independentemente
- **Composição:** Combinações complexas ficam simples
- **Manutenção:** Mudanças localizadas em funções específicas

#### **❌ Quando NÃO Usar:**

- **Operações únicas:** Se só vai usar uma vez
- **Lógica muito simples:** Um único if/else resolve
- **Performance crítica:** Overhead de composição pode importar
- **APIs já estabelecidas:** Não vale refatorar código estável

**Resultado:** Código mais legível, flexível e maintível!

#### ✅ Vantagens
- **Legível:** Method references tornam intenção clara
- **Conciso:** Menos código que builders tradicionais
- **Flexível:** Novos elementos funcionam automaticamente
- **Funcional:** Composição ao invés de estado mutável

---

### 📊 Resumo Comparativo dos Padrões

| **Padrão** | **Prós** | **Contras** | **Melhor Para** |
|------------|----------|-------------|-----------------|
| **Method Chaining** | • Argumentos nomeados<br>• Ordem forçada<br>• Pouco ruído sintático | • Implementação verbosa<br>• Código de ligação<br>• Indentação por convenção | APIs fluentes com sequência específica |
| **Nested Functions** | • Implementação simples<br>• Hierarquia natural | • Métodos estáticos<br>• Argumentos posicionais<br>• Sobrecarga necessária | Estruturas hierárquicas naturais |
| **Lambda Sequencing** | • Parâmetros opcionais<br>• Pouco estático<br>• Hierarquia clara | • Implementação verbosa<br>• Ruído das lambdas | Configurações flexíveis |
| **Abordagem Mista** | • Máxima flexibilidade<br>• Combina vantagens | • Menos uniforme<br>• Curva de aprendizado | DSLs complexas e variadas |
| **Method References** | • Muito legível<br>• Escalável<br>• Funcional | • Abstração adicional<br>• Nem sempre aplicável | Operações sequenciais similares |

**Conclusão:** A escolha do padrão depende do domínio, usuários, e complexidade. É possível e recomendado combinar padrões conforme necessário.


## 🌍 DSLs Java em Projetos Reais

### 📊 jOOQ - SQL Type-Safe

**Objetivo:** Escrever consultas SQL de forma type-safe e fluente em Java.

#### 🎯 Exemplo Básico
```java
create.selectFrom(BOOK)
    .where(BOOK.PUBLISHED_IN.eq(2016))
    .orderBy(BOOK.TITLE)
```

#### 🔧 Exemplo Completo
```java
// Conexão com banco
Class.forName("org.h2.Driver");
try (Connection c = getConnection("jdbc:h2:~/sql-goodies-with-mapping", "sa", "")) {
    
    DSL.using(c)
        .select(BOOK.AUTHOR, BOOK.TITLE)
        .where(BOOK.PUBLISHED_IN.eq(2016))
        .orderBy(BOOK.TITLE)
        .fetch()                    // jOOQ termina aqui
        .stream()                   // Stream API inicia aqui
        .collect(groupingBy(
            r -> r.getValue(BOOK.AUTHOR),
            LinkedHashMap::new,
            mapping(r -> r.getValue(BOOK.TITLE), toList())))
        .forEach((author, titles) ->
            System.out.println(author + " is author of " + titles));
}
```

#### ✅ Características
- **Type-safe:** Erros detectados em tempo de compilação
- **Fluente:** Sintaxe similar ao SQL mas com validação Java
- **Integração:** Combina perfeitamente com Stream API
- **Código gerado:** Classes geradas a partir do schema do banco

---

### 🧪 Cucumber - BDD Testing

**Objetivo:** Behavior-Driven Development com testes executáveis em linguagem natural.

#### 🎯 Conceito BDD
**BDD** estende TDD usando linguagem específica de domínio para descrever cenários de negócio, servindo como **testes executáveis e critérios de aceitação**.

#### 📋 Exemplo de Cenário
```gherkin
Feature: Buy stock
  Scenario: Buy 10 IBM stocks
    Given the price of a "IBM" stock is 125$
    When I buy 10 "IBM"
    Then the order value should be 1250$
```

**Estrutura:** Given (pré-condições) → When (ações) → Then (verificações)

#### 🔧 Implementação Java 8+

**Tradicional com annotations:**
```java
@Given("^the price of a \"(.*?)\" stock is (\\d+)\\$$")
public void setUnitPrice(String stockName, int unitPrice) {
    stockUnitValues.put(stockName, unitPrice);
}
```

**Com lambdas (mais conciso):**
```java
public BuyStocksSteps() {
    Given("^the price of a \"(.*?)\" stock is (\\d+)\\$$",
        (String stockName, int unitPrice) -> {
            stockUnitValues.put(stockName, unitPrice);
        });
}
```

#### ✅ Vantagens das Lambdas
- **Mais compacto** que annotations
- **Elimina necessidade** de nomes de métodos
- **Lógica próxima** da definição do cenário
- **Sintaxe mais limpa** e legível

#### 🎯 Resultado
- **DSL Externa:** Gherkin em inglês simples
- **DSL Interna:** Implementação Java com lambdas
- **Documentação Executável:** Testes que servem como especificação

---

### 🔄 Spring Integration - Enterprise Integration

**Objetivo:** Implementar Enterprise Integration Patterns com DSL fluente.

#### 🎯 Características
- **Modelo assíncrono** e orientado a mensagens
- **Patterns empresariais** (channels, endpoints, pollers)
- **DSL rica** além de açúcar sintático sobre XML

#### 🔧 Exemplo de IntegrationFlow
```java
@Bean
public IntegrationFlow myFlow() {
    return IntegrationFlows
        .from(this.integerMessageSource(),           // Fonte de dados
              c -> c.poller(Pollers.fixedRate(10)))  // Polling a cada 10ms
        .channel(this.inputChannel())                // Channel de entrada
        .filter((Integer p) -> p % 2 == 0)          // Filtra números pares
        .transform(Object::toString)                 // Converte para String
        .channel(MessageChannels.queue("queueChannel")) // Channel de saída
        .get();
}
```

**Fluxo:** MessageSource → Polling → Filter → Transform → Output Channel

#### 🔀 Variação com Lambda
```java
@Bean
public IntegrationFlow myFlow() {
    return flow -> flow.filter((Integer p) -> p % 2 == 0)
                      .transform(Object::toString)
                      .handle(System.out::println);
}
```

#### 🎯 Padrões Utilizados
- **Method Chaining** (principal): Ideal para pipelines de mensagens
- **Function Sequencing**: Para configurações complexas

#### ✅ Resultado
- **API similar ao Stream**: Processamento fluente de mensagens
- **Configuração simplificada**: Menos verbosa que XML
- **Flexibilidade:** Combine diferentes patterns facilmente

---

### 📊 Comparação das DSLs Reais

| **Framework** | **Domínio** | **Tipo** | **Padrão Principal** | **Vantagem** |
|---------------|-------------|----------|-------------------|-------------|
| **jOOQ** | SQL/Database | Interna | Method Chaining | Type-safety + Fluência |
| **Cucumber** | Testing/BDD | Externa + Interna | Híbrida | Documentação Executável |
| **Spring Integration** | Enterprise Integration | Interna | Method Chaining + Lambdas | Configuração Simplificada |

### 🎯 Lições dos Casos Reais

1. **Combinação de padrões** é comum e eficaz
2. **Type-safety** é crucial para DSLs de produção
3. **Integração com APIs existentes** (Stream, Collections) potencializa as DSLs
4. **Documentação executável** é um benefício valioso
5. **Lambdas** reduzem significativamente a verbosidade

**Conclusão:** DSLs bem projetadas melhoram drasticamente a produtividade e legibilidade em domínios específicos, especialmente quando combinam múltiplos padrões de forma inteligente.