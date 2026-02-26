import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import java.util.*;
import javax.swing.Timer;


public class JogoDaVelhaGUI extends JFrame {
    private Tabuleiro tabuleiro;
    private Jogador jogadorX;
    private Jogador jogadorO;
    private Jogador jogadorAtual;
    private ConfiguracaoJogo config;
    private JButton[][] botoes;
    private JLabel statusLabel;
    private JButton reiniciarButton;
    private final Color COR_X = new Color(255, 99, 132);
    private final Color COR_O = new Color(54, 162, 235);
    private final Color COR_BACKGROUND = new Color(34, 34, 34);
    private final Color COR_BOTAO = new Color(54, 54, 54);
    private Font FONTE;
    private int vitorias = 0, derrotas = 0, empates = 0;
    private JLabel contadorLabel;
    private java.util.List<int[]> historicoJogadas = new ArrayList<>();

    public JogoDaVelhaGUI() {
        super("Jogo da Velha - Cyberpunk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        carregarFonteFuturista();

        config = new ConfiguracaoJogo(this);
        config.mostrarMenuInicial();
    }

    private void carregarFonteFuturista() {
        try {
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Orbitron-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fonte);
            FONTE = fonte.deriveFont(Font.BOLD, 50f);
        } catch (Exception e) {
            FONTE = new Font("SansSerif", Font.BOLD, 50);
        }
    }

    public void iniciarJogo(boolean humanoComeca, int dificuldade, String estrategia) {
        tabuleiro = new Tabuleiro();
        historicoJogadas.clear();

        jogadorX = new JogadorHumanoGUI(true, tabuleiro, this);
        jogadorO = new JogadorIA(false, tabuleiro, dificuldade, estrategia, this);
        jogadorAtual = humanoComeca ? jogadorX : jogadorO;

        getContentPane().removeAll();
        criarInterface();

        if (!humanoComeca) {
            SwingUtilities.invokeLater(() -> jogadorAtual.jogar());
        }

        revalidate();
        repaint();
    }

    private void criarInterface() {
        JPanel tabuleiroPanel = new PainelComFundo();
        tabuleiroPanel.setLayout(new GridLayout(3, 3, 10, 10));
        tabuleiroPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botoes = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton botao = new JButton();
                botao.setFont(FONTE);
                botao.setFocusPainted(false);
                botao.setBackground(COR_BOTAO);
                botao.setForeground(Color.WHITE);
                botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                botao.setOpaque(true);
                botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                final int linha = i;
                final int coluna = j;

                botao.addActionListener(e -> {
                    if (jogadorAtual instanceof JogadorHumanoGUI) {
                        historicoJogadas.add(new int[]{linha, coluna});
                        Som.tocar("sons/click.wav");
                        ((JogadorHumanoGUI) jogadorAtual).processarClique(linha, coluna);
                    }
                });

                botao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (botao.isEnabled()) {
                            botao.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
                            botao.setBackground(new Color(70, 70, 70));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (botao.isEnabled()) {
                            botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                            botao.setBackground(COR_BOTAO);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        botao.setBackground(new Color(100, 100, 100));
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        botao.setBackground(new Color(70, 70, 70));
                    }
                });

