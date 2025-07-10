# Java Collections API - Guia Detalhado de Estudo

## Collection Factories

### Arrays.asList() - Lista de Tamanho Fixo

```java
List<String> friends = Arrays.asList("Raphael", "Olivia", "Thibaut");
friends.set(0, "Richard"); // ✅ Permitido - atualização
// friends.add("John"); // ❌ UnsupportedOperationException
// friends.remove(0); // ❌ UnsupportedOperationException
```

Você obtém uma lista de tamanho fixo que pode ser atualizada, mas não é possível adicionar elementos ou remover elementos dela.

### Criando Sets - Opções Tradicionais

Para fazer com Set não temos um `Arrays.asSet()`, temos duas opções: utilizar o `new HashSet` com uma lista como parâmetro ou utilizar a Streams API.

```java
// Opção 1: Constructor com Collection
Set<String> friends = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));

// Opção 2: Streams API
Set<String> friends = Stream.of("Raphael", "Olivia", "Thibaut")
    .collect(Collectors.toSet());
```

### List Factory - Collections Imutáveis

Na verdade, a list que é produzida é imutável. Substituir um item com o method set() gera uma exception similar. Você também não conseguirá modificá-la usando o method set.

```java
List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
// friends.set(0, "Richard"); // ❌ UnsupportedOperationException
// friends.add("John"); // ❌ UnsupportedOperationException
```

> **Overloading vs. Varargs:** Por que List.of() tem múltiplas sobrecargas ao invés de usar apenas varargs? A resposta é performance. Java fornece métodos sobrecarregados para até 10 elementos (`List.of(e1, e2, e3...)`) que evitam a alocação extra de um array interno, eliminando o overhead de inicialização e garbage collection. Para mais de 10 elementos, a versão varargs é automaticamente invocada. Este mesmo padrão otimizado é usado em `Set.of()` e `Map.of()`, priorizando eficiência para os casos mais comuns.

### Set Factory

```java
Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
// Mesmas características de imutabilidade da List.of()
```

### Map Factory

```java
// Método direto (até 10 pares chave-valor)
Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
System.out.println(ageOfFriends);
```

O método abaixo utiliza varargs para mais de 10 elementos:

```java
import static java.util.Map.entry;

Map<String, Integer> ageOfFriends = Map.ofEntries(
    entry("Raphael", 30),
    entry("Olivia", 25),
    entry("Thibaut", 26)
);
System.out.println(ageOfFriends);
```

### Nota Final sobre Collection Factories

Essas collections são imutáveis, ou seja, não é possível adicionar ou remover elementos delas. Elas são criadas de forma mais eficiente e são mais seguras para uso em ambientes concorrentes.

---

## Working with List and Set

### Novos Methods de Mutação

- **removeIf**: remove elementos que correspondem a um predicate. Está disponível em todas as classes que implementam List ou Set (e é herdado da interface Collection).
- **replaceAll**: está disponível em List e substitui elementos usando uma function (UnaryOperator).
- **sort**: também está disponível na interface List e ordena a própria list.

**Importante:** Todos esses methods alteram as collections nas quais são invocados, diferentemente das operações de stream que produzem um novo resultado (copiado).

### removeIf - Evitando ConcurrentModificationException

**Problema:** Tentar remover elementos durante iteração com for-each pode causar ConcurrentModificationException, pois dois objects separados gerenciam a collection - o Iterator (next/hasNext) e a Collection (remove).

**Código problemático:**
```java
// ❌ Pode gerar ConcurrentModificationException
for (Transaction transaction : transactions) {
    if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
        transactions.remove(transaction); // Problema: modificando collection durante iteração
    }
}
```

**Por que acontece?** Por baixo dos panos, o for-each usa um Iterator. Dois objects separados gerenciam a collection:
- O Iterator (consultando com next() e hasNext())
- A Collection (removendo com remove())

O estado do iterator fica dessincronizado com o estado da collection.

