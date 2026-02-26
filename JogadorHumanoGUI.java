public class JogadorHumanoGUI implements Jogador {
    private boolean jogadorX;
    private Tabuleiro tabuleiro;
    private JogoDaVelhaGUI gui;

    public JogadorHumanoGUI(boolean jogadorX, Tabuleiro tabuleiro, JogoDaVelhaGUI gui) {
        this.jogadorX = jogadorX;
        this.tabuleiro = tabuleiro;
        this.gui = gui;
    }

    public void processarClique(int linha, int coluna) {
        if (tabuleiro.marcarPosicao(linha, coluna, jogadorX)) {
            gui.atualizarTabuleiro();
            gui.proximoJogador();
        }
    }

    @Override
    public void jogar() {} 
    @Override
    public boolean isJogadorX() {
        return jogadorX;
    }
}