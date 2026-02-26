import javax.swing.SwingUtilities;

public class JogadorIA implements Jogador {
    private boolean jogadorX;
    private Tabuleiro tabuleiro;
    private int dificuldade;
    private String estrategia;
    private JogoDaVelhaGUI gui;

    public JogadorIA(boolean jogadorX, Tabuleiro tabuleiro, int dificuldade, String estrategia, JogoDaVelhaGUI gui) {
        this.jogadorX = jogadorX;
        this.tabuleiro = tabuleiro;
        this.dificuldade = dificuldade;
        this.estrategia = estrategia;
        this.gui = gui;
    }

    @Override
    public void jogar() {
        int[] jogada;

        if (estrategia.equalsIgnoreCase("Heurística")) {
            jogada = encontrarJogadaHeuristica();
        } else {
            jogada = encontrarMelhorJogadaMinimax();
        }

        tabuleiro.marcarPosicao(jogada[0], jogada[1], jogadorX);

        SwingUtilities.invokeLater(() -> {
            gui.atualizarTabuleiro();
            gui.proximoJogador();
        });
    }

    private int[] encontrarMelhorJogadaMinimax() {
        int melhorValor = jogadorX ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] melhorJogada = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!tabuleiro.getPosicoesOcupadas()[i][j]) {
                    Tabuleiro copia = tabuleiro.copiar();
                    copia.marcarPosicao(i, j, jogadorX);

                    int valor = minimax(copia, 0, !jogadorX);

                    if ((jogadorX && valor > melhorValor) || (!jogadorX && valor < melhorValor)) {
                        melhorValor = valor;
                        melhorJogada[0] = i;
                        melhorJogada[1] = j;
                    }
                }
            }
        }

        return melhorJogada;
    }

    private int minimax(Tabuleiro estado, int profundidade, boolean maximizando) {
        char resultado = estado.verificarVencedor();

        if (resultado == 'X') return 10 - profundidade;
        if (resultado == 'O') return -10 + profundidade;
        if (resultado == 'E') return 0;
        if (profundidade >= dificuldade) return 0;

        int melhorValor = maximizando ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!estado.getPosicoesOcupadas()[i][j]) {
                    Tabuleiro copia = estado.copiar();
                    copia.marcarPosicao(i, j, maximizando);

                    int valor = minimax(copia, profundidade + 1, !maximizando);

                    if (maximizando) {
                        melhorValor = Math.max(melhorValor, valor);
                    } else {
                        melhorValor = Math.min(melhorValor, valor);
                    }
                }
            }
        }

        return melhorValor;
    }

    private int[] encontrarJogadaHeuristica() {
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!tabuleiro.getPosicoesOcupadas()[i][j]) {
                    Tabuleiro copia = tabuleiro.copiar();
                    copia.marcarPosicao(i, j, jogadorX);
                    if (copia.verificarVencedor() == (jogadorX ? 'X' : 'O')) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!tabuleiro.getPosicoesOcupadas()[i][j]) {
                    Tabuleiro copia = tabuleiro.copiar();
                    copia.marcarPosicao(i, j, !jogadorX);
                    if (copia.verificarVencedor() == (!jogadorX ? 'X' : 'O')) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        if (!tabuleiro.getPosicoesOcupadas()[1][1]) {
            return new int[]{1, 1};
        }

        int[][] cantos = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] canto : cantos) {
            if (!tabuleiro.getPosicoesOcupadas()[canto[0]][canto[1]]) {
                return canto;
            }
        }

        int[][] laterais = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
        for (int[] lateral : laterais) {
            if (!tabuleiro.getPosicoesOcupadas()[lateral[0]][lateral[1]]) {
                return lateral;
            }
        }
        return new int[]{0, 0};
    }

    @Override
    public boolean isJogadorX() {
        return jogadorX;
    }
}
