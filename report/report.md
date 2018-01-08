---
title: Benchmarking Java Streams
subtitle: Processamento de Dados com Streams de JAVA
author:
 - Afonso Silva
 - Octávio Maia
lang: pt
date: \today
toc: yes
documentclass: scrreprt
classoption: titlepage=false
papersize: a4
include-before:
 - \setuptoc{toc}{leveldown}
 - \lstset{
     basicstyle=\footnotesize,
     captionpos=b,
     frame=single,
   }
---

Testes
======

## Cálculo dos valores de transações registadas

### Observações

### Métodos a testar

```{.java caption="Cálculo da soma dos valores das transações atraveś de um array do tipo double"}
public double sumValuesArray() {
    double[] values = new double[transactions.size()];
    int i = 0;

    for (TransCaixa transaction : transactions) {
        values[i++] = transaction.getValor();
    }

    double sum = 0.0;

    for (i = 0; i < values.length; i++) {
        sum += values[i];
    }

    return sum;
}
```

```{.java caption="Cálculo da soma dos valores das transações através de uma DoubleStream"}
public double sumValuesDoubleStream() {
    DoubleStream values = transactions.stream()
        .mapToDouble(TransCaixa::getValor);
    return values.sum();
}
```

```{.java caption="Cálculo da soma dos valores das transações através de uma DoubleStream paralela"}
public double sumValuesDoubleStreamParallel() {
    DoubleStream values = transactions.parallelStream()
        .mapToDouble(TransCaixa::getValor);
    return values.sum();
}
```

```{.java caption="Cálculo da soma dos valores das transações através de Stream<Double>"}
public double sumValuesStream() {
    Stream<Double> values = transactions.stream()
        .map(TransCaixa::getValor);
    return values.reduce(0.0, (a,b) -> a + b);
}
```

```{.java caption="Cálculo da soma dos valores das transações através de uma Stream<Double> paralela"}
public double sumValuesStreamParalell() {
    Stream<Double> values = transactions.parallelStream()
        .map(TransCaixa::getValor);
    return values.reduce(0.0, (a,b) -> a + b);
}
```

### Resultados

```table
---
include: t1.csv
---
```

### Análise e conclusões

## Extração dos primeiros e últimos 20% de transações realizadas
