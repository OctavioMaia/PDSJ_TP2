Conclusões e trabalho futuro
============================

### Conclusão {-}

Após realizado este projeto é justo afirmar que o mesmo foi bem sucedido. Foram
testadas todas as funcionalidades desejadas e estes testes foram ao encontro
daquilo que era expectável.

De um modo geral a utilização das novas `streams` do `JAVA8` compensa em termos
de *performance* para a maioria dos problemas. A sua paralelização só começa a
ser viável a partir de `inputs` bastante grandes, pois requere algum custo de
inicialização para dividir a carga de trabalho. Contudo, existe um ponto em
comum a praticamente todos os testes realiados: o código escrito com recurso
a `streams` é bastante mais conciso e legível, não requere variáveis auxiliares,
portanto de uma certa forma é *point-free* e dá a sensação ao programador de
estar a escrever código funcional, que tal como é sabido garante uma boa
robustez e minimiza a propenção de erro do mesmo.

Uma outra surpresa foi a pouca diferença de *performance* entre a `JDK8` e a
`JDK9`, provavelmente se deve ao facto de a última ser bastante recente, e de
a máquina onde foram corridos os testes não beneficiar muito desta instalação.
