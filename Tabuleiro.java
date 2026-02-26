public class Tabuleiro {
    private boolean[][] posicoesOcupadas;
    private boolean[][] jogadores; 
    
    public Tabuleiro() {
        posicoesOcupadas = new boolean[3][3];
        jogadores = new boolean[3][3];
        limpar();
    }
    
    public void limpar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                posicoesOcupadas[i][j] = false;
                jogadores[i][j] = false;
            }
        }
    }
    
    public boolean marcarPosicao(int linha, int coluna, boolean jogador) {
        if (!posicoesOcupadas[linha][coluna]) {
            posicoesOcupadas[linha][coluna] = true;
            jogadores[linha][coluna] = jogador;
            return true;
        }
        return false;
    }
    
    public char verificarVencedor() {
        
        for (int i = 0; i < 3; i++) {
            if (posicoesOcupadas[i][0] && posicoesOcupadas[i][1] && posicoesOcupadas[i][2]) {
                if (jogadores[i][0] == jogadores[i][1] && jogadores[i][1] == jogadores[i][2]) {
                    return jogadores[i][0] ? 'X' : 'O';
                }
            }
        }
        
        for (int j = 0; j < 3; j++) {
            if (posicoesOcupadas[0][j] && posicoesOcupadas[1][j] && posicoesOcupadas[2][j]) {
                if (jogadores[0][j] == jogadores[1][j] && jogadores[1][j] == jogadores[2][j]) {
                    return jogadores[0][j] ? 'X' : 'O';
                }
            }
        }
        
        if (posicoesOcupadas[0][0] && posicoesOcupadas[1][1] && posicoesOcupadas[2][2]) {
            if (jogadores[0][0] == jogadores[1][1] && jogadores[1][1] == jogadores[2][2]) {
                return jogadores[0][0] ? 'X' : 'O';
            }
        }
        
        if (posicoesOcupadas[0][2] && posicoesOcupadas[1][1] && posicoesOcupadas[2][0]) {
            if (jogadores[0][2] == jogadores[1][1] && jogadores[1][1] == jogadores[2][0]) {
                return jogadores[0][2] ? 'X' : 'O';
            }
        }
        
        boolean empate = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!posicoesOcupadas[i][j]) {
                    empate = false;
                    break;
                }
            }
            if (!empate) break;
        }
        
        return empate ? 'E' : ' ';
    }
    
    public boolean[][] getPosicoesOcupadas() {
        return posicoesOcupadas;
    }
    
    public boolean[][] getJogadores() {
        return jogadores;
    }
    
    public Tabuleiro copiar() {
        Tabuleiro copia = new Tabuleiro();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copia.posicoesOcupadas[i][j] = this.posicoesOcupadas[i][j];
                copia.jogadores[i][j] = this.jogadores[i][j];
            }
        }
        return copia;
    }

    public int[][] getLinhaVencedora() {
        boolean[][] jogadas = getJogadores();  
        boolean[][] ocupadas = getPosicoesOcupadas();
    
        int[][] linhas = {
            {0, 0, 0, 1, 0, 2},
            {1, 0, 1, 1, 1, 2},
            {2, 0, 2, 1, 2, 2},
            {0, 0, 1, 0, 2, 0},
            {0, 1, 1, 1, 2, 1},
            {0, 2, 1, 2, 2, 2},
            {0, 0, 1, 1, 2, 2},
            {0, 2, 1, 1, 2, 0}
        };
    
        for (int[] l : linhas) {
            int r1 = l[0], c1 = l[1];
            int r2 = l[2], c2 = l[3];
            int r3 = l[4], c3 = l[5];
    
            if (ocupadas[r1][c1] && ocupadas[r2][c2] && ocupadas[r3][c3]) {
                boolean jogador = jogadas[r1][c1];
                if (jogadas[r2][c2] == jogador && jogadas[r3][c3] == jogador) {
                    return new int[][] { {r1, c1}, {r2, c2}, {r3, c3} };
                }
            }
        }
    
        return null;
    }
    
}