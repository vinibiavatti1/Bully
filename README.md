# Algoritmo Bully
Este programa representa o algoritmo Bully utilizado nos conceitos de Sistemas Distribuídos. O projeto consiste em uma simulaçao de processos assíncronos comandados por um processo pai (Coordenador). O próprio algoritmo verifica possíveis desconexões do processo pai, e realiza uma eleição para promover o próximo processo coordenador que será o novo servidor. 

A aplicaçao utiliza Threads para simular os processos em um cluster, portanto este nao é um programa operável e utilizável.

## Plataforma
A plataforma <b>Java 8</b> foi utilizada para a simulação. O projeto pode ser aberto pela IDE Apache NetBeans.

## Javadoc
A documentação do código está em formato Javadoc e pode ser encontrada em <a href="./dist/javadoc">aqui</a>. Basta baixar o repositório e executar abrir a página index.html no navegador.
