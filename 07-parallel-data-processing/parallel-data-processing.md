# Parallel data processing and performance

- Processing data in parallel with parallel streams
- Performance analyses of parallel streams
- the fork/join framework
- splittin a strea of data using a Spliterator

## Antes de parallel streams
1. você precisava dividir explicitamente a estrutura de dados contendo seus dados em subpartes.
2. Você precisava atribuir cada uma dessas subpartes a uma thread diferente.
3. Você precisava sincronizá-las adequadamente para evitar race conditions indesejadas, aguardar a conclusão de todas as threads e
4. Combinar os resultados parciais.

# Parallel streams

Um parallel stream é um stream que divide seus elementos em múltiplos chunks, processando cada chunk com uma thread diferente. Assim, você pode automaticamente particionar a carga de trabalho de uma determinada operação em todos os cores do seu processador multicore e mantê-los todos igualmente ocupados.

## Turning a sequential stream into a parallel one
Você pode fazer o processo de redução funcional anterior (soma) executar em paralelo transformando o stream em um paralelo; chame o método `parallel` no sequential stream:

```java
public long parallelSum(long n) {
   return Stream.iterate(1L, i -> i + 1)
           .limit(n)
           .parallel()  // Transforma o stream em um paralelo
           .reduce(0L, Long::sum);
}
```

![alt text](image.png)


Da mesma forma, você pode transformar um parallel stream em um sequential invocando o método sequential nele. Note que você pode pensar que ao combinar esses dois métodos você poderia alcançar controle mais refinado sobre quais operações você quer executar em paralelo e quais sequencialmente enquanto percorre o stream.

``` java
stream.parallel()
	.filter(...)
	.sequential()
	.map(...)
	.parallel()
	.reduce();
```

Mas a última chamada para parallel ou sequential vence e afeta o pipeline globalmente. Neste exemplo, o pipeline será executado em paralelo porque essa é a última chamada no pipeline.

> **Configurando o thread pool usado por parallel streams**: Os parallel streams usam internamente o ForkJoinPool padrão, que por padrão possui tantas threads quanto processadores disponíveis (retornado por `Runtime.getRuntime().availableProcessors()`). Você pode alterar o tamanho do pool usando a propriedade do sistema `java.util.concurrent.ForkJoinPool.common.parallelism`, mas esta é uma configuração global que afeta todos os parallel streams do código. Atualmente não é possível especificar este valor para um único parallel stream. O tamanho padrão é uma escolha otimizada, e modificações devem ser feitas apenas quando houver uma boa razão para isso.


## Measuring stream performance

Afirmamos que o método de soma paralela deveria performar melhor que os métodos sequencial e iterativo. No entanto, em engenharia de software, adivinhar nunca é uma boa ideia! Ao otimizar performance, você deve sempre seguir três regras de ouro: medir, medir, medir.

biblioteca chamada Java Microbenchmark Harness (JMH) :
Esta é uma ferramenta que ajuda a criar, de forma simples e baseada em annotations, microbenchmarks confiáveis para programas Java e para qualquer outra linguagem que tenha como alvo a Java Virtual Machine (JVM).

``` xml
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-core</artifactId>
	<version>1.17.4</version>
</dependency>
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-generator-annprocess</artifactId>
	<version>1.17.4</version>
</dependency>
```

plugin do maven:


``` xml
<build>
   <plugins>
       <plugin>
           <groupId>org.apache.maven.plugins</groupId>
           <artifactId>maven-shade-plugin</artifactId>
           <executions>
               <execution>
                   <phase>package</phase>
                   <goals><goal>shade</goal></goals>
                   <configuration>
                       <finalName>benchmarks</finalName>
                       <transformers>
                           <transformer implementation="org.apache.maven.plugins.shade.
                           resource.ManifestResourceTransformer">
                               <mainClass>org.openjdk.jmh.Main</mainClass>
                           </transformer>
                       </transformers>
                   </configuration>
               </execution>
           </executions>
       </plugin>
   </plugins>
</build>
```

exemplo de benchmark:

``` java

@BenchmarkMode(Mode.AverageTime)          // Mede o tempo médio necessário para o método de benchmark
@OutputTimeUnit(TimeUnit.MILLISECONDS)    // Imprime os resultados do benchmark usando milissegundos como unidade de tempo
@Fork(2, jvmArgs={"-Xms4G", "-Xmx4G"})   // Executa o benchmark 2 vezes para aumentar a confiabilidade dos resultados, com 4Gb de heap space
public class ParallelStreamBenchmark {
   private static final long N = 10_000_000L;

   @Benchmark                             // O método a ser testado no benchmark
   public long sequentialSum() {
       return Stream.iterate(1L, i -> i + 1).limit(N)
               .reduce(0L, Long::sum);
   }

   @TearDown(Level.Invocation)           // Tenta executar o garbage collector após cada iteração do benchmark
   public void tearDown() {
       System.gc();
   }
}
```
