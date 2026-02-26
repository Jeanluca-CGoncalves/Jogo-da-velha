# Jogo da Velha - Cyberpunk ⭕❌

Este repositório contém um Jogo da Velha (Tic-Tac-Toe) desenvolvido em Java, com uma interface gráfica estilizada com o tema Cyberpunk. O jogo permite partidas entre um jogador humano e uma Inteligência Artificial, oferecendo diferentes níveis de dificuldade e estratégias de decisão.

## 🚀 Funcionalidades

* **Menu de Configuração Inicial:** Antes de iniciar a partida, o usuário pode definir quem começa (Jogador ou IA), o nível de profundidade/dificuldade (1 a 9) e a estratégia da máquina.
* **Inteligência Artificial Dupla:** * **Minimax:** Algoritmo recursivo que prevê todas as jogadas possíveis para garantir a melhor decisão, tornando a máquina imbatível nos níveis mais altos.
    * **Heurística:** Uma abordagem baseada em regras de prioridade (tentar vencer, bloquear o adversário, dominar o centro, cantos ou laterais) para um estilo de jogo alternativo.
* **Interface Gráfica (GUI):** Construída com `javax.swing`, apresentando painéis com gradientes, botões interativos que mudam de cor ao passar o mouse e uma fonte futurista ("Orbitron").
* **Efeitos Audiovisuais:**
    * Sons integrados para cliques, vitórias e empates (utilizando a classe `Som`).
    * Animação piscante na linha vencedora para destacar o fim da partida.
    * Botão de "Reiniciar" com efeito de pulsação de cores utilizando HSB.
* **Placar em Tempo Real:** Contador atualizado automaticamente para vitórias, derrotas e empates.

## 🛠️ Tecnologias e Estrutura

* **Linguagem:** Java.
* **Interface:** Java Swing (`JFrame`, `JPanel`, `JButton`, etc.) e AWT para renderização de cores e gradientes.
* **Áudio:** `javax.sound.sampled` para reprodução de efeitos sonoros.
* **Padrão de Projeto:** Utilização de interfaces (`Jogador`) para polimorfismo entre o jogador humano (`JogadorHumanoGUI`) e a máquina (`JogadorIA`).

## ⚙️ Como Executar

1.  Certifique-se de ter o [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) instalado na sua máquina.
2.  Clone este repositório:
    ```bash
    git clone [https://github.com/Jeanluca-CGoncalves/Jogo-da-velha.git](https://github.com/Jeanluca-CGoncalves/Jogo-da-velha.git)
    ```
3.  Acesse a pasta do projeto e certifique-se de que os diretórios `fonts/` (contendo `Orbitron-Regular.ttf`) e `sons/` (contendo os arquivos `.wav`) estão na raiz do projeto.
4.  Compile os arquivos `.java`:
    ```bash
    javac *.java
    ```
5.  Execute a classe principal:
    ```bash
    java Main
    ```
