# Benchmarking JAVA Streams


Correr o teste 1:

    java -jar bjs.jar 1

Cada teste gera no `STDOUT` um `CSV` que contém os resultados dos testes para
os 4 inputs.

Gerar relatório:

    pandoc report.md -o report.pdf -F pantable --top-level-division part --listings