                botoes[i][j] = botao;
                tabuleiroPanel.add(botao);
            }
        }

        statusLabel = new JLabel("Vez do " + (jogadorAtual.isJogadorX() ? "❌" : "⭕️"), SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        contadorLabel = new JLabel(getContadorTexto(), SwingConstants.CENTER);
        contadorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contadorLabel.setForeground(Color.LIGHT_GRAY);

        reiniciarButton = new JButton("🔄 Reiniciar");
        reiniciarButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        reiniciarButton.setFocusPainted(false);
        reiniciarButton.setBackground(new Color(99, 255, 132));
        reiniciarButton.setForeground(Color.BLACK);
        reiniciarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        reiniciarButton.addActionListener(e -> config.mostrarMenuInicial());

        animarBotaoReiniciar();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(COR_BACKGROUND);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(reiniciarButton, BorderLayout.CENTER);

        add(statusLabel, BorderLayout.NORTH);
        add(tabuleiroPanel, BorderLayout.CENTER);
        add(contadorLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    private String getContadorTexto() {
        return "Vitórias: " + vitorias + "  |  Derrotas: " + derrotas + "  |  Empates: " + empates;
    }

    public void atualizarTabuleiro() {
        boolean[][] ocupadas = tabuleiro.getPosicoesOcupadas();
        boolean[][] jogadores = tabuleiro.getJogadores();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ocupadas[i][j]) {
                    String simbolo = jogadores[i][j] ? "❌" : "⭕️";
                    botoes[i][j].setText(simbolo);
                    botoes[i][j].setEnabled(false);
                    botoes[i][j].setForeground(jogadores[i][j] ? COR_X : COR_O);
                } else {
                    botoes[i][j].setText("");
                    botoes[i][j].setEnabled(jogadorAtual instanceof JogadorHumanoGUI);
                    botoes[i][j].setForeground(Color.WHITE);
                }
            }
        }

        statusLabel.setText("Vez do " + (jogadorAtual.isJogadorX() ? "❌" : "⭕️"));
    }

    public void proximoJogador() {
        jogadorAtual = (jogadorAtual == jogadorX) ? jogadorO : jogadorX;
        atualizarTabuleiro();

        char resultado = tabuleiro.verificarVencedor();
        if (resultado != ' ') {
            String mensagem = resultado == 'E' ? "Empate!" : resultado + " venceu!";
            statusLabel.setText(mensagem);
            Som.tocar(resultado == 'E' ? "sons/empate.wav" : "sons/vitoria.wav");
            desabilitarBotoes();
            atualizarContadores(resultado);
            contadorLabel.setText(getContadorTexto());
            int[][] linha = tabuleiro.getLinhaVencedora();
            if (linha != null) animarVitoria(linha);
        } else if (!(jogadorAtual instanceof JogadorHumanoGUI)) {
            new Thread(() -> {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                SwingUtilities.invokeLater(() -> jogadorAtual.jogar());
            }).start();
        }
    }

    private void atualizarContadores(char resultado) {
        if (resultado == 'X') vitorias++;
        else if (resultado == 'O') derrotas++;
        else empates++;
    }

    private void animarVitoria(int[][] linha) {
        Timer timer = new Timer(300, null);
        final boolean[] on = {true};
        final int[] count = {0};

        timer.addActionListener(e -> {
            for (int[] pos : linha) {
                JButton b = botoes[pos[0]][pos[1]];
                b.setBackground(on[0] ? Color.CYAN : COR_BOTAO);
                b.setForeground(on[0] ? Color.BLACK : Color.WHITE);
            }
            on[0] = !on[0];
            count[0]++;
            if (count[0] > 6) timer.stop();
        });
        timer.start();
    }

    private void desabilitarBotoes() {
        for (JButton[] linha : botoes) {
            for (JButton botao : linha) {
                botao.setEnabled(false);
            }
        }
    }

    private void animarBotaoReiniciar() {
        Timer pulsar = new Timer(100, null);
        final float[] brilho = {0f};

        pulsar.addActionListener(e -> {
            float h = 0.4f;
            float s = 1f;
            float b = 0.5f + 0.5f * (float) Math.sin(brilho[0]);
            reiniciarButton.setBackground(Color.getHSBColor(h, s, b));
            brilho[0] += 0.2;
        });
        pulsar.start();
    }
}

class PainelComFundo extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint grad = new GradientPaint(0, 0, new Color(20, 20, 50), getWidth(), getHeight(), new Color(0, 0, 0));
        g2.setPaint(grad);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}

class Som {
    public static void tocar(String caminho) {
        try {
            File arquivo = new File(caminho);
            if (!arquivo.exists()) return;
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(arquivo);
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
