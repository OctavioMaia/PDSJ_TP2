---
title: Benchmarking Java Streams
subtitle: Processamento de Dados com Streams de JAVA
author:
 - Afonso Silva, \small{a70387}
 - Octávio Maia, \small{a71369}
lang: pt
toc: yes
toc-depth: 0
documentclass: scrreprt
classoption: titlepage=false
papersize: a4
colorlinks: true
include-before:
 - \setuptoc{toc}{leveldown}
 - \lstset{
     basicstyle=\scriptsize,
     captionpos=b,
     frame=single,
   }
---

Contextualização e objetivos
============================

### Introdução {-}

Pretende-se com este relatório dar a conhecer as diferenças de *performance*
na análise de grandes sequências de dados recorrendo quer a técnicas
tradicionais do `JAVA7` ou a técnicas mais modernas do `JAVA8` e `JAVA9`.

Para tal criou-se um programa de ambiente de execução de testes, que permitia
ao utilizador selecionar o teste pretendido e executá-lo, mostrando os
resultados no `STDOUT`. A adição de novos testes é feita de forma
*standardizada* pois todos eles seguem o mesmo formato.

Neste relatório, para cada um dos testes analisados, encontra-se ocódigo-fonte
que lhe diz respeito, bem como os resultados da sua execução para cada
`input`. Esses resultados são demonstrados em formato tabular e em formato
gráfico sendo efetivamente comentados e analisados.

### Execução {-}

Todos os testes deste projeto foram desenvolvidos no mesmo programa, que se
encontra hospedado no `GitHub`:

<https://github.com/OctavioMaia/PDSJ_TP2>

O executável pode ser descarregado diretamente
[aqui](https://github.com/OctavioMaia/PDSJ_TP2/blob/master/bjs.jar?raw=true) e
é corrido desta forma:

	$ java -jar bjs.jar T

onde `T` é o número do teste que se pretende executar.

O resultado do teste será um tabela `CSV` que contém na primeira linha os
nomes dos **indicadores** executados e na primeira coluna os nomes dos
**inputs** indicados. Este `CSV` pode ser facilmente incluído numa folha de
cálculo para melhor análise estatística do mesmo, ou numa table em formato de
documento.

### Implementação {-}

De forma a uniformizar e facilitar a implementação de todos os testes
requisitados, optou-se por implementar uma **interface** comun a todos os
testes. Deste modo cada teste corresponderá a uma **classe** que implementa
esta mesma interface.

Assume-se também que cada teste é composto por um conjunto de indicadores, que
mais não são o conjunto dos métodos que o teste deve executar. Para além disso,
cada teste pode receber ou não, um determinado **input** (por exemplo o
conjunto de todas as transações realizadas, ou o número de números aleatórios a
gerar).


```{.java}
public interface Test {
    /**
     * Returns a textual description of the input of this test.
     * If no input is given, then it should return an empty Optional.
     *
     * @return textual description of the input if it exists.
     */
    default Optional<String> input() {
        return Optional.empty();
    }

    /**
     * Returns all the indicators that this test should run.
     * Each indicator is an association between its textual description
     * and the function (Supplier) that it should run.
     *
     * @return Amap, where the keys are the textual description of the
     *         indicator, and the values are the supplier to run.
     */
    Map<String, Supplier<?>> indicators();

    /**
     * Calculates the times needed to run each one of the indicators.
     *
     * @return a map, where the keys are the textual description of the
     *         indicator, and the values are theirs results in seconds.
     */
    default Map<String, Double> results() {
        return indicators().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> measure(entry.getValue()).getKey()
        ));
    }

    /**
     * Measures the required time to run the given supplier.
     * Before the supplier is ran, it does a 5 turns warmup and
     * cleans the memory.
     * @author FMM
     *
     * @param supplier supplier to run
     * @param <R> type of the result of the supplier
     * @return An entry composed by the time in seconds that the
     *         supplier took to run, and the result of that supplier.
     */
    static <R> SimpleEntry<Double, R> measure(Supplier<R> supplier) {
        for (int i = 0; i < 5; i++) supplier.get();
        System.gc();

        Crono.start();
        R result = supplier.get();
        Double time = Crono.stop();
        return new SimpleEntry<>(time, result);
    }
}
```

Com a utilização desta **interface** torna-se então bastante intuitiva a
criação de novos testes. A classe principal do programa fica então responsável
por ler o número do teste escolhido pelo utilizador e por corrê-lo com o
**input** especificado.


Testes
======

Todos os testes aqui expostos foram executados num máquina com as seguintes
especificações:

    Windows 10 Pro v1607 (64 bits)
    Intel Core i5 4690k (4c/4t)
    16 GB RAM
    JDK8_144, JDK9_01