**Solução Verbosa com Iterator:**
```java
for (Iterator<Transaction> iterator = transactions.iterator();
     iterator.hasNext(); ) {
    Transaction transaction = iterator.next();
    if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
        iterator.remove(); // ✅ Usar remove() do Iterator
    }
}
```

**Solução Elegante com removeIf:**
```java
transactions.removeIf(transaction ->
    Character.isDigit(transaction.getReferenceCode().charAt(0)));
```

### replaceAll - Modificando Elementos In-Place

**Problema:** Streams criam nova collection, mas às vezes queremos modificar a collection existente.

**Com Streams (cria nova collection):**
```java
List<String> upperCased = referenceCodes.stream()
    .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
    .collect(Collectors.toList());
// [a12, C14, b13] -> [A12, C14, B13] (nova lista)
```

**Solução Verbosa com ListIterator:**
```java
for (ListIterator<String> iterator = referenceCodes.listIterator();
     iterator.hasNext(); ) {
    String code = iterator.next();
    iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
}
```

**Solução Elegante com replaceAll:**
```java
referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) +
                          code.substring(1));
// Modifica a collection existente in-place
```

---

## Working with Map

### forEach - Iterando sobre Map

**Modo tradicional:**
```java
for(Map.Entry<String, Integer> entry: ageOfFriends.entrySet()) {
    String friend = entry.getKey();
    Integer age = entry.getValue();
    System.out.println(friend + " is " + age + " years old");
}
```

**Solução Elegante com forEach:**
```java
ageOfFriends.forEach((friend, age) ->
    System.out.println(friend + " is " + age + " years old"));
```

### Sorting Maps

Utilizando comparadores para ordenar entradas do Map:
- `Entry.comparingByValue()` - ordena por valor
- `Entry.comparingByKey()` - ordena por chave

```java
Map<String, String> favouriteMovies = Map.ofEntries(
    entry("Raphael", "Star Wars"),
    entry("Cristina", "Matrix"),
    entry("Olivia", "James Bond")
);

favouriteMovies
    .entrySet()
    .stream()
    .sorted(Entry.comparingByKey()) // Processa os elementos do stream em ordem alfabética baseada no nome da pessoa
    .forEachOrdered(System.out::println);
```

### getOrDefault - Valores Padrão

```java
Map<String, String> favouriteMovies = Map.ofEntries(
    entry("Raphael", "Star Wars"),
    entry("Olivia", "James Bond")
);

System.out.println(favouriteMovies.getOrDefault("Olivia", "Matrix")); // Star Wars
System.out.println(favouriteMovies.getOrDefault("Thibaut", "Matrix")); // Matrix (valor padrão)
```

### Compute Patterns

O Java oferece três methods para operações condicionais em Maps que facilitam cache e manipulação de valores:

**computeIfAbsent** - Calcula value apenas se key estiver ausente ou null. Útil para cache de operações caras:
```java
// Exemplo de cache de hash SHA-256
Map<String, byte[]> dataToHash = new HashMap<>();
MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

lines.forEach(line ->
    dataToHash.computeIfAbsent(line, this::calculateDigest));

private byte[] calculateDigest(String key) {
    return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
}
```

**computeIfPresent** - Calcula novo value apenas se key estiver presente. Remove mapping se function retornar null.

**compute** - Sempre calcula novo value para a key dada.

#### Vantagem Principal: Inicialização Automática de Collections

**Código verboso tradicional:**
```java
String friend = "Raphael";
List<String> movies = friendsToMovies.get(friend);
if(movies == null) {
    movies = new ArrayList<>();
    friendsToMovies.put(friend, movies);
}
movies.add("Star Wars");
System.out.println(friendsToMovies); // {Raphael=[Star Wars]}
```

**Código elegante com computeIfAbsent:**
```java
friendsToMovies.computeIfAbsent("Raphael", name -> new ArrayList<>())
               .add("Star Wars");
System.out.println(friendsToMovies); // {Raphael=[Star Wars]}
```

