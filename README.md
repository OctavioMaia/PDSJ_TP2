# Benchmarking JAVA Streams


Executar:

    java -jar bjs.jar [1-12]

Cada teste gera no `STDOUT` um `CSV` que contém os resultados dos testes para
os 4 inputs.

Gerar relatório:

    pandoc *.md -o report.pdf -F pantable --top-level-division part --listings