O computeIfAbsent automaticamente cria a List se não existir e retorna a List existente caso contrário, eliminando verificações manuais de null.

### Remove Patterns

```java
// Remove condicional - só remove se key mapeada para value específico
favouriteMovies.remove("Raphael", "Star Wars"); // Remove apenas se Raphael -> Star Wars

// Remove com predicate (disponível em algumas implementações)
favouriteMovies.entrySet().removeIf(entry -> entry.getKey().startsWith("O"));
```

### Replacement Patterns

O Map tem dois novos methods que permitem substituir as entradas dentro de um Map:

- **replaceAll** — Substitui o value de cada entrada com o resultado da aplicação de uma BiFunction. Este method funciona de forma similar ao replaceAll em uma List.
- **replace** — Permite substituir um value no Map se uma key estiver presente. Uma sobrecarga adicional substitui o value apenas se a key estiver mapeada para um determinado value.

```java
Map<String, String> favouriteMovies = new HashMap<>(); // Temos que usar um map mutável já que usaremos replaceAll
favouriteMovies.put("Raphael", "Star Wars");
favouriteMovies.put("Olivia", "james bond");
favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
System.out.println(favouriteMovies);
// {Olivia=JAMES BOND, Raphael=STAR WARS}

// Replace condicional
favouriteMovies.replace("Olivia", "JAMES BOND", "007"); // Só substitui se valor atual for "JAMES BOND"
```

### Merge - Combinando Maps

O method **merge** permite combinar dois Maps de forma flexível, especialmente quando há keys duplicadas. Enquanto **putAll** simplesmente sobrescreve values duplicados, merge permite definir como combinar os values conflitantes usando uma BiFunction.

#### Exemplo Básico - Combinando Movies
```java
Map<String, String> family = Map.ofEntries(
    entry("Teo", "Star Wars"), 
    entry("Cristina", "James Bond")
);
Map<String, String> friends = Map.ofEntries(
    entry("Raphael", "Star Wars"), 
    entry("Cristina", "Matrix")
);

Map<String, String> everyone = new HashMap<>(family);
friends.forEach((k, v) ->
    everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
// Result: {Raphael=Star Wars, Cristina=James Bond & Matrix, Teo=Star Wars}
```

#### Uso para Contadores
O merge também simplifica padrões de inicialização/incremento:

**Código verboso tradicional:**
```java
Map<String, Long> moviesToCount = new HashMap<>();
String movieName = "JamesBond";
Long count = moviesToCount.get(movieName);
if(count == null) {
    moviesToCount.put(movieName, 1L);
} else {
    moviesToCount.put(movieName, count + 1L);
}
```

**Código elegante com merge:**
```java
moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);
```

**Como funciona:** O merge automaticamente usa o value padrão (1L) se a key não existir, ou aplica a BiFunction se a key já existir. Se a function retornar null, o mapping é removido.

---

## Improved ConcurrentHashMap

A classe ConcurrentHashMap foi introduzida para fornecer um HashMap mais moderno, que também é amigável à concorrência. ConcurrentHashMap permite operações de adição e atualização concorrentes que fazem lock apenas de certas partes da estrutura de dados interna. Assim, operações de leitura e escrita têm performance melhorada comparadas com a alternativa Hashtable sincronizada. (Note que o HashMap padrão não é sincronizado.)

### Reduce and Search

ConcurrentHashMap oferece três tipos de operações inspiradas em streams que permitem processamento paralelo eficiente de Maps concorrentes.

#### Tipos de Operações

- **forEach** — Executa uma ação dada para cada (key, value)
- **reduce** — Combina todos os (key, value) usando uma function de redução em um resultado único
- **search** — Aplica uma function em cada (key, value) até encontrar um resultado não-null

#### Quatro Variantes para Cada Operação

Cada tipo de operação suporta quatro formas diferentes de trabalhar com os dados:
- **keys e values**: forEach, reduce, search
- **apenas keys**: forEachKey, reduceKeys, searchKeys  
- **apenas values**: forEachValue, reduceValues, searchValues
- **Map.Entry objects**: forEachEntry, reduceEntries, searchEntries

#### Características de Concorrência

**Sem locks**: As operações não fazem lock do estado do ConcurrentHashMap e operam nos elementos conforme prosseguem. As functions fornecidas não devem depender de ordenação ou de objects/values que podem mudar durante a execução.

**Threshold de paralelismo**: Todas as operações requerem especificar um threshold:
- Valor **1**: paralelismo máximo usando thread pool comum
- **Long.MAX_VALUE**: execução em thread única
- **Tamanho atual do map menor que o threshold**: execução sequencial

#### Exemplo Prático

```java
ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
// ... populando o map com dados ...

long parallelismThreshold = 1; // Paralelismo máximo
Optional<Long> maxValue = Optional.ofNullable(
    map.reduceValues(parallelismThreshold, Long::max)
);

// Busca por valor específico
String result = map.searchValues(parallelismThreshold, value -> 
    value > 100 ? "Found large value: " + value : null
);

// Aplicação de ação em todas as chaves
map.forEachKey(parallelismThreshold, key -> 
    System.out.println("Processing key: " + key)
);
```

#### Otimizações de Performance

Especializações primitivas como `reduceValuesToInt`, `reduceKeysToLong` evitam boxing e são mais eficientes que versões genéricas, melhorando significativamente a performance para types primitivos:

```java
// Mais eficiente para primitivos
int sum = map.reduceValuesToInt(parallelismThreshold, Long::intValue, 0, Integer::sum);

// Ao invés de (que causa boxing)
Optional<Integer> sum2 = Optional.ofNullable(
    map.reduceValues(parallelismThreshold, (a, b) -> a.intValue() + b.intValue())
);
```

### Counting - mappingCount()

A classe ConcurrentHashMap fornece um novo method chamado mappingCount, que retorna o número de mappings no map como um long. Você deve usá-lo para código novo em preferência ao method size, que retorna um int.

```java
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
// ... adicionar elementos ...

// ✅ Preferido para código novo - retorna long
long count = map.mappingCount();

// ❌ Método legado - retorna int (limitado)
int size = map.size();
```

**Por que usar mappingCount()?** Torna seu código à prova de futuro para uso quando o número de mappings não couber mais em um int (> 2.1 bilhões de elementos).

### Set Views

A classe ConcurrentHashMap fornece um novo method chamado keySet que retorna uma view do ConcurrentHashMap como um Set. (Mudanças no map são refletidas no Set, e vice-versa.) Você também pode criar um Set apoiado por um ConcurrentHashMap usando o novo method static newKeySet.

```java
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
map.put("key1", "value1");
map.put("key2", "value2");

// View do keySet - mudanças são bidirecionais
Set<String> keyView = map.keySet();
keyView.remove("key1"); // Remove do map também
System.out.println(map.size()); // 1

// Criar Set thread-safe baseado em ConcurrentHashMap
Set<String> concurrentSet = ConcurrentHashMap.newKeySet();
concurrentSet.add("item1");
concurrentSet.add("item2");
```

---

## Summary

- **Java 9** suporta collection factories, que permitem criar pequenas lists, sets e maps imutáveis usando `List.of`, `Set.of`, `Map.of`, e `Map.ofEntries`.
- Os **objects retornados** por essas collection factories são imutáveis, o que significa que você não pode mudar seu estado após a criação.
- A **interface List** suporta os default methods `removeIf`, `replaceAll`, e `sort`.
- A **interface Set** suporta o default method `removeIf`.
- A **interface Map** inclui vários novos default methods para padrões comuns e reduz o escopo para bugs (`forEach`, `getOrDefault`, `compute*`, `merge`, `replace*`).
- **ConcurrentHashMap** suporta os novos default methods herdados de Map, mas fornece implementações thread-safe para eles, além de operações paralelas especializadas (`forEach`, `reduce`, `search`) e melhorias como `mappingCount()` e `newKeySet()`.